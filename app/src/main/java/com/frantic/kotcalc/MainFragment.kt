package com.frantic.kotcalc

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.frantic.kotcalc.domain.CalcPresenter
import com.frantic.kotcalc.domain.Screens
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var lastPresenter: CalcPresenter? = (activity as MainActivity).fragmentRouter.lastCalcPresenter
        if (lastPresenter == null && savedInstanceState != null){
            if (savedInstanceState.getBoolean("lastCalcPresenter")){

                lastPresenter = CalcPresenter()
                lastPresenter.operation.append(savedInstanceState.getString("operation"))
                lastPresenter.num.append(savedInstanceState.getString("num"))
                lastPresenter.lastOperation.clear()
                lastPresenter.lastOperation.append(savedInstanceState.getString("lastOperation"))
                lastPresenter.result = savedInstanceState.getDouble("result")
                lastPresenter.isCLR = savedInstanceState.getBoolean("isCLR")
                lastPresenter.isOperation = savedInstanceState.getBoolean("isOperation")
                lastPresenter.isDivByZero = savedInstanceState.getBoolean("isDivByZero")
                (activity as MainActivity).fragmentRouter.lastCalcPresenter = lastPresenter
            }
        }

        btnToCalc?.setOnClickListener { onBtnToCalcClick() }
        btnToTranslate?.setOnClickListener { onBtnToTranslateClick() }
    }

    private fun onBtnToCalcClick() {
        (activity as MainActivity).fragmentRouter.replace(Screens.FRAGMENTS.CALC_FRAGMENT)
    }

    private fun onBtnToTranslateClick() {
        (activity as MainActivity).fragmentRouter.replace(Screens.FRAGMENTS.TRANSLATE_FRAGMENT)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        val lastCalcPresenter = (activity as MainActivity).fragmentRouter.lastCalcPresenter
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
}