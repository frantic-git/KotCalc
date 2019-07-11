package com.frantic.kotcalc

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.frantic.kotcalc.domain.Screens
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnToCalc?.setOnClickListener { onBtnToCalcClick() }
        btnToTranslate?.setOnClickListener { onBtnToTranslateClick() }
    }

    private fun onBtnToCalcClick() {
        (activity as MainActivity).fragmentRouter.replace(Screens.FRAGMENTS.CALC_FRAGMENT)
    }

    private fun onBtnToTranslateClick() {
        (activity as MainActivity).fragmentRouter.replace(Screens.FRAGMENTS.TRANSLATE_FRAGMENT)
    }
}