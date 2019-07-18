package com.frantic.kotcalc.domain

import androidx.fragment.app.Fragment
import com.frantic.kotcalc.presentation.CalcFragment
import com.frantic.kotcalc.presentation.FoxListFragment
import com.frantic.kotcalc.presentation.MainFragment
import com.frantic.kotcalc.presentation.RandomFoxFragment

object Screens {

    enum class FRAGMENTS(val fragmentName: String) {
        MAIN_FRAGMENT("MAIN_FRAGMENT"),
        CALC_FRAGMENT("CALC_FRAGMENT"),
        RANDOM_FOX_FRAGMENT("RANDOM_FOX_FRAGMENT"),
        FOX_LIST_FRAGMENT("FOX_LIST_FRAGMENT")
    }

    fun createFragment(fragment: FRAGMENTS): Fragment = when (fragment) {
        Screens.FRAGMENTS.MAIN_FRAGMENT -> MainFragment()
        Screens.FRAGMENTS.CALC_FRAGMENT -> CalcFragment()
        Screens.FRAGMENTS.RANDOM_FOX_FRAGMENT -> RandomFoxFragment()
        Screens.FRAGMENTS.FOX_LIST_FRAGMENT -> FoxListFragment()
    }
}