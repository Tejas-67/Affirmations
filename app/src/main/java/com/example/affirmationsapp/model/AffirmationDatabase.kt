package com.example.affirmationsapp.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters


@Database(entities = [Affirmation::class], version=1)
@TypeConverters(Convertors::class)
abstract class AffirmationDatabase : RoomDatabase(){
    abstract fun getDao(): AffirmationDao

    companion object {

        @Volatile
        private var INSTANCE: AffirmationDatabase? = null

        fun getDatabase(context: Context): AffirmationDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AffirmationDatabase::class.java,
                    "affirmation_database"
                ).allowMainThreadQueries().build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}