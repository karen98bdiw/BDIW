package com.example.bdiw.quizbattle.activities

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.bdiw.R
import com.example.bdiw.quizbattle.models.Question
import com.example.bdiw.quizbattle.models.Test
import com.example.bdiw.quizbattle.utils.TestDatabase
import com.example.bdiw.quizbattle.utils.Utils
import kotlinx.android.synthetic.main.activity_create_test.*
import kotlinx.android.synthetic.main.in_add_question_view.view.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async


class CreateTestActivity : AppCompatActivity() {

    lateinit var test:Test
    lateinit var questionList:ArrayList<Question>
    lateinit var inflater: LayoutInflater
    var iteratorForQuestionNumber = 0
    lateinit var alertDialogBuilder:AlertDialog.Builder
    lateinit var questionsMap: MutableMap<View,Question>
    private lateinit var db:TestDatabase

    companion object{
        val CODE = 10004
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_test)
        questionList = arrayListOf()

        inflater = LayoutInflater.from(this@CreateTestActivity)
        questionsMap = mutableMapOf()

        db = TestDatabase.getInstance(this@CreateTestActivity)

        goToCreateQuestionBtn.setOnClickListener {
            val goToCreateQuestionIntent = Intent(this@CreateTestActivity,CreateQuestionActivity::class.java)
            goToCreateQuestionIntent.putExtra("qls",questionList.size)
            startActivityForResult(goToCreateQuestionIntent, CODE)
        }

        createTestBtn.setOnClickListener {
            val intent = Intent()
            if(costumizeTest()){
                saveTest(test)
                intent.putExtra("cT",test)
                setResult(Activity.RESULT_OK,intent)
                finish()
            }

        }


    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == CODE){
            if(resultCode == Activity.RESULT_OK){
                if(data != null){
                    val resultQuestion = data.getSerializableExtra("cQ") as Question
                    questionList.add(resultQuestion)
                    drawQuestions(resultQuestion)
                }else{
                    Log.e("resultQuestionData","data is null")
                }

            }
        }
    }

    private fun drawQuestions(q:Question) {
        val view = inflater.inflate(R.layout.in_add_question_view,null)
        view.inAddQuestionTextShowView.text = q.questonText
        questionsMap.put(view,q)
        inAddQuestionsShowArea.addView(view)

    }



    private fun  costumizeTest():Boolean{
        if(Utils.validateEditing(inAddTestDurationInput,inAddTestNameInput)){
            val inputTestName = inAddTestNameInput.text.toString()
            val inputTestDuration = inAddTestDurationInput.text.toString()
            test = Test(inputTestName,inputTestDuration,"curentUser","level")
            test.questions = questionList
            return true

        }else{
            Toast.makeText(this@CreateTestActivity,"Wrong...",Toast.LENGTH_LONG).show()
            return false
        }
    }

    private  fun saveTest(t:Test){
        GlobalScope.async {
            db.testDao().saveTest(t)
        }
    }



}
