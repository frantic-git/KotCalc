package com.frantic.kotcalc

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.frantic.kotcalc.domain.TranslatePresenter
import com.frantic.kotcalc.presentation.TranslateView
import kotlinx.android.synthetic.main.fragment_translate.*

class TranslateFragment : Fragment(), TranslateView {

    lateinit var mPresenter: TranslatePresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_translate, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val lastPresenter: TranslatePresenter? = (activity as MainActivity).fragmentRouter.lastTranslatePresenter

        if (lastPresenter != null) {
            mPresenter = lastPresenter
            mPresenter.mView = this
        } else {
            mPresenter = TranslatePresenter(this)
        }

        btnTranslate.setOnClickListener { btnTranslateClick() }
        btnSave?.setOnClickListener { btnSaveClick() }
    }

    private fun btnTranslateClick() {
        mPresenter.btnTranslateClick()
    }

    private fun btnSaveClick() {
        mPresenter.btnSaveClick()
    }
}