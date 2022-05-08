package com.example.mystoryapp

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.mystoryapp.databinding.ActivityDetailStoryBinding
import com.example.mystoryapp.models.UserPreference
import com.example.mystoryapp.viewmodel.DetailStoryViewModel

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")


class DetailStoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailStoryBinding
    private lateinit var detailStoryViewModel: DetailStoryViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailStoryBinding.inflate(layoutInflater)
        setContentView(binding.root)


        setupAction()


    }

    private fun setupAction() {
        detailStoryViewModel = ViewModelProvider(
            this,
            ViewModelFactory(UserPreference.getInstance(dataStore))
        )[DetailStoryViewModel::class.java]

        val name = intent.getStringExtra(EXTRA_NAME)
        val bundle = Bundle()
        bundle.putString(EXTRA_NAME, name)

        val image_url = intent.getStringExtra(EXTRA_URL_IMAGE)
        val bundle2 = Bundle()
        bundle2.putString(EXTRA_URL_IMAGE, image_url)

        val description = intent.getStringExtra(EXTRA_DESCRIPTION)
        val bundle3 = Bundle()
        bundle3.putString(EXTRA_DESCRIPTION, description)


        detailStoryViewModel.getUser().observe(this, {
            detailStoryViewModel.getDetailStory("Bearer ${it.token}")

            Glide.with(this)
                .load(image_url)
                .centerCrop()
                .into(binding.imgDetailStory)

            binding.tvDescriptionDeatailStory.text = description

            binding.tvNameDetailStory.text = name
//            detailStoryViewModel.getTheDetailStory().observe(this) {
//                if (it != null) {
//                    Glide.with(this)
//                        .load(it.photoUrl)
//                        .centerCrop()
//                        .into(binding.imgDetailStory)
//
//                    binding.tvNameDetailStory.text = it.name
//
//                    binding.tvDescriptionDeatailStory.text = it.description
//                }
//            }
        })


    }

    companion object {
        const val EXTRA_NAME = "extra_name"
        const val EXTRA_URL_IMAGE = "extra_url_image"
        const val EXTRA_DESCRIPTION = "extra_description"
    }
}