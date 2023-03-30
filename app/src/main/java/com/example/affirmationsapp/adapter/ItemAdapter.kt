package com.example.affirmationsapp.adapter

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.graphics.drawable.toDrawable
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.affirmationsapp.Fragments.ListFragmentDirections
import com.example.affirmationsapp.R
import com.example.affirmationsapp.model.Affirmation
import com.example.affirmationsapp.model.Convertors
import com.example.affirmationsapp.model.ViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ItemAdapter(val v : ViewModel): RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {
    private var list= emptyList<Affirmation>()

    class ItemViewHolder(private val view: View) : RecyclerView.ViewHolder(view){
        val textview: TextView=view.findViewById(R.id.item_title)
        val imageview: ImageView =view.findViewById((R.id.item_image))
        val deleteButton: FloatingActionButton=view.findViewById(R.id.delteButton)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {

        val adapterLayout = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return ItemViewHolder(adapterLayout)

    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = list[position]
        holder.textview.text = item.text.toString()
        holder.imageview.setImageBitmap(getBitmap(item.image!!))
        holder.imageview.setOnClickListener {
            val action=ListFragmentDirections.actionListFragmentToUpdateFragment(item)
            holder.itemView.findNavController().navigate(action)
        }
        holder.textview.setOnClickListener {
            val action=ListFragmentDirections.actionListFragmentToUpdateFragment(affirmation = item)
            holder.itemView.findNavController().navigate(action)
        }
        holder.deleteButton.setOnClickListener {
            v.deleteAffirmation(item)
        }


    }
    private fun getBitmap(blob: ByteArray): Bitmap{
        return BitmapFactory.decodeByteArray(blob, 0, blob.size);
    }
    fun setData(list: List<Affirmation>){
        this.list=list
        notifyDataSetChanged()
    }
}