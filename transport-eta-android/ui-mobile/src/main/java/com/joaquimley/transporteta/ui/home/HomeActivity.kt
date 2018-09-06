package com.joaquimley.transporteta.ui.home

import android.Manifest
import androidx.lifecycle.ViewModelProviders
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.joaquimley.transporteta.R
import com.joaquimley.transporteta.presentation.home.HomeViewModel
import com.joaquimley.transporteta.presentation.home.HomeViewModelImpl
import com.joaquimley.transporteta.ui.home.favorite.FavoritesFragment
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

class HomeActivity : AppCompatActivity(), HasSupportFragmentInjector/*, BottomNavigationView.OnNavigationItemSelectedListener*/ {

    private lateinit var viewModel: HomeViewModel
    private val favouritesFragment = FavoritesFragment.newInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        setupViewModel()
        if (hasReadSmsPermission().not()) {
            requestReadAndSendSmsPermission()
        }
        changeFragment(favouritesFragment)
    }

    private fun changeFragment(fragment: androidx.fragment.app.Fragment) {
        supportFragmentManager
                .beginTransaction()
                .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                .replace(R.id.fragment_container, fragment)
                .commit()
    }

    private fun setupViewModel() {
        // Create a HomeViewModelFactory to abstract the HomeViewModelImpl class
        viewModel = ViewModelProviders.of(this).get(HomeViewModelImpl::class.java)
    }

    /**
     * Runtime permission shenanigans
     * TODO: Remove this from the activity (move to permissions activity)
     */
    private fun hasReadSmsPermission(): Boolean {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestReadAndSendSmsPermission() {
        ActivityCompat.requestPermissions(this,
                arrayOf(Manifest.permission.SEND_SMS,
                        Manifest.permission.READ_SMS,
                        Manifest.permission.RECEIVE_SMS,
                        Manifest.permission.READ_PHONE_STATE), SMS_PERMISSION_CODE)
    }

    @Inject lateinit var fragmentInjector: DispatchingAndroidInjector<Fragment>
    override fun supportFragmentInjector(): AndroidInjector<Fragment>? {
        return fragmentInjector
    }

    companion object {
        const val SMS_PERMISSION_CODE = 1337
    }
}


//    private val favouritesFragment = FavoritesFragment.newInstance("")
//    lateinit var viewModel: HomeViewModelImpl
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_home)
//        setupViewModel()
//        setupNavigationView()
//    }
//
//    override fun onStart() {
//        super.onStart()
//        viewModel.observeSelectedScreen().observe(this, Observer {
//            when (it) {
//                NavigationType.FAVOURITES -> showFavouritesFragment()
//                NavigationType.DASHBOARD -> showSecondFragment()
//                NavigationType.NOTIFICATIONS -> showThirdAdminFragment()
//            }
//        })
//    }
//
//
//    private fun setupViewModel() {
//        viewModel = ViewModelProviders.of(this).get(HomeViewModelImpl::class.java)
//    }
//
//    private fun setupNavigationView() {
//        navigationView.setOnNavigationItemSelectedListener(this)
//    }
//
//    override fun onNavigationItemSelected(item: MenuItem): Boolean {
//        return when (item.itemId) {
//            R.id.navigation_favourites -> {
//                viewModel.setSelectedScreen(NavigationType.FAVOURITES)
//                true
//            }
//            R.id.navigation_dashboard -> {
//                viewModel.setSelectedScreen(NavigationType.DASHBOARD)
//                true
//            }
//            R.id.navigation_notifications -> {
//                viewModel.setSelectedScreen(NavigationType.NOTIFICATIONS)
//                true
//            }
//            else -> false
//        }
//    }