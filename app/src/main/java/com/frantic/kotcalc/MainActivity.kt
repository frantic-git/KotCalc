package com.frantic.kotcalc

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.frantic.kotcalc.domain.Router
import com.frantic.kotcalc.domain.Screens

class MainActivity : AppCompatActivity() {

    lateinit var fragmentRouter: Router

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        fragmentRouter = Router(supportFragmentManager, R.id.activity_main, ::finishActivity)
        if (savedInstanceState == null) {
            fragmentRouter.navigateTo(Screens.FRAGMENTS.CALC_FRAGMENT)
        }
    }

    override fun onBackPressed() {
        fragmentRouter.back()
    }

    private fun finishActivity() {
        finish()
    }
}

