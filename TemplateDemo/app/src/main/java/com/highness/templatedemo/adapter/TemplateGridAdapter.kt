package com.highness.templatedemo.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import com.highness.templatedemo.R

/**
 * Created by admin on 2018/5/7.
 */
class TemplateGridAdapter(var mList: List<Int>, var mContext: Context): BaseAdapter() {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var holder: MyViewHolder
        var v: View
        if (convertView == null){
            holder = MyViewHolder()
            v = LayoutInflater.from(mContext).inflate(R.layout.item_template_grid, parent, false)
            holder.imageView = v.findViewById<ImageView>(R.id.iv_template_cover)
            v.tag = holder
        } else {
            v = convertView
            holder = v.tag as MyViewHolder
        }
        holder.imageView.setImageResource(mList[position])
        return v
    }

    override fun getItem(position: Int): Any {
        return mList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return mList.size
    }

    class MyViewHolder{
        lateinit var imageView: ImageView
    }
}