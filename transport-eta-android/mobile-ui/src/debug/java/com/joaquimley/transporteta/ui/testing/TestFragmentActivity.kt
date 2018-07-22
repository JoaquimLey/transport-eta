package com.joaquimley.transporteta.ui.testing

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.ViewGroup
import android.widget.FrameLayout
import com.joaquimley.transporteta.R
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

/**
* Used as container to test fragments in isolation with Espresso
*/

class TestFragmentActivity: AppCompatActivity(), HasSupportFragmentInjector {

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        val content = FrameLayout(this)
        content.layoutParams = FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        content.id = R.id.fragment_container
        setContentView(content)
    }

    fun addFragment(fragment: androidx.fragment.app.Fragment) {
        supportFragmentManager.beginTransaction().add(
                R.id.fragment_container,
                fragment, "TEST").commit()
    }


    fun replaceFragment(fragment: androidx.fragment.app.Fragment) {
        supportFragmentManager.beginTransaction().replace(
                R.id.fragment_container,
                fragment, "TEST").commit()
    }

    @Inject lateinit var fragmentInjector: DispatchingAndroidInjector<androidx.fragment.app.Fragment>
    override fun supportFragmentInjector(): AndroidInjector<androidx.fragment.app.Fragment>? {
        return fragmentInjector
    }
}