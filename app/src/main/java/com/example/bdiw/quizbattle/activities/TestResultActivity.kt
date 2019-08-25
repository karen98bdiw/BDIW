package com.example.bdiw.quizbattle.activities

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.TextView
import com.example.bdiw.R
import com.example.bdiw.quizbattle.models.Question
import com.example.bdiw.quizbattle.models.Test
import kotlinx.android.synthetic.main.activity_test_result.*

class TestResultActivity : AppCompatActivity() {

    lateinit var curentQuestion: Question
    lateinit var inflater:LayoutInflater
    var rightAnswersCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_result)

        inflater = LayoutInflater.from(this@TestResultActivity)

        val tT = intent.getSerializableExtra("tT") as Test
        val tH = intent.getSerializableExtra("tH") as MutableMap<Int, Int>

        val sH = tH.toSortedMap()

        val list = tT.questions

        for((i,j) in sH){
            if(list.get(i-1).rightOptionNumber() == j){
                Log.e("answer","right")
                rightAnswersCount++
            }else{
                Log.e("answer","false")
            }

            curentQuestion = list.get(i-1)

            val qV = TextView(this@TestResultActivity)

            qV.setText(curentQuestion.questonText)

            resultHistoryShowArea.addView(qV)

            for(o in curentQuestion.options){
                val view = TextView(this@TestResultActivity)
                view.setText(o.optionText)

                if(o.optionNumber == j){
                    view.setBackgroundColor(Color.RED)
                }

                if(curentQuestion.rightOptionNumber() == o.optionNumber){
                    view.setBackgroundColor(Color.GREEN)
                }

                resultHistoryShowArea.addView(view)

            }

        }

        resultRightAnswersCountShowView.setText("yout result is ${rightAnswersCount}/${tT.questions.size}")





    }
}
