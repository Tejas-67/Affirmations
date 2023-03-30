package com.example.affirmationsapp.model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ViewModel(application: Application): AndroidViewModel(application) {
    var getAllData: LiveData<List<Affirmation>>
    private var repository: Repository

    init{
        val Dao=AffirmationDatabase.getDatabase(application).getDao()
        repository= Repository(Dao)
        getAllData=repository.readAllData
    }
    fun addAffirmation(a: Affirmation){
        viewModelScope.launch(Dispatchers.IO){
            repository.addAffirmation(a)
        }
    }
    fun updateAffirmation(a: Affirmation){
        viewModelScope.launch(Dispatchers.IO){
            repository.updateAffirmation(a)
        }
    }
    fun deleteAffirmation(a: Affirmation){
        viewModelScope.launch(Dispatchers.IO){
            repository.deleteAffirmation(a)
        }
    }
}