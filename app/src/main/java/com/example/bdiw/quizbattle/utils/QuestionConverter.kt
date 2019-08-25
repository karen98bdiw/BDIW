package com.example.bdiw.quizbattle.utils

import androidx.room.TypeConverter
import com.example.bdiw.quizbattle.models.Question
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class QuestionConverter {

        @TypeConverter
        fun stringToTest(json:String):ArrayList<Question>{

            val type = object :TypeToken<ArrayList<Question>>() {}.type
            val gson = Gson()
            val questions = gson.fromJson<ArrayList<Question>>(json,type)

            return questions
        }

        @TypeConverter
        fun testToString(questions:ArrayList<Question>):String{

            val type = object:TypeToken<ArrayList<Question>>(){}.type
            val gson = Gson()

            val json = gson.toJson(questions,type)

            return json

        }




}