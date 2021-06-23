package com.steven.baselibrary.contentprovider

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.steven.baselibrary.R

/**
 * author: zhoufan
 * data: 2021/6/18 15:33
 * content:
 */
class InfoAdapter : RecyclerView.Adapter<InfoAdapter.MyViewHolder>() {

    private var infoList: MutableList<MutableMap<String, String>> = mutableListOf()

    fun setInfoList(info: MutableList<MutableMap<String, String>>) {
        infoList.addAll(info)
        notifyDataSetChanged()
    }

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.adapter_info,parent,false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        (holder.itemView.findViewById(R.id.user_name) as TextView).text = infoList[position]["name"]
        (holder.itemView.findViewById(R.id.user_phone) as TextView).text = infoList[position]["phone"]
    }

    override fun getItemCount() = infoList.size
}