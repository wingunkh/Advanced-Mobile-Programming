package com.example.go.Profile

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.example.go.ImagePost.ImagePostListFragment
import com.example.go.MainActivity
import com.example.go.Model.UserModel
import com.example.go.PostViewModel
import com.example.go.R
import com.example.go.Utils.FBAuth
import com.example.go.Utils.FBRef
import com.example.go.databinding.FragmentProfileEditBinding
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProfileEditFragment : Fragment() {

    private lateinit var binding: FragmentProfileEditBinding
    private val viewModel by activityViewModels<PostViewModel>()
    private var ImageUri: Uri = viewModel.getUser(FBAuth.getUid()).imgUri.toUri()

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
                    .load(ImageUri)
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

            storageReference.putFile(ImageUri).addOnFailureListener {}.addOnSuccessListener {
                val user = viewModel.getUser(FBAuth.getUid())
                FBRef.userRef.child(user.uid).setValue(UserModel(user.uid,ImageUri.toString(),user.password,user.email,user.displayName))
                (activity as MainActivity).removeFragment(this@ProfileEditFragment)
            }
            (activity as MainActivity).changeFragmentWithBackStack(ImagePostListFragment.newInstance())
        }

        return binding.root
    }


    private fun selectImage() {

        val intent = Intent().apply {
            type = "image/*"
            action = Intent.ACTION_OPEN_DOCUMENT
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