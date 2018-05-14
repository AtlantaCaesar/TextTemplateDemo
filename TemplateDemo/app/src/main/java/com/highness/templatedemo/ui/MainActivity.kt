package com.highness.templatedemo.ui

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.highness.templatedemo.R
import com.highness.templatedemo.adapter.TemplateGridAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setData()
        setItemClickListener()
    }

    fun setData(){
        val list = mutableListOf<Int>(
                R.drawable.template_cover,
                R.drawable.template_cover,
                R.drawable.template_cover,
                R.drawable.template_cover,
                R.drawable.template_cover,
                R.drawable.template_cover,
                R.drawable.template_cover,
                R.drawable.template_cover,
                R.drawable.template_cover,
                R.drawable.template_cover
        )
        val adapter = TemplateGridAdapter(list, this)
        gridview_template.adapter = adapter
    }

    fun setItemClickListener(){
        gridview_template.setOnItemClickListener { parent, view, position, id -> startActivity(Intent(this, EditActivity::class.java)) }
    }
}
