package com.example.affirmationsapp.Fragments

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.affirmationsapp.R
import com.example.affirmationsapp.databinding.FragmentUpdateBinding
import com.example.affirmationsapp.model.Affirmation
import com.example.affirmationsapp.model.ViewModel
import java.io.ByteArrayOutputStream


private const val TAG = "TEJAS"
private const val ARG_PARAM2 = "param2"

class UpdateFragment : Fragment() {

    companion object{
        private const val CAMERA_PERMISSION_CODE=1
        private const val CAMERA=2
    }

    private var affirmation: Affirmation? = null
    private var _binding: FragmentUpdateBinding?=null
    private val binding get()=_binding!!
    private var image: Bitmap?=null
    private lateinit var viewModel: ViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
//            param1 = it.getString(ARG_PARAM1)
            affirmation = it.getParcelable("affirmation")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel=ViewModelProvider(this).get(ViewModel::class.java)
        _binding=FragmentUpdateBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.updateItemImage.setImageBitmap(BitmapFactory.decodeByteArray(affirmation!!.image, 0, affirmation!!.image!!.size))
        binding.updateItemTitle.setText(affirmation!!.text.toString())

        binding.updateItemImage.setOnClickListener {
            updateImage()
        }

        binding.updateButton.setOnClickListener {
            if(check()){
                if(image!=null){
                    val stream = ByteArrayOutputStream()
                    image!!.compress(Bitmap.CompressFormat.PNG, 90, stream)
                    val array = stream.toByteArray()
                    affirmation!!.image = array

                }
                affirmation!!.text = binding.updateItemTitle.text.toString()
                viewModel.updateAffirmation(affirmation!!)
                Toast.makeText(requireContext(), "Updated Succesfully", Toast.LENGTH_SHORT)
                val action=UpdateFragmentDirections.actionUpdateFragmentToListFragment()
                findNavController().navigate(action)
            }
            else{
                if(image==null) Toast.makeText(requireContext(), "Couldnt Parse Image", Toast.LENGTH_SHORT).show()
                else if(binding.updateItemTitle.text==null) Toast.makeText(requireContext(), "Text is null", Toast.LENGTH_SHORT)
                else Toast.makeText(requireContext(), "Unknown error", Toast.LENGTH_SHORT).show()
            }
        }

    }
    private fun updateImage() {
        if(ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA)
            == PackageManager.PERMISSION_GRANTED){
            val intent= Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            Log.w(TAG, "permission granted")
            startActivityForResult(intent, AddFragment.CAMERA)
        }else{
            ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.CAMERA),
                CAMERA_PERMISSION_CODE
            )

        }
    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode== CAMERA_PERMISSION_CODE) {
            if(grantResults.isNotEmpty()&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
                val intent=Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                startActivityForResult(intent, CAMERA)
                Log.w(TAG, "camera started")
            }else{
                Toast.makeText(requireContext(), "Permission Denied", Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode== Activity.RESULT_OK){
            if(requestCode== CAMERA){
                image=data!!.extras!!.get("data") as Bitmap
                Log.w(TAG, "image assigned")
                binding.updateItemImage.setImageBitmap(image)

            }
            else Toast.makeText(requireContext(), "couldnt load image", Toast.LENGTH_SHORT).show()
        }

    }
    private fun check():Boolean{
        return image!=null || binding.updateItemTitle.text!=null
    }
}