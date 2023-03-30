package com.example.affirmationsapp.model

import androidx.lifecycle.LiveData

class Repository(private var affirmationDao: AffirmationDao) {

    val readAllData: LiveData<List<Affirmation>> = affirmationDao.readAllData()

    suspend fun addAffirmation(a: Affirmation){
        affirmationDao.insertAffirmation(a)
    }
    suspend fun updateAffirmation(a: Affirmation){
        affirmationDao.updateAffirmation(a);
    }
    suspend fun deleteAffirmation(a: Affirmation){
        affirmationDao.deleteAffirmation(a)
    }
}