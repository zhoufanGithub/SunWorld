package com.steven.baselibrary.demo

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.steven.baselibrary.R
import com.steven.baselibrary.datapersistence.file.FileTool
import java.io.*


class FileActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_file)
    }

    fun createFile(view: View) {
        val result = FileTool.createFolderAndFileBaseDir("zhoufan/txt", "a.txt")
        if (result) {
            Toast.makeText(this, "文件创建成功", Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(this, "文件创建失败", Toast.LENGTH_LONG).show()
        }
    }

    fun deleteFile(view: View) {
        val path = FileTool.getBaseDirPath() + "zhoufan"
        val result = FileTool.deleteFile(path)
        if (result) {
            Toast.makeText(this, "文件删除成功", Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(this, "文件删除失败", Toast.LENGTH_LONG).show()
        }
    }

    fun writeData(view: View) {
        val path = FileTool.getBaseDirPath() + "zhoufan/txt/a.txt"
        val file = File(path)
        if (file.exists()) {
            val value = "12345678"
            val fileWriter = FileWriter(file)
            fileWriter.write(value)
            // 一定要加上这句，否则有可能写入失败
            fileWriter.flush()
            fileWriter.close()
        }
    }

    fun readData(view: View) {
        val path = FileTool.getBaseDirPath() + "zhoufan/txt/a.txt"
        val file = File(path)
        if (file.exists()) {
            val fileReader = FileReader(file)
            val bufferedReader = BufferedReader(fileReader, 1024)
            var readBuff: String? = null
            while (bufferedReader.readLine().also { readBuff = it } != null) {
                if (readBuff!!.isNotEmpty()) {
                    Toast.makeText(this, readBuff, Toast.LENGTH_LONG).show()
                }
            }
            bufferedReader.close()
        }
    }
}
