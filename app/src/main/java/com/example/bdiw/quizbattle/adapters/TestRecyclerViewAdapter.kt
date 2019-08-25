package com.example.bdiw.quizbattle.adapters

import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.bdiw.R
import com.example.bdiw.quizbattle.models.Test
import kotlinx.android.synthetic.main.test_recycler_item.view.*
import java.lang.StringBuilder

class TestRecyclerViewAdapter(private val context:Context):RecyclerView.Adapter<TestRecyclerViewAdapter.ViewHolder>(){

    interface OnTestChooseListener{
        fun onTestChoose(t:Test)
        fun onTestUnchoose(t:Test)
    }

    lateinit var onTestChooseListener: OnTestChooseListener


    private val sb = StringBuilder()

    private val durationDescription = this.context.resources.getString(R.string.testDurationViewDescription)
    private val questionsCountDescription = this.context.resources.getString(R.string.testQuestionsCountViewDescription)
    private val creatorUsernameDescription = this.context.resources.getString(R.string.testCreaterUsernameViewDescription)
    private val testItemChoosenBackgroundColor = this.context.resources.getColor(R.color.testRecyclerItemOnChooseBackground)



    val map = mutableMapOf<View,Test>()
    var lastChoosed:View? = null
    private var isTestItemChoosed = false

    var onTestItemClickIterator = 0




    private val testes = arrayListOf<Test>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val inflater = LayoutInflater.from(parent.context)

        val view = inflater.inflate(R.layout.test_recycler_item,parent,false)


        return ViewHolder(view)

    }

    override fun getItemCount(): Int {
        return testes.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val curentTest = with(testes) { get(position) }

         map.put(holder.itemView,curentTest)


        holder.testNameView.text = curentTest.testName

        val testDurationForSet = this.sb
            .append(this.durationDescription)
            .append(" ")
            .append(curentTest.testDuration).toString()

        with(sb) {

            clear()
        }

        val testQuestionCountForSet = this.sb
            .append(this.questionsCountDescription)
            .append(" ")
            .append(curentTest.questions.size).toString()


        with(sb) {

            clear()
        }

        val testCreatorUsernameForSet = this.sb
            .append(this.creatorUsernameDescription)
            .append(" ")
            .append(curentTest.testCreator).toString()

        with(sb) {

            clear()
        }


        holder.testDurationView.text = testDurationForSet
        holder.testCreaterUsernameView.text = testCreatorUsernameForSet
        holder.testQuestionsCountView.text = testQuestionCountForSet



        holder.testItemView.setOnClickListener {



            if(lastChoosed == null){
                lastChoosed = holder.testItemView
            }

            if(lastChoosed == holder.testItemView){
                onTestItemClickIterator++
            }else if(!(lastChoosed == holder.testItemView) && !(isTestItemChoosed)){
                onTestItemClickIterator++
            }

            if(lastChoosed == null){
                lastChoosed = holder.testItemView
            }else{
                lastChoosed!!.setBackgroundColor(Color.TRANSPARENT)
                lastChoosed = holder.testItemView
            }





            isTestItemChoosed = !(onTestItemClickIterator%2 == 0)
            Log.e("clicked","cl${onTestItemClickIterator}")
            if(isTestItemChoosed){

                it.setBackgroundColor(testItemChoosenBackgroundColor)
                    this.onTestChooseListener.onTestChoose(curentTest)

            }else{
                it.setBackgroundColor(Color.TRANSPARENT)
                this.onTestChooseListener.onTestUnchoose(curentTest)
            }

            lastChoosed = holder.testItemView

        }

    }


    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){

        val testItemView = itemView.rootView
        val testNameView = itemView.testRecyclerTestNameView!!
        val testDurationView = itemView.testRecyclerTestDurationView!!
        val testQuestionsCountView = itemView.testRecyclerTestQuestionsCountView!!
        val testCreaterUsernameView = itemView.testRecyclerTestCreaterUsernameView!!
    }

    fun addTest(t:Test){
        testes.add(t)
    }
}

