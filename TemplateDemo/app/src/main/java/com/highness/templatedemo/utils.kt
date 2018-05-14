package com.highness.templatedemo

import android.content.Context
import android.widget.Toast


/**
 * Created by admin on 2018/5/8.
 */
fun Context.toast(message: CharSequence, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, duration).show()
}
