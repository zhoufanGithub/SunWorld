package com.steven.lbslibrary

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.baidu.location.BDLocation
import com.baidu.location.BDLocationListener
import com.baidu.location.LocationClient
import com.baidu.location.LocationClientOption
import com.baidu.mapapi.SDKInitializer
import com.baidu.mapapi.map.BaiduMap
import com.baidu.mapapi.map.MapStatusUpdateFactory
import com.baidu.mapapi.map.MyLocationData
import com.baidu.mapapi.model.LatLng
import kotlinx.android.synthetic.main.activity_l_b_s.*

class LBSActivity : AppCompatActivity(), BDLocationListener {

    private var mPermissionList: MutableList<String> = mutableListOf()
    private var locationClient: LocationClient? = null
    private var baiduMap: BaiduMap? = null
    private var isFirstLocate = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        locationClient = LocationClient(this)
        locationClient!!.registerLocationListener(this)
        SDKInitializer.initialize(applicationContext)
        setContentView(R.layout.activity_l_b_s)
        initLBS()
        baiduMap = baiduMapView.map
        baiduMap!!.isMyLocationEnabled = true
    }

    /**
     * 开启定位
     */
    private fun initLBS() {
        if (ContextCompat.checkSelfPermission(
                this@LBSActivity,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            mPermissionList.add(Manifest.permission.ACCESS_FINE_LOCATION)
        }
        if (ContextCompat.checkSelfPermission(
                this@LBSActivity,
                Manifest.permission.READ_PHONE_STATE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            mPermissionList.add(Manifest.permission.READ_PHONE_STATE)
        }
        if (ContextCompat.checkSelfPermission(
                this@LBSActivity,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            mPermissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }
    }

    override fun onResume() {
        super.onResume()
        baiduMapView.onResume()

    }

    override fun onPause() {
        super.onPause()
        baiduMapView.onPause()
    }

    private fun navigateTo(latitude: Double, longitude: Double) {
        if (isFirstLocate) {
            val ll = LatLng(latitude, longitude)
            var update = MapStatusUpdateFactory.newLatLng(ll)
            baiduMap!!.animateMapStatus(update)
            update = MapStatusUpdateFactory.zoomTo(16f)
            baiduMap!!.animateMapStatus(update)
            isFirstLocate = false
        }
        val locationBuilder = MyLocationData.Builder()
        locationBuilder.latitude(latitude)
        locationBuilder.longitude(longitude)
        val locationData = locationBuilder.build()
        baiduMap!!.setMyLocationData(locationData)
    }

    fun getCurrentLBS(view: View) {
        if (mPermissionList.isNotEmpty()) {
            val permission: Array<String> = mPermissionList.toTypedArray<String>()
            ActivityCompat.requestPermissions(this@LBSActivity, permission, 1)
        } else {
            requestLocation()
        }
    }

    private fun requestLocation() {
        initLocation()
        locationClient!!.start()
    }

    private fun initLocation() {
        val option = LocationClientOption()
        option.setScanSpan(5000)
        option.setIsNeedAddress(true)
        locationClient!!.locOption = option
    }

    override fun onReceiveLocation(location: BDLocation?) {
        val currentPosition = StringBuilder()
        currentPosition.append("纬度：").append(location!!.latitude).append("\n")
        currentPosition.append("经度：").append(location.longitude).append("\n")
        currentPosition.append("国家：").append(location.country).append("\n")
        currentPosition.append("省：").append(location.province).append("\n")
        currentPosition.append("市：").append(location.city).append("\n")
        currentPosition.append("区：").append(location.district).append("\n")
        currentPosition.append("街道：").append(location.street).append("\n")
        currentPosition.append("定位方式：")
        if (location.locType == BDLocation.TypeGpsLocation) {
            currentPosition.append("GPS")
        } else if (location.locType == BDLocation.TypeNetWorkLocation) {
            currentPosition.append("网络")
        }
        get_current_lbs.text = currentPosition
        navigateTo(location!!.latitude, location.longitude)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == 1) {
            if (grantResults.isNotEmpty()) {
                for (result in grantResults) {
                    if (result != PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(this@LBSActivity, "必须同意所有权限才能使用本程序", Toast.LENGTH_SHORT)
                            .show()
                        finish()
                        return
                    }
                }
                requestLocation()
            } else {
                Toast.makeText(this@LBSActivity, "必须同意所有权限才能使用本程序", Toast.LENGTH_SHORT)
                    .show()
                finish()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        locationClient!!.stop()
        baiduMapView.onDestroy()
        baiduMap!!.isMyLocationEnabled = false
    }
}
