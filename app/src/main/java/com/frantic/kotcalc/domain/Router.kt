package com.frantic.kotcalc.domain

import androidx.fragment.app.FragmentManager
import com.frantic.kotcalc.CalcFragment
import com.frantic.kotcalc.RandomFoxFragment

class Router(
    private val fragmentManager: androidx.fragment.app.FragmentManager,
    private val containerId: Int,
    private val finishActivity: () -> Unit
) {

    var lastCalcPresenter: CalcPresenter? = null
    var lastRandomFoxPresenter: RandomFoxPresenter? = null

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
                is RandomFoxFragment -> lastRandomFoxPresenter = curFragment.mPresenter
            }
            fragmentManager.popBackStack()
        }
    }
}