package com.highness.templatedemo.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.highness.templatedemo.R
import kotlinx.android.synthetic.main.activity_add_text.*

/**
 * Created by admin on 2018/5/8.
 */
class AddTextActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_text)
        initBundle()
        initView()
    }

    private fun initBundle() {
        val bundle = intent.extras
        if (bundle == null) return
        et_input_text.setText(bundle.getString("edit_text"))
    }

    private fun initView() {
        iv_back.setOnClickListener(this)
        tv_finish.setOnClickListener(this)
        et_input_text.setSelection(et_input_text.text.length)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.iv_back -> finish()
            R.id.tv_finish -> doFinish()
        }
    }

    private fun doFinish() {
        if (et_input_text.text.toString().trim().isEmpty()) return
        val bundle = Bundle()
        bundle.putString("text", et_input_text.text.toString().trim())
        val intent = Intent()
        intent.putExtras(bundle)
        setResult(Activity.RESULT_OK, intent)
        finish()
    }
}