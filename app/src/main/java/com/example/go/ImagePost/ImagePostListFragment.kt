package com.example.go.ImagePost

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.go.Follow.FollowFragment
import com.example.go.MainActivity
import com.example.go.Model.ImagePost
import com.example.go.Post.PostListFragment
import com.example.go.PostViewModel
import com.example.go.Profile.ProfileFragment
import com.example.go.Search.SearchFragment
import com.example.go.Utils.FBAuth
import com.example.go.databinding.FragmentImagePostListBinding
import com.ramotion.circlemenu.CircleMenuView
import kotlin.system.exitProcess

class ImagePostListFragment : Fragment() {

    private lateinit var binding: FragmentImagePostListBinding
    private val viewModel by activityViewModels<PostViewModel>()
    private  var myList = arrayListOf<ImagePost>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentImagePostListBinding.inflate(inflater, container, false)

        val menu = binding.circleMenu
        menu.eventListener = object : CircleMenuView.EventListener() {
            override fun onMenuOpenAnimationStart(view: CircleMenuView) {
                Log.d("D", "onMenuOpenAnimationStart")
            }

            override fun onMenuOpenAnimationEnd(view: CircleMenuView) {
                Log.d("D", "onMenuOpenAnimationEnd")
            }

            override fun onMenuCloseAnimationStart(view: CircleMenuView) {
                Log.d("D", "onMenuCloseAnimationStart")
            }

            override fun onMenuCloseAnimationEnd(view: CircleMenuView) {
                Log.d("D", "onMenuCloseAnimationEnd")
            }

            override fun onButtonClickAnimationStart(view: CircleMenuView, index: Int) {
                Log.d("D", "onButtonClickAnimationStart|index: $index")
            }

            override fun onButtonClickAnimationEnd(view: CircleMenuView, index: Int) {
                Log.d("D", "onButtonClickAnimationEnd|index: $index")

                when (index) {
                    0 -> (activity as MainActivity).changeFragmentWithBackStack(PostListFragment.newInstance())
                    1 -> (activity as MainActivity).changeFragmentWithBackStack(ImagePostWriteFragment.newInstance())
                    2 -> (activity as MainActivity).changeFragmentWithBackStack(SearchFragment.newInstance())
                    3 -> {
                        val handler= DialogInterface.OnClickListener{ p0, p1 ->
                            if(p1==DialogInterface.BUTTON_POSITIVE){
                                activity?.let { ActivityCompat.finishAffinity(it) } //액티비티 종료
                                exitProcess(0) //프로세스 종료
                            }
                        }

                        AlertDialog.Builder(context).run{ //AlterDialog 클래스의 빌더를 이용해 알림 창을 구현
                            setTitle("Go-SNS")
                            setIcon(android.R.drawable.ic_dialog_info)
                            setMessage("정말 종료하시겠습니까?")
                            setPositiveButton("예",handler)
                            setNegativeButton("아니오",null)
                            show()
                        }

//                        val intent = Intent(activity, LoginActivity::class.java).apply {
//                            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//                        }
//                        FBAuth.auth.signOut()
//                        startActivity(intent)
//                        Toast.makeText(context,"Logout",Toast.LENGTH_SHORT).show()
                    }
                    4 -> (activity as MainActivity).changeFragmentWithBackStack(ProfileFragment.newInstance(FBAuth.getUid()))
                }
            }

            override fun onButtonLongClick(
                view: CircleMenuView,
                buttonIndex: Int,
            ): Boolean {
                Log.d("D", "onButtonLongClick|index: $buttonIndex")
                return true
            }

            override fun onButtonLongClickAnimationStart(
                view: CircleMenuView,
                buttonIndex: Int,
            ) {
                Log.d("D", "onButtonLongClickAnimationStart|index: $buttonIndex")
            }

            override fun onButtonLongClickAnimationEnd(
                view: CircleMenuView,
                buttonIndex: Int,
            ) {
                Log.d("D", "onButtonLongClickAnimationEnd|index: $buttonIndex")
                when (buttonIndex) {
                    0 -> (activity as MainActivity).changeFragmentWithBackStack(PostListFragment.newInstance())
                    1 -> (activity as MainActivity).changeFragmentWithBackStack(ImagePostWriteFragment.newInstance())
                    2 -> (activity as MainActivity).changeFragmentWithBackStack(SearchFragment.newInstance())
                    3 -> (activity as MainActivity).changeFragmentWithBackStack(FollowFragment.newInstance())
                    4 -> (activity as MainActivity).changeFragmentWithBackStack(ProfileFragment.newInstance(FBAuth.getUid()))
                }
            }
        }

        viewModel.imagePostLiveData.observe(viewLifecycleOwner, Observer {
            myList.clear()
            myList.addAll(it)
            initView()
        })

        return binding.root
    }

    private fun initView() {

        binding.imagePostList.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext())
            adapter = ImagePostAdapter(myList)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = ImagePostListFragment()
    }
}