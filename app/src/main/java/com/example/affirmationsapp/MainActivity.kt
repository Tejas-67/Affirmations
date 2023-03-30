package com.example.affirmationsapp

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.graphics.drawable.toBitmap
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.affirmationsapp.adapter.ItemAdapter
import com.example.affirmationsapp.databinding.ActivityMainBinding
import com.example.affirmationsapp.model.Affirmation
import com.example.affirmationsapp.model.AffirmationDao
import com.example.affirmationsapp.model.AffirmationDatabase
import com.example.affirmationsapp.model.Convertors
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream

const val Tag="MainActivity"
const val CAMERA_REQUEST_CODE=0
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }
}