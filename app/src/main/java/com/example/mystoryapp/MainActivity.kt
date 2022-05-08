package com.example.mystoryapp


import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.example.mystoryapp.databinding.ActivityMainBinding
import com.example.mystoryapp.models.UserModel
import com.example.mystoryapp.models.UserPreference
import com.example.mystoryapp.viewmodel.MainViewModel
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mystoryapp.adapter.LoadingStateAdapter
import com.example.mystoryapp.adapter.StoriesAdapter
import kotlinx.coroutines.launch

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var mainViewModel: MainViewModel
    private lateinit var user: UserModel

    private val pagingViewModel: PagingViewModel by viewModels {
        PagingViewModel.ViewModelFactory(this)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViewModel()
//        val layoutManager = LinearLayoutManager(this)
//        binding.rvItems.layoutManager = layoutManager

        getTheStories()

        binding.fabAdd.setOnClickListener {
            val intent = Intent(this, AddStoryActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu -> {
                mainViewModel.logout()

                val i = Intent(this, WelcomeActivity::class.java)
                startActivity(i)
                return true
            }
            R.id.menu2 -> {
                val i = Intent(this, MapsActivity::class.java)
                startActivity(i)
                return true
            }
            else -> return true
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.mainProgressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun getTheStories() {
        val adapter = StoriesAdapter()
        binding.rvItems.adapter = adapter.withLoadStateFooter(
            footer = LoadingStateAdapter {
                adapter.retry()
            }
        )

        binding.rvItems.layoutManager = LinearLayoutManager(this)
        binding.rvItems.setHasFixedSize(true)

        mainViewModel.getUser().observe(this) { stories ->
            if (stories.isLogin) {
                pagingViewModel.getTheListStories("Bearer ${stories.token}").observe(this) {
                    adapter.submitData(lifecycle, it)
                    Log.d("CEK DATA", "$it")
                }
            } else {

                Toast.makeText(applicationContext, "Masih gagal", Toast.LENGTH_LONG)
            }
        }
    }

    private fun setupViewModel() {
        mainViewModel = ViewModelProvider(
            this,
            ViewModelFactory(UserPreference.getInstance(dataStore))
        )[MainViewModel::class.java]

//        val adapter = StoriesAdapter()
//        binding.rvItems.adapter = adapter


//        mainViewModel.getUser().observe(this, { user ->
//            if (user.isLogin) {
//                showLoading(true)
//                mainViewModel.getListStories("Bearer ${user.token}")
//
//                mainViewModel.getTheStories().observe(this) { it ->
//                    val layoutManager = LinearLayoutManager(this)
//                    binding.rvItems.layoutManager = layoutManager
//                    val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
//                    binding.rvItems.addItemDecoration(itemDecoration)
//                    val adapter = StoriesAdapter(it)
//                    binding.rvItems.adapter = adapter
//
//                    showLoading(false)
//
//                    adapter.setOnItemClickCallBack(object : StoriesAdapter.OnItemClickCallBack {
//                        override fun onItemClicked(data: ListStoryItem) {
//                            Intent(this@MainActivity, DetailStoryActivity::class.java).also {
//                                it.putExtra(DetailStoryActivity.EXTRA_NAME, data.name)
//                                it.putExtra(DetailStoryActivity.EXTRA_DESCRIPTION, data.description)
//                                it.putExtra(DetailStoryActivity.EXTRA_URL_IMAGE, data.photoUrl)
//                                startActivity(it)
//                            }
//                        }
//
//                    })
//                }
//            } else {
//                startActivity(Intent(this, WelcomeActivity::class.java))
//                finish()
//            }
//        })

//        mainViewModel.getStories(user.token).observe(this){
//            val layoutManager = LinearLayoutManager(this)
//                    binding.rvItems.layoutManager = layoutManager
//                    val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
//                    binding.rvItems.addItemDecoration(itemDecoration)
//                    val adapter = StoriesAdapter()
//                    binding.rvItems.adapter = adapter
//
//                    showLoading(false)
//
//                    adapter.setOnItemClickCallBack(object : StoriesAdapter.OnItemClickCallBack {
//                        override fun onItemClicked(data: ListStoryItem) {
//                            Intent(this@MainActivity, DetailStoryActivity::class.java).also {
//                                it.putExtra(DetailStoryActivity.EXTRA_NAME, data.name)
//                                it.putExtra(DetailStoryActivity.EXTRA_DESCRIPTION, data.description)
//                                it.putExtra(DetailStoryActivity.EXTRA_URL_IMAGE, data.photoUrl)
//                                startActivity(it)
//                            }
//                        }
//
//                    })
//                }
//            } else {
//                startActivity(Intent(this, WelcomeActivity::class.java))
//                finish()
//            }

//        mainViewModel.getUser().observe(this) {
//            if (it != null) {
//                pagingViewModel.getTheListStories("Bearer " + it.token).observe(this) {
//                    adapter.submitData(lifecycle,it)
//                }
//            }
//        }
    }
}


