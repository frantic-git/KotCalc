package com.frantic.kotcalc

import android.graphics.Bitmap
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.frantic.kotcalc.domain.RandomFoxPresenter
import com.frantic.kotcalc.presentation.RandomFoxView
import kotlinx.android.synthetic.main.fragment_randomfox.*

class RandomFoxFragment: Fragment(), RandomFoxView {

    lateinit var mPresenter: RandomFoxPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_randomfox, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val lastPresenter: RandomFoxPresenter? = (activity as MainActivity).fragmentRouter.lastRandomFoxPresenter

        if (lastPresenter != null) {
            mPresenter = lastPresenter
            mPresenter.mView = this
        } else {
            mPresenter = RandomFoxPresenter(this)
        }

        btnGetRandomFox.setOnClickListener { btnGetRandomFoxOnClick() }
        btnSave.setOnClickListener { btnSaveOnClick() }
    }

    private fun btnGetRandomFoxOnClick() {
        mPresenter.btnGetRandomFoxOnClick()
    }

    private fun btnSaveOnClick() {
        mPresenter.btnSaveOnClick()
    }

    override fun showFox(bitmap: Bitmap) {
        imageView.setImageBitmap(bitmap)
    }
}