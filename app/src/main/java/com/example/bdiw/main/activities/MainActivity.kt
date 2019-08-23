package com.example.bdiw.main.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.example.bdiw.communication.models.User
import com.example.bdiw.R
import com.example.bdiw.main.utils.Utils
import com.google.android.material.navigation.NavigationView
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(),NavigationView.OnNavigationItemSelectedListener {

    lateinit var mAut: FirebaseAuth
    var curentUser: FirebaseUser?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolBar)

        FirebaseApp.initializeApp(this)

        //Main Activity Construct building

        ///>

        //Firebase checking user status
        mAut = FirebaseAuth.getInstance()
        curentUser = FirebaseAuth.getInstance().currentUser

        if (curentUser != null) {
            Utils.constructToolBar(toolBar, drawerLayout)
            Utils.constructMainViewPager(viewPager, supportFragmentManager)
            Utils.constructBottomNavigationView(bottomNavigationView)
            tabLayout.setupWithViewPager(viewPager)
            navigationView.setNavigationItemSelectedListener(this)
            setOnNavigationItemSelectedListener()
//            startActivity(Intent(this@MainActivity,HomeActivity::class.java))
        }else{
            startActivity(Intent(this@MainActivity,SignUpActivity::class.java))
        }


        ///>


        /////costumize navigation view header for curent user
        val reference = FirebaseDatabase.getInstance().reference.child("users")
            .addValueEventListener(object :ValueEventListener{
                override fun onCancelled(p0: DatabaseError) {

                }

                override fun onDataChange(p0: DataSnapshot) {

                    p0.children.forEach {
                        val user = it.getValue(User::class.java)
                        if(user!=null && curentUser!=null){
                            if(user.id.equals(curentUser!!.uid)){
                                val name = user.name
                                val surname = user.surname
                                Utils.costumizeNavigationView(navigationView,name,surname)
                            }
                        }

                    }

                }

            })





    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.tool_bar_menu,menu)
        return true

    }

    override fun onNavigationItemSelected(p0: MenuItem): Boolean {

        when(p0.itemId){
            R.id.item1 -> Toast.makeText(this@MainActivity,"navigationMenu:Item1 Selected",Toast.LENGTH_LONG).show()
            R.id.item2 -> Toast.makeText(this@MainActivity,"navigationMenu:Item2 Selected",Toast.LENGTH_LONG).show()
            R.id.item3 -> Toast.makeText(this@MainActivity,"navigationMenu:Item3 Selected",Toast.LENGTH_LONG).show()
            R.id.item4 -> Toast.makeText(this@MainActivity,"navigationMenu:Item4 Selected",Toast.LENGTH_LONG).show()
            else -> Toast.makeText(this@MainActivity,"navigationMenu:NoItem",Toast.LENGTH_LONG).show()
        }

        return true

    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        when(item?.itemId){
            R.id.searcht -> Toast.makeText(this@MainActivity,"toolBar:Search Item Selected",Toast.LENGTH_LONG).show()
            R.id.item1t -> Toast.makeText(this@MainActivity,"toolBar:Item1 Selected",Toast.LENGTH_LONG).show()
            R.id.item2t -> Toast.makeText(this@MainActivity,"toolBar:Item2 Selected",Toast.LENGTH_LONG).show()
            R.id.toolBarSignOutItem -> signOut()
            else -> Toast.makeText(this@MainActivity,"toolBar:NoItem",Toast.LENGTH_LONG).show()
        }

        return super.onOptionsItemSelected(item)
    }



    fun setOnNavigationItemSelectedListener(){
        bottomNavigationView.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.item1b -> Toast.makeText(this@MainActivity,"bottomMenu:Item1 Selected",Toast.LENGTH_LONG).show()
                R.id.item2b -> Toast.makeText(this@MainActivity,"bottomMenu:Item2 Selected",Toast.LENGTH_LONG).show()
                R.id.item3b -> Toast.makeText(this@MainActivity,"bottomMenu:Item3 Selected",Toast.LENGTH_LONG).show()
                else -> Toast.makeText(this@MainActivity,"bottomMenu:NoItem",Toast.LENGTH_LONG).show()

            }
            false
        }
    }

    fun signOut(){
        mAut.signOut()
        startActivity(Intent(this@MainActivity,SignUpActivity::class.java))
        finish()
    }



}

