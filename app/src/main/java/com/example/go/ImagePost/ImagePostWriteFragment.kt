package com.example.go.ImagePost

import android.app.Activity.RESULT_CANCELED
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.go.MainActivity
import com.example.go.Model.ImagePost
import com.example.go.PostViewModel
import com.example.go.Utils.FBAuth
import com.example.go.Utils.FBRef
import com.example.go.databinding.FragmentImagePostWriteBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage


class ImagePostWriteFragment : Fragment() {

    private lateinit var binding: FragmentImagePostWriteBinding
    private val viewModel by activityViewModels<PostViewModel>()
    private lateinit var ImageUri: Uri


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
            var postUser = ""
            FBRef.userRef.child(postUid).child("displayName").addListenerForSingleValueEvent(object :
                ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    postUser = dataSnapshot.value.toString()
                }
                override fun onCancelled(error: DatabaseError) {}
            })
            val postDate = FBAuth.getTime()
            val storageReference =
                FirebaseStorage.getInstance().getReference("images/$postDate")

            storageReference.putFile(ImageUri).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    task.result.metadata?.reference?.downloadUrl?.addOnSuccessListener {
                        viewModel.createImagePostItem(
                            newPostKey, ImagePost(
                                newPostKey, postUid, it.toString(),
                                postUser, postContent, postDate
                            )
                        )
                    }
                    (activity as MainActivity).removeFragment(this@ImagePostWriteFragment)
                    Log.d("check", ImageUri.toString())
                }
            }
            (activity as MainActivity).changeFragmentWithBackStack(ImagePostListFragment.newInstance())
        }

        return binding.root
    }

    private fun selectImage() {

        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
            type = "image/*"
            action = Intent.ACTION_GET_CONTENT
        }
        startActivityForResult(intent, 100)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != RESULT_CANCELED) {
            if (requestCode == 100) {
                if (data != null) {
                    ImageUri = data.data!!
                }
                val bitmap =
                    MediaStore.Images.Media.getBitmap(requireActivity().contentResolver, ImageUri)
                binding.imagePostWritePicture.setImageBitmap(bitmap)
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = ImagePostWriteFragment()
    }
}