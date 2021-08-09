package com.steven.baselibrary.contentprovider

import android.Manifest
import android.app.Activity
import android.content.ContentValues
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.steven.baselibrary.R
import kotlinx.android.synthetic.main.activity_content_provider.*
import me.jessyan.autosize.internal.CustomAdapt
import java.lang.Exception

/**
 * 读取手机上面的联系人信息
 */
class ContentProviderActivity : AppCompatActivity(){

    private var mAdapter: InfoAdapter? = null
    private var infoList: MutableList<MutableMap<String, String>> = mutableListOf()
    private val requestCodeValue = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(android.R.style.Theme_Light)
        setContentView(R.layout.activity_content_provider)
        initRecyclerView()
    }

    /*############################### 获取手机联系人 start ###########################*/

    private fun initRecyclerView() {
        mAdapter = InfoAdapter()
        recyclerview.layoutManager = LinearLayoutManager(this)
        recyclerview.adapter = mAdapter
    }

    fun readInfo(view: View) {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_CONTACTS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.READ_CONTACTS),
                requestCodeValue
            )
        } else {
            readContacts()
        }
    }

    /**
     * 读取手机的联系人信息
     */
    private fun readContacts() {
        var cursor: Cursor? = null
        try {
            cursor = contentResolver.query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                null,
                null,
                null,
                null
            )
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    // 获取联系人姓名
                    val userName =
                        cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))
                    // 获取联系人手机号
                    val userPhone =
                        cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                    val map: MutableMap<String, String> = mutableMapOf()
                    map["name"] = userName
                    map["phone"] = userPhone
                    infoList.add(map)
                }
                mAdapter!!.setInfoList(infoList)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            cursor?.close()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            requestCodeValue -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    readContacts()
                } else {
                    Toast.makeText(this, "由于你拒绝了此权限，所以导致获取联系人失败", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    /*############################### 获取手机联系人 end ###########################*/
    fun addData(view: View) {
        val uri = Uri.parse("content://com.steven.sunworld.provider/book")
        val values = ContentValues()
        values.put("name", "A Clash of kings")
        values.put("author", "George Martin")
        values.put("pages", 1040)
        values.put("price", 22.85)
        val newUri = contentResolver.insert(uri, values)
        val newId = newUri!!.pathSegments[1]
        Log.i("zhoufan", newId.toString())
    }

    fun queryData(view: View) {
        val uri = Uri.parse("content://com.steven.sunworld.provider/book")
        val cursor = contentResolver.query(uri, null, null, null, null)
        if (cursor != null) {
            while (cursor.moveToNext()) {
                val name = cursor.getString(cursor.getColumnIndex("name"))
                val author = cursor.getString(cursor.getColumnIndex("author"))
                val pages = cursor.getInt(cursor.getColumnIndex("pages"))
                val price = cursor.getDouble(cursor.getColumnIndex("author"))
                Log.i("zhoufan", name + author + pages + price)
            }
            cursor.close()
        }
    }
}
