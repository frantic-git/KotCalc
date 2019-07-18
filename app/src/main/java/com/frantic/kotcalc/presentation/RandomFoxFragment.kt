package com.frantic.kotcalc.presentation

import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.frantic.kotcalc.R
import com.frantic.kotcalc.domain.RandomFoxPresenter
import com.frantic.kotcalc.domain.Screens
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
        btnList.setOnClickListener { btnListOnClick() }

        if (mPresenter.byteArray != null) {
            showFox(mPresenter.byteArray!!)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putByteArray("byteArray", mPresenter.byteArray)
        outState.putString("imageName", mPresenter.imageName)
    }

    private fun onRestoreInstanceState(savedInstanceState: Bundle?) {

        val lastPresenter: RandomFoxPresenter? = (activity as MainActivity).fragmentRouter.lastRandomFoxPresenter

        if (savedInstanceState != null) {
            mPresenter = RandomFoxPresenter(this)
            mPresenter.byteArray = savedInstanceState.getByteArray("byteArray")
            mPresenter.imageName = savedInstanceState.getString("imageName")
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

    private fun btnListOnClick(){
        (activity as MainActivity).fragmentRouter.replace(Screens.FRAGMENTS.FOX_LIST_FRAGMENT)
    }

    override fun showFox(byteArray: ByteArray) {
        val bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
        imageView.setImageBitmap(bitmap)
    }

}