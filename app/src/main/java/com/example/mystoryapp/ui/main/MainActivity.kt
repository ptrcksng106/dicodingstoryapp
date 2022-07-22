package com.example.mystoryapp.ui.main


import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.example.mystoryapp.databinding.ActivityMainBinding
import com.example.mystoryapp.models.UserPreference
import com.example.mystoryapp.ui.main.MainViewModel
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mystoryapp.R
import com.example.mystoryapp.adapter.LoadingStateAdapter
import com.example.mystoryapp.adapter.StoriesAdapter
import com.example.mystoryapp.ui.PagingViewModel
import com.example.mystoryapp.ui.ViewModelFactory
import com.example.mystoryapp.ui.add.AddStoryActivity
import com.example.mystoryapp.ui.maps.MapsActivity
import com.example.mystoryapp.ui.welcome.WelcomeActivity

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var mainViewModel: MainViewModel

    private val pagingViewModel: PagingViewModel by viewModels {
        PagingViewModel.ViewModelFactory(this)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViewModel()

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
                finish()
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
            }
        }
    }

    private fun setupViewModel() {
        mainViewModel = ViewModelProvider(
            this,
            ViewModelFactory(UserPreference.getInstance(dataStore))
        )[MainViewModel::class.java]
    }
}


