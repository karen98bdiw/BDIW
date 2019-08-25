package com.example.bdiw.quizbattle.utils

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.bdiw.quizbattle.models.Test

@Dao
interface TestDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveTest(t: Test)

    @Query("SELECT * FROM Test")
    fun loadTests():List<Test>

}