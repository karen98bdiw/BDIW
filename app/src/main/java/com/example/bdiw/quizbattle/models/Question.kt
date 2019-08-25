package com.example.bdiw.quizbattle.models

import java.io.Serializable

class Question(var questionNumber:Int,var questonText:String):Serializable {

    var options = arrayListOf<Option>()


    fun addOption(option: Option){
        options.add(option)
    }

    fun removeOption(option: Option){
        options.remove(option)
    }

    public fun rightOptionNumber():Int{
        for (o in options){
            if (o.isThisOptionRight){
                return o.optionNumber
            }
        }
        return 0
    }

}