package com.jarvizu.crowdcontrol.ui.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.afollestad.assent.Permission
import com.afollestad.assent.askForPermissions
import com.afollestad.assent.isAllGranted
import com.afollestad.assent.showSystemAppDetailsPage
import com.afollestad.materialdialogs.MaterialDialog
import com.jarvizu.crowdcontrol.R
import com.jarvizu.crowdcontrol.app.AuthManager
import com.jarvizu.crowdcontrol.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import timber.log.Timber
import javax.inject.Inject


@ExperimentalCoroutinesApi
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var authManager : AuthManager
    private lateinit var binding: ActivityMainBinding

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
    }

    private fun authenticateSession() {
        viewModel.getAuthState()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
    }


    override fun onAttachFragment(fragment: Fragment) {
        super.onAttachFragment(fragment)
    }

    override fun onStart() {
        super.onStart()
        askForPermissions(Permission.ACCESS_FINE_LOCATION, Permission.ACCESS_COARSE_LOCATION) {
            Timber.d(it.toString())
            if (!isAllGranted(Permission.ACCESS_FINE_LOCATION, Permission.ACCESS_COARSE_LOCATION)) {
                MaterialDialog(this)
                    .title(R.string.permissions_dialog)
                    .message(R.string.permissions_denied_dialog)
                    .show {
                        positiveButton(R.string.ok_button) {
                            showSystemAppDetailsPage()
                        }
                        negativeButton(R.string.disagree) {
                            findNavController(R.id.nav_host_fragment_container).navigate(R.id.splashFragment)
                            finish()
                            cancel()
                        }
                    }
            }
        }
    }
}


