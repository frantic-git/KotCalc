package com.frantic.kotcalc.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.frantic.kotcalc.R
import com.frantic.kotcalc.domain.CalcPresenter
import com.frantic.kotcalc.domain.Router
import com.frantic.kotcalc.domain.Screens

class MainActivity : AppCompatActivity() {

    lateinit var fragmentRouter: Router

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (!::fragmentRouter.isInitialized) {
            fragmentRouter = Router(supportFragmentManager, R.id.activity_main, ::finishActivity)
        }
        if (savedInstanceState == null) {
            fragmentRouter.navigateTo(Screens.FRAGMENTS.MAIN_FRAGMENT)
        }
    }

    override fun onBackPressed() {
        fragmentRouter.back()
    }

    private fun finishActivity() {
        finish()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        val lastCalcPresenter = fragmentRouter.lastCalcPresenter
        if (lastCalcPresenter != null) {
            outState.putString("operation", lastCalcPresenter.operation.toString())
            outState.putString("num", lastCalcPresenter.num.toString())
            outState.putString("lastOperation", lastCalcPresenter.lastOperation.toString())
            outState.putDouble("result", lastCalcPresenter.result)
            outState.putBoolean("isOperation", lastCalcPresenter.isOperation)
            outState.putBoolean("isCLR", lastCalcPresenter.isCLR)
            outState.putBoolean("isDivByZero", lastCalcPresenter.isDivByZero)
        }
        outState.putBoolean("lastCalcPresenter", lastCalcPresenter != null)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        fragmentRouter = Router(supportFragmentManager, R.id.activity_main, ::finishActivity)

        if (savedInstanceState.getBoolean("lastCalcPresenter")) {
            val lastPresenter = CalcPresenter()
            lastPresenter.operation.append(savedInstanceState.getString("operation"))
            lastPresenter.num.append(savedInstanceState.getString("num"))
            lastPresenter.lastOperation.clear()
            lastPresenter.lastOperation.append(savedInstanceState.getString("lastOperation"))
            lastPresenter.result = savedInstanceState.getDouble("result")
            lastPresenter.isCLR = savedInstanceState.getBoolean("isCLR")
            lastPresenter.isOperation = savedInstanceState.getBoolean("isOperation")
            lastPresenter.isDivByZero = savedInstanceState.getBoolean("isDivByZero")
            fragmentRouter.lastCalcPresenter = lastPresenter
        }
    }
}

