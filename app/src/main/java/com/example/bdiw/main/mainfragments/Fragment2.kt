package com.example.bdiw.main.mainfragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bdiw.communication.adapters.UsersRecyclerViewAdapter
import com.example.bdiw.communication.models.Message
import com.example.bdiw.communication.models.User
import com.example.bdiw.R
import com.example.bdiw.main.activities.SignUpActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.fragment2_view.view.*

class Fragment2 : Fragment() {

    lateinit var mAut: FirebaseAuth
    lateinit var mDatabase: FirebaseDatabase
    var curentUser: FirebaseUser?=null
    lateinit var users:ArrayList<User>
    lateinit var adapter: UsersRecyclerViewAdapter
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment2_view,container,false)

        mAut = FirebaseAuth.getInstance()
        mDatabase = FirebaseDatabase.getInstance()
        curentUser = FirebaseAuth.getInstance().currentUser
        users = arrayListOf()
        adapter = UsersRecyclerViewAdapter(users)

        view.usersRecyclerView.layoutManager = LinearLayoutManager(this.context, RecyclerView.VERTICAL,false)


        if(curentUser == null){
            Log.e("isSign","no")
            val intent = Intent(this.context, SignUpActivity::class.java)
            startActivity(intent)
        }else{
            mDatabase.reference.child("users").addValueEventListener(object: ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {

                }

                override fun onDataChange(p0: DataSnapshot) {
                    p0.children.forEach {
                        val user: User? = it.getValue(User::class.java)
                        if(!user!!.id.equals(curentUser!!.uid)){
                            Log.e("users","user:${user.id},name:${user.name}")
                            users.add(user)
                        }
                    }
                    view.usersRecyclerView.adapter = adapter
                }

            })

        }



        mDatabase.reference.child("chat").addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {

                p0.children.forEach {
                    val message = it.getValue(Message::class.java)

                    if (message != null) {
                        if(message!!.receiver.equals(curentUser?.uid)){
                        }
                    }else{
                        Log.e("null","message is null")
                    }

                }

            }

        })
        return view
    }


}