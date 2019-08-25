package com.example.bdiw.quizbattle.utils

import android.content.Context
import androidx.room.*
import com.example.bdiw.quizbattle.models.Test

@Database(entities = arrayOf(Test::class),version = 1)
abstract class TestDatabase() : RoomDatabase(){

    abstract fun testDao():TestDao

    companion object{
        private var INSTANCT:TestDatabase? = null
        fun getInstance(context:Context):TestDatabase{
            if(INSTANCT == null){
                INSTANCT = Room.databaseBuilder(
                    context,
                    TestDatabase::class.java,
                   "testdb"
                ).build()
            }
            return INSTANCT as TestDatabase
        }
    }


}