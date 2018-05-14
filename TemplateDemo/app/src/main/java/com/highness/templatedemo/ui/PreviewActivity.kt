package com.highness.templatedemo.ui

import android.graphics.BitmapFactory
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.highness.templatedemo.R
import com.highness.templatedemo.toast
import kotlinx.android.synthetic.main.activity_preview.*

/**
 * Created by admin on 2018/5/8.
 */
class PreviewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_preview)
        initBundle()
        initView()
    }

    private fun initBundle(){
        val bundle = intent.extras
        if (bundle == null) return
        val datas = bundle.getByteArray("bitmap")
        val bitmap = BitmapFactory.decodeByteArray(datas, 0, datas.size)
        iv_preview.setImageBitmap(bitmap)
    }

    private fun initView() {
        iv_back.setOnClickListener { finish() }
        tv_uploading.setOnClickListener { toast("上传并保存") }
    }

}