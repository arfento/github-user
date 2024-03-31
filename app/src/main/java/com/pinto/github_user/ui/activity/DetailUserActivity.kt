package com.pinto.github_user.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.pinto.github_user.R
import com.pinto.github_user.databinding.ActivityDetailUserBinding
import com.pinto.github_user.ui.adapter.SectionsPagerAdapter
import com.pinto.github_user.ui.viewmodel.MainViewModel

class DetailUserActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by viewModels()
    private lateinit var binding: ActivityDetailUserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val sectionsPagerAdapter = SectionsPagerAdapter(this)
        val viewPager: ViewPager2 = binding.viewPager
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = binding.tabs
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = TAB_TITLES[position]
        }.attach()

        username = intent.getStringExtra(EXTRA_USER).toString()
        supportActionBar?.title = username

        showViewModel()
        viewModel.getIsLoading.observe(this, this::showLoading)
    }

    private fun showViewModel() {
        viewModel.detailUser(username)
        viewModel.getUserDetail.observe(this) { detailUser ->
            Glide.with(this)
                .load(detailUser.avatarUrl)
                .skipMemoryCache(true)
                .into(binding.imgAvatar)

            binding.tvName.text = detailUser.name
            binding.tvUsername.text = detailUser.login
            binding.tvCompany.text = detailUser.company
            binding.tvLocation.text = detailUser.location
            binding.tvRepositoryValue.text = detailUser.publicRepos
            binding.tvFollowersValue.text = detailUser.followers
            binding.tvFollowingValue.text = detailUser.following
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    companion object {
        const val EXTRA_USER = "extra_user"
        var username = String()


        private val TAB_TITLES = arrayOf(
           "Follower",
            "Following"
        )
    }
}