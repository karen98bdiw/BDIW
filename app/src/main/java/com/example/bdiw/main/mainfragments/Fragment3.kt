package com.example.bdiw.main.mainfragments

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bdiw.R
import com.example.bdiw.quizbattle.activities.CreateTestActivity
import com.example.bdiw.quizbattle.activities.TakeTestActivity
import com.example.bdiw.quizbattle.adapters.TestRecyclerViewAdapter
import com.example.bdiw.quizbattle.models.Test
import com.example.bdiw.quizbattle.models.TestModelForPost
import com.example.bdiw.quizbattle.utils.TestDatabase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.fragment3_view.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class Fragment3 : Fragment() {

    lateinit var adapter: TestRecyclerViewAdapter
    lateinit var manager: LinearLayoutManager
    lateinit var db: TestDatabase
    lateinit var mHandler:Handler
    lateinit var mAuth:FirebaseAuth
    lateinit var mReference:DatabaseReference
    var userChoosedTest: Test? = null
    val testes = arrayListOf<Test>()
    var i = 0

    companion object{
        val TEST_CODE = 10005
        val TAG = "RESULT TEST"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment3_view,container,false)
        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mAuth = FirebaseAuth.getInstance()
        mReference = FirebaseDatabase.getInstance().reference

        mHandler = Handler(Looper.getMainLooper())

        adapter = TestRecyclerViewAdapter(context!!)
        manager = LinearLayoutManager(context!!, RecyclerView.VERTICAL,false)

        if(userChoosedTest == null){
            goToTakeTestBtn.isEnabled = false
        }else{
            Log.e("choosed","${userChoosedTest!!.testName}")
        }

        adapter.onTestChooseListener = object : TestRecyclerViewAdapter.OnTestChooseListener {
            override fun onTestChoose(t: Test) {

                userChoosedTest = t
                goToTakeTestBtn.isEnabled = true

            }

            override fun onTestUnchoose(t:Test) {
                if(userChoosedTest == t){

                    userChoosedTest == null
                    goToTakeTestBtn.isEnabled = false

                }

            }


        }

        recyclerViewForTest.layoutManager = manager
        recyclerViewForTest.adapter = adapter

        db = TestDatabase.getInstance(context!!)

        goToCreateTestBtn.setOnClickListener {
            startActivityForResult(
                Intent(context, CreateTestActivity::class.java),
                TEST_CODE
            )
        }

        goToSendTestBtn.setOnClickListener {

            val creatorId = mAuth.currentUser!!.uid
            val receiverId = "someReceiverId"
            val test = userChoosedTest

            if(test != null){
                postTest(creatorId,receiverId,test)
            }else{
                Toast.makeText(context,"choose test..",Toast.LENGTH_LONG).show()
            }





        }

        goToTakeTestBtn.setOnClickListener {

            val intent = Intent(context, TakeTestActivity::class.java)
            intent.putExtra("t",userChoosedTest)

            startActivity(intent)

        }


        loadTest()

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == TEST_CODE){
            if(resultCode == Activity.RESULT_OK){
                if (data != null) {
                    val resultTest = data.getSerializableExtra("cT") as Test
                    drawTest(resultTest)

                    adapter.addTest(resultTest)
                    adapter.notifyItemInserted(adapter.itemCount)
                }
            }
        }
    }

    private fun drawTest(t:Test){
        Log.e(TAG,"${t.testName}:${t.testDuration}:${t.testCreator}")
        for(i in t.questions){
            Log.e(TAG,"${i.questionNumber}:${i.questonText}")
            for(j in i.options){
                Log.e(TAG,"${j.optionNumber}:${j.isThisOptionRight},${j.optionText}")
            }
        }
    }

    private fun loadTest(){


        GlobalScope.launch {
            val list = db.testDao().loadTests()

            for (t in list) {

                adapter.addTest(t)
            }

            val mRunnable = object: Runnable{
                override fun run() {
                    adapter.notifyItemInserted(adapter.itemCount)                }
            }

            mHandler.post(mRunnable)




        }
    }

    private fun postTest(creatorId:String,receiverId:String,test:Test){

        val testForPost = TestModelForPost(creatorId,receiverId,test)

        mReference.child("Quizes").push().setValue(testForPost).addOnCompleteListener {
            if(it.isSuccessful){
                Log.e("push","pushed")
            }else{
                Log.e("push","nonpushed${it.exception}")
            }
        }
    }

}