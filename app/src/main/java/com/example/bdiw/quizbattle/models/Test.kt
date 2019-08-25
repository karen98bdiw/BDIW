package com.example.bdiw.quizbattle.models

import androidx.room.*
import com.example.bdiw.quizbattle.utils.QuestionConverter
import java.io.Serializable

@Entity
@TypeConverters(QuestionConverter::class)
class Test(
    val testName:String,
    val testDuration:String,
    val testCreator:String,
    val testLevel:String
) :Serializable{

    @PrimaryKey(autoGenerate = true)
    var id:Int = 0

    var questions = arrayListOf<Question>()

    fun addQuestion(question: Question){
        questions.add(question)
    }

    fun removeQuestion(question: Question){
        questions.remove(question)
    }



}