package com.example.bdiw.Activities

import com.example.bdiw.R
import android.content.Context
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bdiw.Adapters.MessageRecyclerViewAdapter
import com.example.bdiw.Models.Chat
import com.example.bdiw.Models.Message
import com.example.bdiw.Models.User
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_chat.*
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class ChatActivity : AppCompatActivity() {

    lateinit var mAuth: FirebaseAuth
    lateinit var mDatabase:FirebaseDatabase
    var notify = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)



        mAuth = FirebaseAuth.getInstance()
        mDatabase = FirebaseDatabase.getInstance()
        val  imm:InputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

        val messagesList = ArrayList<Message>()
        val manager = LinearLayoutManager(this,RecyclerView.VERTICAL,false)
        manager.stackFromEnd = true
        messagesRecyclerView.layoutManager = manager

        val intent = getIntent()

        val receiverUser = intent.getSerializableExtra("receiverUser") as User
        val curentUser = mAuth.currentUser

        Log.e("receiverUser","${receiverUser.name}")

        sendMessageBtn.setOnClickListener {




            val messageText = messageInputView.text.toString()




            if(messageText.isNotEmpty()){
                mDatabase.reference.child("chat").push().setValue(Message(messageText,receiverUser.id,curentUser!!.uid)).addOnCompleteListener {
                    if(it.isSuccessful){
                        imm.hideSoftInputFromWindow(chatLayout.windowToken,0)
                        Log.e("sending","message is send")

                    }else{
                        Log.e("sending","cant sand ${it.exception}")
                    }
                }
            }



        }

        mDatabase.reference.child("chat").addValueEventListener(object:ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                messagesList.clear()
                p0.children.forEach {
                    val message = it.getValue(Message::class.java)
                    if(message != null){
                        if(message.sender == curentUser?.uid && message.receiver == receiverUser.id || message.sender == receiverUser.id && message.receiver == curentUser?.uid){

                            messagesList.add(message)
                            messagesRecyclerView.scrollToPosition(messagesList.size)
                            messagesRecyclerView.smoothScrollToPosition(messagesList.size)
                            Log.e("scroled","${messagesList.lastIndex}")
                        }
                    }
                }
                messagesRecyclerView.adapter = MessageRecyclerViewAdapter(messagesList)
            }

        })



    }




}
