package com.example.go.Profile

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.example.go.ImagePost.ImagePostListFragment
import com.example.go.MainActivity
import com.example.go.PostViewModel
import com.example.go.R
import com.example.go.Utils.FBAuth
import com.example.go.databinding.FragmentProfileEditBinding
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream

class ProfileEditFragment : Fragment() {

    private lateinit var binding: FragmentProfileEditBinding
    private lateinit var ImageUri: Uri
    private val viewModel by activityViewModels<PostViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileEditBinding.inflate(inflater, container, false)

        // 프로필 유저 정보 가져오기
        if(viewModel.getUser(FBAuth.getUid()).imgUri=="") {
            binding.profileEditImage.setImageResource(R.drawable.ic_baseline_face_24)
        } else {
            CoroutineScope(Dispatchers.Main).launch {
                Glide.with(requireContext())
                    .load(viewModel.getUser(FBAuth.getUid()).imgUri)
                    .into(binding.profileEditImage)
            }
        }
        binding.profileEditUsername.setText(FBAuth.getDisplayName())
        binding.profileEditEmail.text = FBAuth.getEmail()
        binding.profileEditPassword.setText("********")

        // 유저 프로필 편집
        binding.profileEditImage.setOnClickListener {
            selectImage()
        }
        binding.p1.setOnClickListener {
            FBAuth.setDisplayName(binding.profileEditUsername.text.toString())
        }

        // 완료 버튼 클릭
        binding.profileEditFinishBtn.setOnClickListener {
            val storageReference =
                FirebaseStorage.getInstance().getReference("images/${FBAuth.getTime()}")

            binding.profileEditImage.apply {
                isDrawingCacheEnabled = true
                buildDrawingCache()
            }
            val bitmap = (binding.profileEditImage.drawable as BitmapDrawable).bitmap
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
                    (activity as MainActivity).removeFragment(this@ProfileEditFragment)
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

        if (requestCode == 100 && resultCode == Activity.RESULT_OK) {
            ImageUri = data?.data!!
            binding.profileEditImage.setImageURI(ImageUri)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = ProfileEditFragment()
    }
}