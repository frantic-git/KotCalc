package com.frantic.kotcalc.domain

import android.support.v4.app.FragmentManager
import com.frantic.kotcalc.CalcFragment
import com.frantic.kotcalc.TranslateFragment

class Router(
    private val fragmentManager: FragmentManager,
    private val containerId: Int,
    private val finishActivity: () -> Unit
) {

    var lastCalcPresenter: CalcPresenter? = null
    var lastTranslatePresenter: TranslatePresenter? = null

    fun navigateTo(fragment: Screens.FRAGMENTS) {
        fragmentManager.beginTransaction()
            .add(containerId, Screens.createFragment(fragment))
            .addToBackStack(null)
            .commit()
    }

    fun replace(fragment: Screens.FRAGMENTS) {
        fragmentManager.beginTransaction()
            .replace(containerId, Screens.createFragment(fragment))
            .addToBackStack(null)
            .commit()
    }

    fun back() {
        if (fragmentManager.backStackEntryCount == 1) {
            finishActivity.invoke()
        } else {
            val curFragment = fragmentManager.fragments.last()
            when (curFragment) {
                is CalcFragment -> lastCalcPresenter = curFragment.mPresenter
                is TranslateFragment -> lastTranslatePresenter = curFragment.mPresenter
            }
            fragmentManager.popBackStack()
        }
    }
}