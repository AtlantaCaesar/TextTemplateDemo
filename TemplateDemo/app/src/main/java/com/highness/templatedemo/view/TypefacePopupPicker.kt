package com.highness.templatedemo.view

import android.annotation.TargetApi
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewAnimationUtils
import android.view.ViewGroup
import android.widget.TextView
import com.highness.templatedemo.R
import com.labo.kaji.relativepopupwindow.RelativePopupWindow

/**
 * Created by admin on 2018/5/11.
 */
class TypefacePopupPicker(context: Context) : RelativePopupWindow(context), View.OnClickListener {

    var onTypefaceSelected: OnTypefaceSelectedListener? = null

    init {
        contentView = LayoutInflater.from(context).inflate(R.layout.popup_typeface_picker, null)
        contentView.findViewById<TextView>(R.id.tv_kaiti).setOnClickListener(this)
        contentView.findViewById<TextView>(R.id.tv_fangzhengkatongjianti).setOnClickListener(this)
        contentView.findViewById<TextView>(R.id.tv_youyuan).setOnClickListener(this)
        width = ViewGroup.LayoutParams.WRAP_CONTENT
        height = ViewGroup.LayoutParams.WRAP_CONTENT
        isFocusable = true
        isOutsideTouchable = true
        setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            animationStyle = 0
        }
    }

    override fun showOnAnchor(anchor: View, vertPos: Int, horizPos: Int, x: Int, y: Int, fitInScreen: Boolean) {
        super.showOnAnchor(anchor, vertPos, horizPos, x, y, fitInScreen)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            circularReveal(anchor)
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private fun circularReveal(anchor: View) {
        contentView.apply {
            post {
                val myLocation = IntArray(2).apply { getLocationOnScreen(this) }
                val anchorLocation = IntArray(2).apply { anchor.getLocationOnScreen(this) }
                val cx = anchorLocation[0] - myLocation[0] + anchor.width/2
                val cy = anchorLocation[1] - myLocation[1] + anchor.height/2

                measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED)
                val dx = Math.max(cx, measuredWidth - cx)
                val dy = Math.max(cy, measuredHeight - cy)
                val finalRadius = Math.hypot(dx.toDouble(), dy.toDouble()).toFloat()
                ViewAnimationUtils.createCircularReveal(this, cx, cy, 0f, finalRadius).apply {
                    duration = 500
                    start()
                }
            }
        }
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.tv_kaiti -> onTypefaceSelected?.onSelected("0")
            R.id.tv_fangzhengkatongjianti -> onTypefaceSelected?.onSelected("1")
            R.id.tv_youyuan -> onTypefaceSelected?.onSelected("2")
        }
    }

    fun setOnTypefaceSelectedListener(onTypefaceSelectedListener: OnTypefaceSelectedListener){
        onTypefaceSelected = onTypefaceSelectedListener
    }

    interface OnTypefaceSelectedListener{
        fun onSelected(tag: String)
    }
}