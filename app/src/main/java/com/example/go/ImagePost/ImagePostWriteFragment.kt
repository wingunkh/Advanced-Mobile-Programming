package com.example.go.ImagePost

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.contentValuesOf
import androidx.core.graphics.drawable.toIcon
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.go.MainActivity
import com.example.go.Model.ImagePost
import com.example.go.Post.PostListFragment
import com.example.go.PostViewModel
import com.example.go.Utils.FBAuth
import com.example.go.Utils.FBRef
import com.example.go.databinding.FragmentImagePostWriteBinding
import com.google.firebase.inappmessaging.model.ImageData
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import java.io.ByteArrayOutputStream

class ImagePostWriteFragment : Fragment() {

    private lateinit var binding: FragmentImagePostWriteBinding
    private lateinit var ImageUri: Uri
    private val viewModel by activityViewModels<PostViewModel>()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentImagePostWriteBinding.inflate(inflater, container, false)

        binding.imagePostWritePicture.setOnClickListener {
            selectImage()
        }

        binding.imagePostWriteBtn.setOnClickListener {
            val newPostKey = FBRef.imagePostRef.child(FBAuth.getUid()).push().key!!
            val postContent = binding.imagePostWriteContent.text.toString()
            val postUid = FBAuth.getUid()
            val postUser = FBAuth.getDisplayName()
            val postDate = FBAuth.getTime()
            val storageReference =
                FirebaseStorage.getInstance().getReference("images/$postDate")

            binding.imagePostWritePicture.apply {
                isDrawingCacheEnabled = true
                buildDrawingCache()
            }
            val bitmap = (binding.imagePostWritePicture.drawable as BitmapDrawable).bitmap
            val baos = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
            val data = baos.toByteArray()
            val uploadTask = storageReference.putBytes(data)
            uploadTask.addOnFailureListener {}.addOnSuccessListener {}
            uploadTask.continueWithTask { task->
                if (!task.isSuccessful){
                    task.exception?.let{
                        throw it
                    }
                }
                storageReference.downloadUrl
            }.addOnCompleteListener{ task->
                if(task.isSuccessful){
                    val downloadUri = task.result
                    val imuri = downloadUri.toString()
                    viewModel.createImagePostItem(
                        newPostKey,ImagePost(newPostKey,postUid,imuri,
                            postUser,postContent,postDate))
                    (activity as MainActivity).removeFragment(this@ImagePostWriteFragment)
                    Log.d("check", imuri)
                }
            }
            (activity as MainActivity).changeFragmentWithBackStack(ImagePostListFragment.newInstance())
        }

        return binding.root
    }

    private fun selectImage() {

        var intent = Intent().apply {
            type = "image/*"
            action = Intent.ACTION_GET_CONTENT
        }
        startActivityForResult(intent, 100)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 100 && resultCode == RESULT_OK) {
            ImageUri = data?.data!!
            binding.imagePostWritePicture.setImageURI(ImageUri)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = ImagePostWriteFragment()
    }
}