package com.example.affirmationsapp.Fragments

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
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
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.affirmationsapp.R
import com.example.affirmationsapp.adapter.ItemAdapter
import com.example.affirmationsapp.databinding.FragmentListBinding
import com.example.affirmationsapp.model.ViewModel

private const val TAG = "TEJAS"
private const val ARG_PARAM2 = "param2"

class ListFragment : Fragment() {
    companion object{
        private const val CAMERA_PERMISSION_CODE=1
        private const val CAMERA=2
    }
    private var param2: String? = null

    private var _binding: FragmentListBinding?=null
    private val binding get()=_binding!!
    private lateinit var viewModel: ViewModel
    var image: Bitmap?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        arguments?.let {
////            param1 = it.getString(ARG_PARAM1)
////            param2 = it.getString(ARG_PARAM2)
//        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       _binding=FragmentListBinding.inflate(inflater, container, false)
        viewModel=ViewModelProvider(this).get(ViewModel::class.java)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter=ItemAdapter(viewModel)
        binding.listRcv.adapter=adapter
        binding.listRcv.layoutManager=LinearLayoutManager(requireContext())
        viewModel.getAllData.observe(viewLifecycleOwner, Observer{
            List-> adapter.setData(List)
        })

        binding.fab.setOnClickListener {
            captureImage()
            Log.w(TAG, "image part done")
            Log.w(TAG, "Navigated")
        }
    }
    private fun captureImage() {
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
            if(requestCode==CAMERA){
                image=data!!.extras!!.get("data") as Bitmap
                Log.w(TAG, "image assigned")
                val action=ListFragmentDirections.actionListFragmentToAddFragment(image!!)

                findNavController().navigate(action)
            }
            else Toast.makeText(requireContext(), "", Toast.LENGTH_SHORT).show()
        }

    }
}