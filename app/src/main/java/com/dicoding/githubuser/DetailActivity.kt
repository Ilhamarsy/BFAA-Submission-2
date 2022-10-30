package com.dicoding.githubuser

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.dicoding.githubuser.adapter.SectionsPagerAdapter
import com.dicoding.githubuser.databinding.ActivityDetailBinding
import com.dicoding.githubuser.event.Event
import com.dicoding.githubuser.fragment.FollowFragment
import com.dicoding.githubuser.model.DetailViewModel
import com.dicoding.githubuser.response.DetailUserResponse
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private val viewModel by viewModels<DetailViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intent = intent.getStringExtra(EXTRA_USER)

        if (savedInstanceState == null && intent!=null) viewModel.setData(intent)

        viewModel.data.observe(this){
            setPagerAdapter(it.login)
            setData(it)
        }

        viewModel.isLoading.observe(this) {
            showLoading(it)
        }

        viewModel.isError.observe(this){
            showError(it)
        }
    }

    private fun setPagerAdapter(login: String?) {
        if (login != null) {
            FollowFragment.user = login
        }

        val sectionsPagerAdapter = SectionsPagerAdapter(this)
        val viewPager: ViewPager2 = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
        supportActionBar?.elevation = 0f
    }

    private fun setData(it: DetailUserResponse?) {
        if (it!=null){
            Glide.with(this)
                .load(it.avatarUrl)
                .circleCrop()
                .into(binding.imgUser)
            binding.apply {
                tvFollowers.text = it.followers.toString()
                tvFollowing.text = it.following.toString()
                tvRepository.text = it.publicRepos.toString()
                tvName.text = it.login
                tvUsername.text = it.name
                tvLocation.text = it.location
                tvCompany.text = it.company
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showError(it: Event<Boolean>) {
        it.getContentIfNotHandled()?.let { condition ->
            if (condition){
                Snackbar.make(
                    binding.root,
                    R.string.error,
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        }
    }

    companion object{
        const val EXTRA_USER = "extra_user"
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.followers,
            R.string.following
        )
    }
}