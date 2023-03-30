package com.example.affirmationsapp.Fragments

import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.affirmationsapp.databinding.FragmentAddBinding
import com.example.affirmationsapp.model.Affirmation
import com.example.affirmationsapp.model.ViewModel
import java.io.ByteArrayOutputStream


private const val TAG = "TEJAS"
private const val ARG_PARAM2 = "param2"

class AddFragment : Fragment() {
    companion object{
        const val CAMERA_PERMISSION_CODE=1
        const val CAMERA=2
    }
    private var param1: String? = null
    private var param2: String? = null
    private var image: Bitmap?=null

    private var _binding: FragmentAddBinding?=null
    private val binding get()=_binding!!
    private lateinit var viewModel: ViewModel
    private lateinit var bitmap: Bitmap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

            image = it.getParcelable("imageBitmap")
            Log.w(TAG, "Got image")
//            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.w(TAG, "onCreateView")
        _binding=FragmentAddBinding.inflate(inflater, container, false)
        viewModel=ViewModelProvider(this).get(ViewModel::class.java)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.w(TAG, "onViewCreated")
        if(image!=null) binding.imageViewAdd.setImageBitmap(image)

        binding.createbutton.setOnClickListener {

            if(check()) {

                val stream = ByteArrayOutputStream()
                image!!.compress(Bitmap.CompressFormat.PNG, 90, stream)
                val array = stream.toByteArray()
                viewModel.addAffirmation(Affirmation(binding.textInput.text.toString(), array, 0))
                val action=AddFragmentDirections.actionAddFragmentToListFragment()
                findNavController().navigate(action)
            }
            else Toast.makeText(requireContext(), "Couldnt Add Image", Toast.LENGTH_SHORT).show()
        }
    }

//    private fun captureImage() {
//         if(ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA)
//         ==PackageManager.PERMISSION_GRANTED){
//             val intent=Intent(MediaStore.ACTION_IMAGE_CAPTURE)
//             startActivityForResult(intent, CAMERA)
//         }else{
//             ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.CAMERA), CAMERA_PERMISSION_CODE)
//
//         }
//
//    }


//    override fun onRequestPermissionsResult(
//        requestCode: Int,
//        permissions: Array<out String>,
//        grantResults: IntArray
//    ) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//        if(requestCode== CAMERA_PERMISSION_CODE) {
//            if(grantResults.isNotEmpty()&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
//                val intent=Intent(MediaStore.ACTION_IMAGE_CAPTURE)
//                startActivityForResult(intent, CAMERA)
//            }else{
//                Toast.makeText(requireContext(), "Permission Denied", Toast.LENGTH_LONG).show()
//            }
//        }
//    }
//
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if(resultCode==Activity.RESULT_OK){
//            if(requestCode== CAMERA){
//                image=data!!.extras!!.get("data") as Bitmap
//                binding.imageViewAdd.setImageBitmap(image)
//            }
//        }
//
//    }

    private fun check(): Boolean{
        return image!=null && binding.textInput.text!=null
    }
}