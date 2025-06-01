package com.example.myapplication.Activities.Activities

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.Activities.data.database.AppDatabase
import com.example.myapplication.Activities.data.model.User
import com.example.myapplication.Activities.data.repository.UserRepository
import com.example.myapplication.Activities.viewmodel.ProductViewModel
import com.example.myapplication.Activities.viewmodel.UserViewModel
import com.example.myapplication.Activities.viewmodel.UserViewModelFactory
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivitySignUpBinding

class UserActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding
    private lateinit var viewModel: UserViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val imageView = findViewById<ImageView>(R.id.imageView5)
        val dao = AppDatabase.getDatabase(application).userDao()
        val repository = UserRepository(dao)
        val factory = UserViewModelFactory(repository)

        viewModel = ViewModelProvider(this, factory)[UserViewModel::class.java]

        imageView.setOnClickListener {
            val intent = Intent(this, CameraActivity::class.java)
            startActivity(intent)
        }

        binding.buttonSignUp.setOnClickListener{
            val name = binding.editTextUsername.text.toString()
            val email = binding.editTextEmail.text.toString()
            val phoneNumber = binding.editTextPhone.text.toString()
            val password = binding.editTextPassword.text.toString()

            val user = User(name = name, email = email, phoneNumber = phoneNumber, password = password)
            viewModel.insert(user)

            Toast.makeText(this, "Your account has been successfully created!", Toast.LENGTH_SHORT).show()
        }

        // Customized arrow
        binding.backButton.setOnClickListener {
            finish()
        }


    }


}
