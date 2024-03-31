package com.pinto.github_user.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.pinto.github_user.data.model.ResponseFollow
import com.pinto.github_user.databinding.FragmentFollowerBinding
import com.pinto.github_user.ui.activity.DetailUserActivity
import com.pinto.github_user.ui.adapter.FollowAdapter
import com.pinto.github_user.ui.viewmodel.MainViewModel


class FollowerFragment : Fragment() {
    private val viewModel: MainViewModel by viewModels()
    private val adapter = FollowAdapter()

    private lateinit var binding: FragmentFollowerBinding
    private val _binding get() = binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFollowerBinding.inflate(inflater, container, false)
        return _binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showViewModel()
        showRecyclerView()
        viewModel.getIsLoading.observe(viewLifecycleOwner, this::showLoading)
    }

    private fun showViewModel() {
        viewModel.followers(DetailUserActivity.username)
        viewModel.getFollowers.observe(viewLifecycleOwner) { followers ->
            if (followers.size != 0) {
                adapter.setData(followers)
            } else {
                Toast.makeText(context, "Followers Not Found", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun showRecyclerView() {
        binding.rvFollowers.layoutManager = LinearLayoutManager(requireContext())
        binding.rvFollowers.setHasFixedSize(true)
        binding.rvFollowers.adapter = adapter

        adapter.setOnItemClickCallback { data -> selectedUser(data) }
    }

    private fun selectedUser(user: ResponseFollow) {
        Toast.makeText(context, "You choose ${user.login}", Toast.LENGTH_SHORT).show()

        val i = Intent(activity, DetailUserActivity::class.java)
        i.putExtra(DetailUserActivity.EXTRA_USER, user.login)
        startActivity(i)
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.followers(DetailUserActivity.username)
    }

}