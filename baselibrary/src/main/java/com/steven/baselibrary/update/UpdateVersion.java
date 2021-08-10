package com.steven.baselibrary.update;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.RemoteViews;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.FileProvider;

import com.steven.baselibrary.R;
import com.steven.baselibrary.util.MyToast;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


/**
 * author: zhoufan
 * data: 2021/8/10 10:04
 * content: 实现App的版本更新
 */
public class UpdateVersion {

    /**
     * 上下文
     */
    private Context mContext;

    /**
     * 版本号
     */
    private int mVersionCode;

    /**
     * 版本名称
     */
    private String mVersionName;

    /**
     * 更新提示内容
     */
    private String mUpdateContent;

    /**
     * 更新的APK对应的网络地址
     */
    private String mUpdateAPKUrl;

    /**
     * 更新的类型 1：正常更新，2：强制更新
     */
    private int mUpdateStatus = 1;

    /**
     * 更新的时候是否在前台开启进度条
     */
    private boolean isShowUpdateDialog;

    /**
     * 更新弹出的确认更新确认框
     */
    private AlertDialog mDialog;

    /**
     * APK下载之后保存的地址
     */
    private String mSavePath;

    /**
     * 保存的文件名
     */
    private String mSaveFileName;

    /**
     * 当前下载的进度
     */
    private int mDownloadProgress;

    private RelativeLayout mUpdateProgressLayout;
    private ProgressBar mUpdateProgressbar;
    private TextView mUpdateDownloadNumber;
    private TextView mUpdateCancel;
    private TextView mUpdateSure;

    public UpdateVersion(Context context, int versionCode, String versionName, String updateContent, String updateAPKUrl, int updateStatus, boolean isShowUpdateDialog) {
        this.mContext = context;
        this.mVersionCode = versionCode;
        this.mVersionName = versionName;
        this.mUpdateContent = updateContent;
        this.mUpdateAPKUrl = updateAPKUrl;
        this.mUpdateStatus = updateStatus;
        this.isShowUpdateDialog = isShowUpdateDialog;
    }

    public void showUpdateDialog() {
        // 设置下载的安装路径
        mSavePath = Environment.getExternalStorageDirectory().getPath() + File.separator + "Android/data/" + mContext.getPackageName() + File.separator + "apk";
        mSaveFileName = mSavePath + File.separator + mVersionCode + ".apk";
        createDialog();
        // 取消更新
        mUpdateCancel.setOnClickListener(v -> {
            if (mDialog != null && mDialog.isShowing())
                mDialog.dismiss();
        });
        // 确认更新
        mUpdateSure.setOnClickListener(v -> {
            File file = new File(mSaveFileName);
            if (file.exists()) {
                // apk文件已经存在，直接安装
                installApk(mSaveFileName);
            } else {
                // 后台开启下载功能，下载完毕后自动更新
                if (isShowUpdateDialog) {
                    mUpdateProgressLayout.setVisibility(View.VISIBLE);
                } else {
                    if (mDialog != null && mDialog.isShowing()) {
                        mDialog.dismiss();
                    }
                }
                startDownloadApk();
            }
        });
    }

    /**
     * 弹出Dialog显示更新对话框
     */
    private void createDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext, R.style.bklistDialog);
        View updateView = LayoutInflater.from(mContext).inflate(R.layout.dialog_update_version, null);
        initDialogView(updateView);
        builder.setView(updateView);
        mDialog = builder.create();
        // 判断是否为强制更新
        if (mUpdateStatus == 2) {
            mDialog.setCanceledOnTouchOutside(false);
            mUpdateCancel.setVisibility(View.GONE);
            mDialog.setOnKeyListener(keyListener);
            mDialog.setCancelable(false);
        } else {
            mDialog.setCanceledOnTouchOutside(true);
        }
        // 显示
        mDialog.show();
    }

    /**
     * 获取View的控件并添加点击响应事件
     */
    private void initDialogView(View updateView) {
        TextView newVersionName = updateView.findViewById(R.id.new_version_value);
        TextView newVersionContent = updateView.findViewById(R.id.update_content);
        mUpdateProgressLayout = updateView.findViewById(R.id.update_download_layout);
        mUpdateProgressbar = updateView.findViewById(R.id.update_download_progressbar);
        mUpdateDownloadNumber = updateView.findViewById(R.id.update_download_number);
        mUpdateCancel = updateView.findViewById(R.id.cancel);
        mUpdateSure = updateView.findViewById(R.id.sure);
        String versionName = "V" + mVersionName;
        newVersionName.setText(versionName);
        if (!TextUtils.isEmpty(mUpdateContent)) {
            newVersionContent.setText(mUpdateContent);
        } else {
            newVersionContent.setText(mContext.getResources().getString(R.string.updateNewVersion));
        }
        mUpdateSure.setText(mContext.getResources().getString(R.string.new_update));
    }

    /**
     * 禁用返回键
     */
    private DialogInterface.OnKeyListener keyListener = (dialog, keyCode, event) -> keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0;

    /**
     * 安装apk
     */
    private void installApk(String filePath) {
        if (mUpdateProgressLayout.getVisibility() == View.GONE) {
            NotificationManager manager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
            manager.cancel(1);
        }
        File file = new File(filePath);
        if (Build.VERSION.SDK_INT >= 24) {//判读版本是否在7.0以上
            String authority = mContext.getPackageName() + ".fileProvider";
            Uri apkUri = FileProvider.getUriForFile(mContext, authority, file);
            Intent install = new Intent(Intent.ACTION_VIEW);
            install.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            install.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);//添加这一句表示对目标应用临时授权该Uri所代表的文件
            install.setDataAndType(apkUri, "application/vnd.android.package-archive");
            mContext.startActivity(install);
        } else {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setDataAndType(Uri.fromFile(file),
                    "application/vnd.android.package-archive");
            mContext.startActivity(intent);
        }
    }

    /**
     * 使用OkHttp来进行网络下载
     */
    private void startDownloadApk() {
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder().url(mUpdateAPKUrl).get().build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                MyToast.showCenterSortToast(mContext, mContext.getString(R.string.download_fail));
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) {
                InputStream is = null;
                byte[] buf = new byte[2048];
                int len = 0;
                FileOutputStream fos = null;
                //储存下载文件的目录
                File dir = new File(mSavePath);
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                File file = new File(mSaveFileName);
                try {
                    is = response.body().byteStream();
                    long total = response.body().contentLength();
                    fos = new FileOutputStream(file);
                    long sum = 0;
                    while ((len = is.read(buf)) != -1) {
                        fos.write(buf, 0, len);
                        sum += len;
                        int progress = (int) (sum * 1.0f / total * 100);
                        if (mUpdateProgressLayout.getVisibility() == View.VISIBLE) {
                            //下载中更新进度条
                            Observable.just(progress).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(integer -> {
                                mUpdateProgressbar.setProgress(integer);
                                String currentPercent = integer + "%";
                                mUpdateDownloadNumber.setText(currentPercent);
                            });
                        } else {
                            Observable.just(progress).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(integer -> {
                                updateNotification(progress);
                            });
                        }
                    }
                    fos.flush();
                    //下载完成
                    Observable.just(100).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(integer -> installApk(mSaveFileName));
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    try {
                        if (is != null) {
                            is.close();
                        }
                        if (fos != null) {
                            fos.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    /**
     * 通知栏更新
     */
    private void updateNotification(int progress) {
        if (NotificationManagerCompat.from(mContext).areNotificationsEnabled()) {
            if (progress > mDownloadProgress) {
                mDownloadProgress = progress;
                NotificationManager manager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
                if (Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    NotificationChannel notificationChannel = new NotificationChannel("1", "update", NotificationManager.IMPORTANCE_HIGH);
                    notificationChannel.setSound(null, null);
                    notificationChannel.enableLights(false);
                    notificationChannel.setLightColor(Color.RED);
                    notificationChannel.setShowBadge(false);
                    notificationChannel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
                    manager.createNotificationChannel(notificationChannel);
                }
                // 创建自定义的样式布局
                RemoteViews remoteViews = new RemoteViews(mContext.getPackageName(), R.layout.download_progress_state_view);
                // 在这里可以设置RemoteView的初始布局
                NotificationCompat.Builder builder = new NotificationCompat.Builder(mContext, "1");
                builder.setCustomContentView(remoteViews);
                // 不可以自动取消
                builder.setAutoCancel(false);
                // 必须要设置，否则在android 10手机上面会闪退
                builder.setSmallIcon(R.mipmap.ic_launcher_round);
                // 设置通知的优先级
                builder.setPriority(NotificationCompat.PRIORITY_MAX);
                Notification nn = builder.build();
                nn.contentView = remoteViews;
                nn.icon = R.mipmap.ic_launcher;
                remoteViews.setImageViewResource(R.id.download_progress_img, R.mipmap.ic_launcher);
                String loadShow = mContext.getResources().getString(R.string.app_download_show);
                remoteViews.setTextViewText(R.id.download_progress_name, loadShow);
                remoteViews.setProgressBar(R.id.download_progressbar, 100, mDownloadProgress, false);
                remoteViews.setTextViewText(R.id.download_progress_text, mDownloadProgress + "%");
                manager.notify(1, nn);
            }
        }
    }
}
