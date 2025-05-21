package com.example.myapplication.Activities.Activities
import android.app.ComponentCaller
import android.content.Intent
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.text.style.UnderlineSpan
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.myapplication.Activities.data.database.AppDatabase
import com.example.myapplication.Activities.data.model.User
import com.example.myapplication.Activities.data.repository.UserRepository
import com.example.myapplication.Activities.viewmodel.UserViewModel
import com.example.myapplication.Activities.viewmodel.UserViewModelFactory
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityHomeBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import kotlinx.coroutines.launch

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var viewModel: UserViewModel
    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private var RC_SIGN_IN = 123
    private val TAG = "GoogleSignIn"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val dao = AppDatabase.getDatabase(application).userDao()
        val repository = UserRepository(dao)
        val factory = UserViewModelFactory(repository)

        viewModel = ViewModelProvider(this, factory)[UserViewModel::class.java]

        binding.homeLogInButton.setOnClickListener(){
            val email = binding.homeLoginEmailEditText.text.toString()
            val password = binding.homeLoginPasswordEditText.text.toString()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            viewModel.login(email, password) { user ->
                if (user != null) {
                    Toast.makeText(this, "Login successful!", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, ProductActivity::class.java)
                    intent.putExtra("name", user.name)
                    startActivity(intent)
                } else {
                    Toast.makeText(this, "Incorrect email or password", Toast.LENGTH_SHORT).show()
                }
            } }

        val fullText = "Don't have an account? Sign Up"
        val spannable = SpannableString(fullText)
        val signUpText = "Sign Up"
        val startIndex = fullText.indexOf(signUpText)
        val endIndex = startIndex + signUpText.length

        val clickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
                val intent = Intent(this@HomeActivity, UserActivity::class.java)
                startActivity(intent)
            }
        }

        // Apply optional style: color and underline to “Sign Up”.
        spannable.setSpan(clickableSpan, startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        spannable.setSpan(UnderlineSpan(), startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        spannable.setSpan(
            ForegroundColorSpan(ContextCompat.getColor(this, R.color.greendark)),
            startIndex,
            endIndex,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        // Assign the text to the TextView and allow interaction
        binding.homeGoToSignUpTextView.text = spannable
        binding.homeGoToSignUpTextView.movementMethod = LinkMovementMethod.getInstance()


        // Set Google Sign In
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .requestProfile()
            .build()

        // Create Google Sign In Client
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

        binding.homeLogInButtonGoogle.setOnClickListener{
            signIn()
        }
    }

    private fun signIn() {
        val signInIntent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?,
        caller: ComponentCaller
    ) {
        super.onActivityResult(requestCode, resultCode, data, caller)
        if (requestCode == RC_SIGN_IN){
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }

    private fun handleSignInResult(CompleteTask: Task<GoogleSignInAccount>) {
        try {
            val account = CompleteTask.getResult(ApiException::class.java)
            // Successful login
            Log.d(TAG, "SignInSuccess: ${account.email}")
            Toast.makeText(this, "Login Successfully", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, ProductActivity::class.java)
            intent.putExtra("name", account.displayName)
            startActivity(intent)

        } catch (e: ApiException){
            Log.d(TAG, "SignInResult: Failed code =" + e.statusCode)
            Toast.makeText(this, "Error logging in ", Toast.LENGTH_SHORT).show()
        }
    }

}
