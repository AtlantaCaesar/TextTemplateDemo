package com.highness.templatedemo.view

import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.*
import android.widget.SeekBar
import com.highness.templatedemo.R
import kotlinx.android.synthetic.main.dialog_size_picker.*

/**
 * Created by admin on 2018/5/11.
 */
class SizePicker: DialogFragment(), SeekBar.OnSeekBarChangeListener{

    var onSizeChangedListener: OnSizeChangeListener? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.dialog_size_picker, container, false)
        return view
    }

    override fun onStart() {
        super.onStart()
        val window = dialog.window
        window.setBackgroundDrawableResource(R.color.transparent)
        val wlp = window.attributes
        wlp.gravity = Gravity.BOTTOM
        wlp.width = WindowManager.LayoutParams.MATCH_PARENT
        wlp.height = WindowManager.LayoutParams.WRAP_CONTENT
        window.attributes = wlp
        seek_bar_size.progress = 16
        seek_bar_size.setOnSeekBarChangeListener(this)
    }

    override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
        tv_size_value.setText("size: " + progress)
        onSizeChangedListener?.onSizeChanged(progress)
    }

    override fun onStartTrackingTouch(seekBar: SeekBar?) {
    }

    override fun onStopTrackingTouch(seekBar: SeekBar?) {
    }

    fun setOnSizeChangeListener(onSizeChangeListener: OnSizeChangeListener){
        onSizeChangedListener = onSizeChangeListener
    }

    interface OnSizeChangeListener{
        fun onSizeChanged(size: Int)
    }
}