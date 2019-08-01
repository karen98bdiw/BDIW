package com.example.bdiw

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.viewpager.widget.ViewPager
import com.google.android.material.navigation.NavigationView
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(),NavigationView.OnNavigationItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolBar)
        Utils.constructMainViewPager(viewPager,supportFragmentManager)
        tabLayout.setupWithViewPager(viewPager)
        navigationView.setNavigationItemSelectedListener(this)
        bottomNavigationView.setTextVisibility(false)
        bottomNavigationView.enableShiftingMode(false)
        bottomNavigationView.enableItemShiftingMode(false)
        bottomNavigationView.enableAnimation(false)
        for(i in 0 until  bottomNavigationView.menu.size()){
            bottomNavigationView.setIconTintList(i,null)
        }

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
            R.id.item3t -> Toast.makeText(this@MainActivity,"toolBar:Item3 Selected",Toast.LENGTH_LONG).show()
            else -> Toast.makeText(this@MainActivity,"toolBar:NoItem",Toast.LENGTH_LONG).show()
        }

        return super.onOptionsItemSelected(item)
    }


}

