package com.frantic.kotcalc.domain

import android.support.v4.app.FragmentManager

class Router(
    private val fragmentManager: FragmentManager,
    private val containerId: Int,
    private val finishActivity: () -> Unit
) {

    fun navigateTo(fragment: Screens.FRAGMENTS) {
        fragmentManager.beginTransaction()
            .add(containerId, Screens.createFragment(fragment))
            .addToBackStack(null)
            .commit()
    }

    fun replace(fragment: Screens.FRAGMENTS) {
        fragmentManager.beginTransaction()
            .replace(containerId, Screens.createFragment(fragment))
            .commit()
    }

    fun back() {
        if (fragmentManager.backStackEntryCount == 1) {
            finishActivity.invoke()
        } else {
            fragmentManager.popBackStack()
        }
    }
}