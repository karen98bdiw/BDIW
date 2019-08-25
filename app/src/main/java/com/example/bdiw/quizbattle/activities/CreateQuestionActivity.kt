package com.example.bdiw.quizbattle.activities

import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.example.bdiw.R
import com.example.bdiw.quizbattle.models.Question
import com.example.bdiw.quizbattle.models.Option
import com.example.bdiw.quizbattle.utils.Utils
import com.example.bdiw.quizbattle.utils.Utils.Companion.validateEditing as validate

import kotlinx.android.synthetic.main.activity_create_question.*
import kotlinx.android.synthetic.main.ask_option_diolog_view.view.*
import kotlinx.android.synthetic.main.in_add_option_view.view.*
import kotlin.collections.ArrayList

class CreateQuestionActivity : AppCompatActivity() {

    private lateinit var optionsList:ArrayList<Option>
    private lateinit var question: Question
    private var questionName = "non taked from user"
    private var questionNumber = 0
    private lateinit var alertDialogBuilder:AlertDialog.Builder
    private lateinit var inflater:LayoutInflater
    private lateinit var map:MutableMap<View,Option>
    private var optionIterator = 1

    private lateinit var returnIntent: Intent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_question)

        returnIntent = Intent()
        optionsList = arrayListOf<Option>()
        map = mutableMapOf()

        questionNumber = intent.getIntExtra("qls",0) + 1

        optionIterator = 1

        alertDialogBuilder = AlertDialog.Builder(this@CreateQuestionActivity)
        inflater = LayoutInflater.from(this@CreateQuestionActivity)

        createQuestionBtn.setOnClickListener {
            if(Utils.validateEditing(inAddQuestionNameInput)){
                setQuestionName()
                checkRightOptions()
                getOptionsFromHashMapToSaveList()
                if (validateQuestion()){
                    question = Question(questionNumber,questionName)
                    question.options = optionsList
                    returnIntent.putExtra("cQ",question)
                    this@CreateQuestionActivity.setResult(Activity.RESULT_OK,returnIntent)
                    finish()
                }
            }


        }

        createOptionBtn.setOnClickListener {
            askForOption()
        }

    }

    private fun validateQuestion(): Boolean {
      return true
    }

    private fun askForOption() {
        val diologView = inflater.inflate(R.layout.ask_option_diolog_view,null)
        alertDialogBuilder
            .setView(diologView)
            .setPositiveButton("ok",object:DialogInterface.OnClickListener{
                override fun onClick(dialog: DialogInterface?, which: Int) {
                  if(validate(diologView.optionDiologOptionTextInput)){
                       addOptionForCreating(diologView.optionDiologOptionTextInput.text.toString())
                  }
                }

            }).show()
    }

    private fun setQuestionName() {
        questionName = inAddQuestionNameInput.text.toString()
    }

    private fun addOptionForCreating(text:String){
        val view = inflater.inflate(R.layout.in_add_option_view,null)
        view.inAddOptionTextShowView.text = text
        val option = Option(optionIterator,text,false)

        map.put(view,option)
        inAddQuestionOptionsShowArea.addView(view)

        Log.e("iterator","$optionIterator")
        optionIterator++
    }

    private fun checkRightOptions(){
        for((v,o) in map ){
            val isOptionChecked = v.optionCheckBox.isChecked

            if(isOptionChecked){
                o.isThisOptionRight = true
            }

        }

    }

    private fun getOptionsFromHashMapToSaveList(){
        for((v,o) in map){
            Log.e("optionsFromMap","${o.optionNumber}")
            optionsList.add(o)
        }
        val newList = map.values
        for(i in newList){
            Log.e("newList","${i.optionNumber}")
        }
    }

}
