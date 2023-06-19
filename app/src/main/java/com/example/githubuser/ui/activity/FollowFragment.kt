package com.example.githubuser.ui.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuser.data.remote.response.FollowResponseItem
import com.example.githubuser.databinding.FragmentFollowBinding
import com.example.githubuser.ui.adapter.FollowAdapter
import com.example.githubuser.ui.models.DetailViewModel

class FollowFragment : Fragment() {

    private var _binding: FragmentFollowBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: DetailViewModel

    private var position : Int? = null
    private var username : String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFollowBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvFollowUser.layoutManager = LinearLayoutManager(activity)

        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(DetailViewModel::class.java)
        arguments?.let {
            position = it.getInt(ARG_POSITION)
            username = it.getString(ARG_USERNAME)
        }

        viewModel.isLoading.observe(viewLifecycleOwner){
            showLoading(it)
        }

        if (position == 1) {

            viewModel.listFollowersUser.observe(viewLifecycleOwner) { followResponse ->
                setFollow(followResponse)
            }
            viewModel.followerUser(username.toString())
        } else {

            viewModel.listFollowingUser.observe(viewLifecycleOwner) { followResponse ->
                setFollow(followResponse)
            }
            viewModel.followingUser(username.toString())
        }
    }

    private fun setFollow(followResponse: List<FollowResponseItem>?) {
        var adapter = FollowAdapter(followResponse!!)
        binding.rvFollowUser.adapter = adapter
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    companion object {
        const val ARG_POSITION = "arg_position"
        const val ARG_USERNAME = "arg_username"
    }
}