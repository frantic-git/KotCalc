package com.frantic.kotcalc.domain

import android.support.v4.app.Fragment
import com.frantic.kotcalc.CalcFragment
import com.frantic.kotcalc.MainFragment
import com.frantic.kotcalc.TranslateFragment

object Screens {

    enum class FRAGMENTS(val fragmentName: String) {
        MAIN_FRAGMENT("MAIN_FRAGMENT"),
        CALC_FRAGMENT("CALC_FRAGMENT"),
        TRANSLATE_FRAGMENT("TRANSLATE_FRAGMENT")
    }

    fun createFragment(fragment: FRAGMENTS): Fragment = when (fragment) {
        Screens.FRAGMENTS.MAIN_FRAGMENT -> MainFragment()
        Screens.FRAGMENTS.CALC_FRAGMENT -> CalcFragment()
        Screens.FRAGMENTS.TRANSLATE_FRAGMENT -> TranslateFragment()
    }
}