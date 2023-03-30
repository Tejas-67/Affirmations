package com.example.affirmationsapp.model

import androidx.lifecycle.LiveData
import androidx.room.*


@Dao
interface AffirmationDao {

    @Query("SELECT * FROM affirmation")
    fun readAllData(): LiveData<List<Affirmation>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAffirmation(a: Affirmation)

    @Update
    suspend fun updateAffirmation(a: Affirmation)

    @Delete
    suspend fun deleteAffirmation(a: Affirmation)
}