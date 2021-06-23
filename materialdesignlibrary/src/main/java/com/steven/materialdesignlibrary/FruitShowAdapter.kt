package com.steven.materialdesignlibrary

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

/**
 * @auther: zhoufan
 * @data: 2021/6/11 19:01
 * @content:
 */
private class FruitShowAdapter(private val mFruitList: List<Fruit>) :
    RecyclerView.Adapter<FruitShowAdapter.ViewHolder>() {
    private var mContext: Context? = null

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var cardView: CardView
        var fruitImage: ImageView
        var fruitName: TextView

        init {
            cardView = view as CardView
            fruitImage = view.findViewById(R.id.fruit_image)
            fruitName = view.findViewById(R.id.fruit_name)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        if (mContext == null) {
            mContext = parent.context
        }
        val view = LayoutInflater.from(mContext).inflate(R.layout.fruit_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val fruit = mFruitList[position]
        holder.fruitName.text = fruit.name
        Glide.with(mContext!!).load(fruit.imageId).into(holder.fruitImage)
    }

    override fun getItemCount(): Int {
        return mFruitList.size
    }
}