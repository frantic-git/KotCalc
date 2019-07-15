package com.frantic.kotcalc

import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.frantic.kotcalc.domain.RandomFoxPresenter
import com.frantic.kotcalc.presentation.RandomFoxView
import kotlinx.android.synthetic.main.fragment_randomfox.*

class RandomFoxFragment : androidx.fragment.app.Fragment(), RandomFoxView {

    lateinit var mPresenter: RandomFoxPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_randomfox, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        onRestoreInstanceState(savedInstanceState)

        btnGetRandomFox.setOnClickListener { btnGetRandomFoxOnClick() }
        btnSave.setOnClickListener { btnSaveOnClick() }

        if (mPresenter.byteArray != null) {
            showFox(mPresenter.byteArray!!)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putByteArray("byteArray", mPresenter.byteArray)
    }

    private fun onRestoreInstanceState(savedInstanceState: Bundle?) {

        val lastPresenter: RandomFoxPresenter? = (activity as MainActivity).fragmentRouter.lastRandomFoxPresenter

        if (savedInstanceState != null) {
            mPresenter = RandomFoxPresenter(this)
            mPresenter.byteArray = savedInstanceState.getByteArray("byteArray")
        } else if (lastPresenter != null) {
            mPresenter = lastPresenter
            mPresenter.mView = this
        } else {
            mPresenter = RandomFoxPresenter(this)
        }
    }

    private fun btnGetRandomFoxOnClick() {
        mPresenter.btnGetRandomFoxOnClick()
    }

    private fun btnSaveOnClick() {
        mPresenter.btnSaveOnClick()
    }

    override fun showFox(byteArray: ByteArray) {
        val bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
        imageView.setImageBitmap(bitmap)
    }
}