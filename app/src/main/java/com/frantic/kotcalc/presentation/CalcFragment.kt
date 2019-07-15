package com.frantic.kotcalc.presentation

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import com.frantic.kotcalc.R
import com.frantic.kotcalc.domain.CalcPresenter
import kotlinx.android.synthetic.main.fragment_calc.*

class CalcFragment : Fragment(), CalcView {

    lateinit var mPresenter: CalcPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_calc, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        onRestoreInstanceState(savedInstanceState)

        tvResult.setOnLongClickListener { onTvResultLongClick() }
        btnDel.setOnLongClickListener { onBtnDelLongClick() }

        btn1.setOnClickListener { onNumberClick(it) }
        btn2.setOnClickListener { onNumberClick(it) }
        btn3.setOnClickListener { onNumberClick(it) }
        btn4.setOnClickListener { onNumberClick(it) }
        btn5.setOnClickListener { onNumberClick(it) }
        btn6.setOnClickListener { onNumberClick(it) }
        btn7.setOnClickListener { onNumberClick(it) }
        btn8.setOnClickListener { onNumberClick(it) }
        btn9.setOnClickListener { onNumberClick(it) }
        btn0.setOnClickListener { onNumberClick(it) }
        btnDot.setOnClickListener { onNumberClick(it) }

        btnEq.setOnClickListener { onOperationClick(it) }
        btnDel.setOnClickListener { onOperationClick(it) }
        btnDiv.setOnClickListener { onOperationClick(it) }
        btnMult.setOnClickListener { onOperationClick(it) }
        btnMinus.setOnClickListener { onOperationClick(it) }
        btnPlus.setOnClickListener { onOperationClick(it) }

        tvResult.text = mPresenter.operation
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("operation", mPresenter.operation.toString())
        outState.putString("num", mPresenter.num.toString())
        outState.putString("lastOperation", mPresenter.lastOperation.toString())
        outState.putDouble("result", mPresenter.result)
        outState.putBoolean("isOperation", mPresenter.isOperation)
        outState.putBoolean("isCLR", mPresenter.isCLR)
        outState.putBoolean("isDivByZero", mPresenter.isDivByZero)
    }

    private fun onRestoreInstanceState(savedInstanceState: Bundle?){

        val lastPresenter: CalcPresenter? = (activity as MainActivity).fragmentRouter.lastCalcPresenter

        if (savedInstanceState != null) {
            mPresenter = CalcPresenter(this)
            mPresenter.operation.append(savedInstanceState.getString("operation"))
            mPresenter.num.append(savedInstanceState.getString("num"))
            mPresenter.lastOperation.clear()
            mPresenter.lastOperation.append(savedInstanceState.getString("lastOperation"))
            mPresenter.result = savedInstanceState.getDouble("result")
            mPresenter.isCLR = savedInstanceState.getBoolean("isCLR")
            mPresenter.isOperation = savedInstanceState.getBoolean("isOperation")
            mPresenter.isDivByZero = savedInstanceState.getBoolean("isDivByZero")
        } else if (lastPresenter != null) {
            mPresenter = lastPresenter
            mPresenter.mView = this
        } else {
            mPresenter = CalcPresenter(this)
        }
    }

    override fun showResult(result: String) {
        tvResult.text = result
    }

    override fun setBtnDelText(text: String) {
        btnDel.text = text
    }

    private fun onNumberClick(v: View) {
        mPresenter.onNumberClick(v.id, (v as Button).text.toString())
    }

    private fun onOperationClick(v: View) {
        mPresenter.onOperationClick(v.id, (v as Button).text.toString())
    }

    private fun onBtnDelLongClick(): Boolean {
        mPresenter.onBtnDelLongClick()
        return true
    }

    private fun onTvResultLongClick(): Boolean {
        val clipBoard: ClipboardManager =
            activity?.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("", tvResult.text.toString())
        clipBoard.primaryClip = clip
        Toast.makeText(activity, "Copied to the ClipBoard", Toast.LENGTH_SHORT).show()
        return true
    }

}