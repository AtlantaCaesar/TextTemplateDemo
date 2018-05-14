package com.highness.templatedemo.ui

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import android.view.View
import android.widget.TextView
import com.highness.templatedemo.R
import com.highness.templatedemo.view.AligningPopupPicker
import com.highness.templatedemo.view.SizePicker
import com.highness.templatedemo.view.TypefacePopupPicker
import com.jaredrummler.android.colorpicker.ColorPickerDialog
import com.jaredrummler.android.colorpicker.ColorPickerDialogListener
import com.labo.kaji.relativepopupwindow.RelativePopupWindow
import kotlinx.android.synthetic.main.activity_edit.*
import java.io.ByteArrayOutputStream

/**
 * Created by admin on 2018/5/8.
 */
class EditActivity : AppCompatActivity(), View.OnClickListener, ColorPickerDialogListener, TypefacePopupPicker.OnTypefaceSelectedListener, SizePicker.OnSizeChangeListener, AligningPopupPicker.OnAligningSelectedListener {

    var textView: TextView? = null
    lateinit var sizePicker: SizePicker
    lateinit var typefacePicker: TypefacePopupPicker
    lateinit var aligningPicker: AligningPopupPicker

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)
        initView()
    }

    override fun onResume() {
        super.onResume()
    }

    private fun initView() {
        iv_back.setOnClickListener(this)
        rl_color_select.setOnClickListener(this)
        rl_size_select.setOnClickListener(this)
        rl_typeface_select.setOnClickListener(this)
        rl_aligning_select.setOnClickListener(this)
        tv_top.setOnClickListener(this)
        tv_bottom.setOnClickListener(this)
        tv_preview.setOnClickListener(this)
        tv_top.setOnLongClickListener { topLongClick() }
        tv_bottom.setOnLongClickListener { bottomLongClick() }
    }


    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.iv_back -> finish()
            R.id.rl_color_select -> colorEdit()
            R.id.rl_size_select -> sizeEdit()
            R.id.rl_typeface_select -> typefaceEdit()
            R.id.rl_aligning_select -> aligningEdit()
            R.id.tv_top -> doEditTextTop()
            R.id.tv_bottom -> doEditTextBottom()
            R.id.tv_preview -> doPreview()
        }
    }

    /**
     * 对顶部文字进行编辑
     */
    private fun doEditTextTop() {
        textView = tv_top
        tv_top.setBackgroundResource(R.drawable.bg_text_click)
        tv_bottom.setBackgroundResource(R.drawable.bg_text_edit)
        val bundle = Bundle()
        bundle.putString("edit_text", tv_top.text.toString())
        val intent = Intent()
        intent.putExtras(bundle)
        intent.setClass(this, AddTextActivity::class.java)
        startActivityForResult(intent, 0)
    }

    /**
     * 对底部文字进行编辑
     */
    private fun doEditTextBottom() {
        textView = tv_bottom
        tv_top.setBackgroundResource(R.drawable.bg_text_edit)
        tv_bottom.setBackgroundResource(R.drawable.bg_text_click)
        val bundle = Bundle()
        bundle.putString("edit_text", tv_bottom.text.toString())
        val intent = Intent()
        intent.putExtras(bundle)
        intent.setClass(this, AddTextActivity::class.java)
        startActivityForResult(intent, 1)
    }

    /**
     * 顶部文字框长按处理（长按后，代表选中此文字框，可以进行颜色，大小，字体的编辑）
     */
    private fun topLongClick(): Boolean {
        textView = tv_top
        tv_top.setBackgroundResource(R.drawable.bg_text_click)
        tv_bottom.setBackgroundResource(R.drawable.bg_text_edit)
        return true
    }

    /**
     * 底部文字框长按处理
     */
    private fun bottomLongClick(): Boolean {
        textView = tv_bottom
        tv_top.setBackgroundResource(R.drawable.bg_text_edit)
        tv_bottom.setBackgroundResource(R.drawable.bg_text_click)
        return true
    }

    /**
     * 颜色选择
     */
    private fun colorEdit() {
        ColorPickerDialog.newBuilder()
                .setDialogTitle(R.string.color_picker_dialog_title)
                .setSelectedButtonText(R.string.color_picker_dialog_select_button_text)
                .setDialogType(ColorPickerDialog.TYPE_CUSTOM)
                .setAllowPresets(false)
                .setDialogId(0)
                .setColor(Color.BLACK)
                .setShowAlphaSlider(true)
                .show(this)
    }

    /**
     * 字体选择
     */
    private fun typefaceEdit() {
        typefacePicker = TypefacePopupPicker(this)
        typefacePicker.setOnTypefaceSelectedListener(this)
        typefacePicker.showOnAnchor(rl_typeface_select, RelativePopupWindow.VerticalPosition.ABOVE, RelativePopupWindow.HorizontalPosition.CENTER, 0, 0, false)
    }

    /**
     * 大小选择
     */
    private fun sizeEdit() {
        sizePicker = SizePicker()
        sizePicker.setOnSizeChangeListener(this)
        sizePicker.show(supportFragmentManager, "EditActivity")
    }

    private fun aligningEdit(){
        aligningPicker = AligningPopupPicker(this)
        aligningPicker.setOnAligningSelectedListener(this)
        aligningPicker.showOnAnchor(rl_aligning_select, RelativePopupWindow.VerticalPosition.ABOVE, RelativePopupWindow.HorizontalPosition.CENTER, 0, 0, false)
    }

    /**
     * 预览操作
     */
    private fun doPreview() {
        tv_top.background = null
        tv_bottom.background = null
        val bitmap = viewToBitmap(constraint_view)
        val baos = ByteArrayOutputStream()
        bitmap?.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val datas = baos.toByteArray()
        val bundle = Bundle()
        bundle.putByteArray("bitmap", datas)
        val intent = Intent()
        intent.setClass(this, PreviewActivity::class.java)
        intent.putExtras(bundle)
        startActivity(intent)
    }

    /**
     * viwe转换为bitmap
     */
    private fun viewToBitmap(view: View): Bitmap? {
        var bitmap: Bitmap? = null
        try {
            val width = view.width
            val height = view.height
            if (width != 0 && height != 0) {
                bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
                val canvas = Canvas(bitmap)
                view.draw(canvas)
            }
        } catch (e: Exception) {
            bitmap = null
            e.stackTrace
        }

        return bitmap
    }

    override fun onColorSelected(dialogId: Int, color: Int) {
        when(dialogId){
            0 -> textView?.setTextColor(color)
        }
    }

    override fun onSizeChanged(size: Int) {
        textView?.setTextSize(size.toFloat())
    }

    override fun onDialogDismissed(dialogId: Int) {

    }

    override fun onSelected(tag: String) {
        typefacePicker?.dismiss()
        when(tag){
            "0" -> textView?.setTypeface(Typeface.createFromAsset(assets, "fonts/kaiti.ttf"))
            "1" -> textView?.setTypeface(Typeface.createFromAsset(assets, "fonts/fangzhengkatongjianti.ttf"))
            "2" -> textView?.setTypeface(Typeface.createFromAsset(assets, "fonts/youyuan.ttf"))
        }
    }

    override fun aligningSelected(tag: Int) {
        aligningPicker?.dismiss()
        when(tag){
            0 -> textView?.gravity = Gravity.CENTER
            1 -> textView?.gravity = Gravity.LEFT
            2 -> textView?.gravity = Gravity.RIGHT
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val bundle = data?.extras
        if (bundle == null) return
        when (requestCode) {
            0 -> tv_top.text = bundle.getString("text")
            1 -> tv_bottom.text = bundle.getString("text")
        }
    }

}