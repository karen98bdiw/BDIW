package com.example.bdiw.quizbattle.utils

import android.text.TextUtils
import android.widget.EditText

class Utils {

    companion object{
         fun validateEditing(vararg views: EditText):Boolean{
            views.forEach {
                if(TextUtils.isEmpty(it.text)){
                    return false
                }
            }
            return true
        }
    }

}