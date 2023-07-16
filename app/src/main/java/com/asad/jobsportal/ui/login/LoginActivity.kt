package com.asad.jobsportal.ui.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.asad.jobsportal.ui.main.MainActivity
import com.asad.jobsportal.data.datastore.SharedPreferences
import com.asad.jobsportal.databinding.ActivityLoginBinding
import com.facebook.*
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import org.json.JSONException

class LoginActivity : AppCompatActivity() {

    private lateinit var binding : ActivityLoginBinding
    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private lateinit var callbackManager: CallbackManager
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        sharedPreferences = SharedPreferences(this)

        if (sharedPreferences.getLoginInfo().nama != ""){
            startActivity(Intent(this, MainActivity::class.java))
            finish()
            return
        }

        setupGoogleSignInClient()
        setupFacebookSignIn()

        binding.btnSignInGoogle.setOnClickListener {
            googleSignIn()
        }
        binding.btnSignInFacebook.setOnClickListener {
            facebookSignIn()
        }
    }

    private fun facebookSignIn() {
        LoginManager.getInstance().logInWithReadPermissions(this, arrayListOf("public_profile"))
    }

    override fun onStart() {
        super.onStart()
        val account = GoogleSignIn.getLastSignedInAccount(this)
        val displayName = account?.displayName
        Log.d("LoginActivity", "Name : $displayName")
    }

    private fun setupGoogleSignInClient(){
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
    }

    private fun setupFacebookSignIn(){
        callbackManager = CallbackManager.Factory.create()

        LoginManager.getInstance().registerCallback(callbackManager, object: FacebookCallback<LoginResult> {
            override fun onCancel() {
                Toast.makeText(this@LoginActivity, "Login Facebook batal", Toast.LENGTH_SHORT).show()
            }
            override fun onError(error: FacebookException) {
                Toast.makeText(this@LoginActivity, "Login Facebook gagal ${error.localizedMessage}", Toast.LENGTH_SHORT).show()
            }
            override fun onSuccess(result: LoginResult) {

                val accessToken = AccessToken.getCurrentAccessToken()
                val request = GraphRequest.newMeRequest(accessToken) { obj, response ->
                    try {
                        val fullName = obj?.getString("name")
                        val urlPhoto = obj?.getJSONObject("picture")?.getJSONObject("data")?.getString("url")
                        sharedPreferences.saveLoginInfo(fullName?:"", urlPhoto?:"")
                        startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                        finish()
                    }
                    catch (e: JSONException){
                        Toast.makeText(this@LoginActivity, e.localizedMessage, Toast.LENGTH_SHORT).show()
                    }
                }
                val parameters = Bundle()
                parameters.putString("fields", "id,name,link,picture.type(large)")
                request.parameters = parameters
                request.executeAsync()
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        callbackManager.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)
    }


    private fun googleSignIn(){
        val signInIntent = mGoogleSignInClient.signInIntent
        launcher.launch(signInIntent)
    }

    private val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result ->
        if (result.resultCode == RESULT_OK) {
            try {
                val data = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                val displayName = data.result.displayName ?: ""
                val urlPhoto = data.result.photoUrl.toString()
                sharedPreferences.saveLoginInfo(displayName, urlPhoto)
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            } catch (e: ApiException) {
                e.printStackTrace()
                Toast.makeText(this@LoginActivity, e.localizedMessage, Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this@LoginActivity, "gagal login", Toast.LENGTH_SHORT).show()
        }
    }

}