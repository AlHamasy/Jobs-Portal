package com.asad.jobsportal.ui.account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.asad.jobsportal.R
import com.asad.jobsportal.data.datastore.SharedPreferences
import com.asad.jobsportal.databinding.FragmentNotificationsBinding
import com.bumptech.glide.Glide
import com.facebook.login.LoginManager
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions

class AccountFragment : Fragment() {

    private var _binding: FragmentNotificationsBinding? = null
    private val binding get() = _binding!!
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var mGoogleSignInClient: GoogleSignInClient

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        sharedPreferences = SharedPreferences(requireContext())

        setupGoogleSignInClient()

        binding.apply {
            Glide.with(this@AccountFragment)
                .load(sharedPreferences.getLoginInfo().urlFoto)
                .placeholder(R.drawable.image_available)
                .error(R.drawable.no_image_2)
                .into(imgProfile)
            tvUsername.text = sharedPreferences.getLoginInfo().nama
            btnLogout.setOnClickListener {
                logout()
            }
        }

        return root
    }

    private fun setupGoogleSignInClient(){
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)
    }

    private fun logout(){
        LoginManager.getInstance().logOut()
        mGoogleSignInClient.signOut()

        sharedPreferences.clearLoginInfo()
        requireActivity().finish()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}