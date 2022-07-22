package com.example.mystoryapp.ui.register

import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.example.mystoryapp.databinding.ActivityRegisterBinding
import com.example.mystoryapp.models.UserModel
import com.example.mystoryapp.models.UserPreference
import com.example.mystoryapp.ui.ViewModelFactory
import com.example.mystoryapp.ui.login.LoginActivity

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")


class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var user: UserModel
    private lateinit var registerViewModel: RegisterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        setupViewModel()
        setupAction()
    }

    private fun setupView() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }

    private fun setupViewModel() {
        registerViewModel = ViewModelProvider(
            this,
            ViewModelFactory(UserPreference.getInstance(dataStore))
        )[RegisterViewModel::class.java]

        registerViewModel.getUser().observe(this, { user ->
            this.user = user
        })

        registerViewModel.isLoading.observe(this, {
            showLoading(it)
        })
    }

    private fun setupAction() {
        binding.buttonRegister.setOnClickListener {
            val name = binding.etRegisterName.text.toString()
            val email = binding.etRegisterEmail.text.toString()
            val password = binding.etRegisterPassword.text.toString()

            when {
                name.isEmpty() -> {
                    binding.etRegisterName.error = "Name is required"
                }
                email.isEmpty() -> {
                    binding.etRegisterEmail.error = "Email is required"
                }

                password.isEmpty() -> {
                    binding.etRegisterPassword.error = "Password is required"
                }
                else -> {
                    registerViewModel.register(name, email, password)
                    if (!user.isLogin) {
                        Toast.makeText(
                            this,
                            "akun ini sudah didaftarkan",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        Toast.makeText(
                            this,
                            "Berhasil!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }

        }

        binding.registerTvSignIn.setOnClickListener(View.OnClickListener {
            val intent = Intent(applicationContext, LoginActivity::class.java)
            startActivity(intent)
        })
    }

    private fun showLoading(isLoading: Boolean) {
        binding.registerProgressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}