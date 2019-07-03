package com.frantic.kotcalc

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_main.*
import java.util.*

class MainFragment : Fragment() {

    private val calcLog = "calc_log"

    var operation: StringBuilder = StringBuilder()
    var num: StringBuilder = StringBuilder()
    var lastOperation: StringBuilder = StringBuilder()

    var result: Double = 0.0

    var isOperation: Boolean = false
    var isDivByZero: Boolean = false
    var isCLR: Boolean = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Log.d(calcLog,"onCreateView")
        return inflater.inflate(R.layout.fragment_main, container, true)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        /*
        tvResult!!.text = "OK"

        Log.d(calcLog,"onCreate")
        tvResult.setOnLongClickListener { onBtnDelLongClick() }
        btnDel.setOnLongClickListener { onTvResultLongClick() }

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
        */

        lastOperation.append("=")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("operation", operation.toString())
        outState.putString("num", num.toString())
        outState.putString("lastOperation", lastOperation.toString())
        outState.putDouble("result", result)
        outState.putBoolean("isOperation", isOperation)
        outState.putBoolean("isDivByZero", isDivByZero)
        outState.putBoolean("isCLR", isCLR)
    }

    fun onNumberClick(v: View) {

        if (isCLR) {
            isCLR = false
            btnDel.text = getString(R.string.del)
        }

        var numberName = (v as Button).text.toString()

        if (v.id == R.id.btnDot) {
            if (num.isEmpty()) numberName = "0$numberName"
            if (num.contains("-") && num.length == 1) numberName = "0$numberName"
            if (num.contains(".")) return
        }

        if (v.id == R.id.btn0 && num.toString() == "0") return

        num.append(numberName)
        operation.append(numberName)
        isOperation = false
        tvResult.text = operation
    }

    fun onOperationClick(v: View) {

        val operatorName = (v as Button).text.toString()

        when (v.id) {
            R.id.btnEq -> {
                calcEqually(operatorName)
            }
            R.id.btnDel -> {
                calcDelete()
            }
            R.id.btnMinus -> {
                calcMinus(operatorName)
            }
            else -> calcOperation(operatorName)
        }

        if (v.id != R.id.btnEq) {
            isCLR = false
            btnDel.text = getString(R.string.del)
        }

        if (isDivByZero) {
            tvResult.text = getString(R.string.dividedByZero)
            isDivByZero = false
            calcClear()
        } else tvResult.text = operation
    }

    private fun calcMinus(operatorName: String) {

        if (lastOperation.toString() == "=" && num.contains(operatorName) && num.length == 1) return

        if (lastOperation.toString() == "=" && num.isEmpty()) {
            num.append(operatorName)
            operation.append(operatorName)
            return
        }

        if (lastOperation.toString() == "=" && num.contains(operatorName) && num.length > 1) {
            lastOperation.clear()
            lastOperation.append(operatorName)
            isOperation = true
            result = num.toString().toDouble()
            num.clear()
            operation.append(operatorName)
            return
        }

        if (lastOperation.toString() == "=" && !num.contains(operatorName) && num.isNotEmpty()) {
            lastOperation.clear()
            lastOperation.append(operatorName)
            isOperation = true
            result = num.toString().toDouble()
            num.clear()
            operation.append(operatorName)
            return
        }

        if (isOperation) {
            when (lastOperation.toString()) {
                operatorName -> return
                "+" -> {
                    lastOperation.clear()
                    lastOperation.append(operatorName)
                    operation.deleteCharAt(operation.length - 1)
                    operation.append(operatorName)
                    return
                }
                else -> {
                    isOperation = false
                    num.append(operatorName)
                    operation.append(operatorName)
                    return
                }
            }
        }

        calcResult()
        updateOperationView(operatorName)
    }

    private fun calcOperation(operatorName: String) {

        if (lastOperation.toString() == "=" && num.isEmpty()) return

        if (lastOperation.toString() == "=" && num.contains("-") && num.length == 1) {
            num.clear()
            operation.deleteCharAt(operation.length - 1)
            return
        }

        if (lastOperation.toString() == "=" && num.isNotEmpty()) {
            lastOperation.clear()
            lastOperation.append(operatorName)
            isOperation = true

            result = num.toString().toDouble()
            num.clear()

            operation.append(operatorName)
            return
        }

        if (isOperation) {
            when (lastOperation.toString()) {
                operatorName -> return
                else -> {
                    lastOperation.clear()
                    lastOperation.append(operatorName)

                    operation.deleteCharAt(operation.length - 1)
                    operation.append(operatorName)
                    return
                }
            }
        }

        calcResult()
        updateOperationView(operatorName)
    }

    private fun updateOperationView(operatorName: String) {
        isOperation = true
        lastOperation.clear()
        lastOperation.append(operatorName)
        num.clear()
        operation.clear()
        operation.append(takeDoubleWithoutTail(result))
        operation.append(operatorName)
    }

    private fun calcEqually(operatorName: String) {

        if (lastOperation.toString() == operatorName && !isOperation) return

        if (num.isNotEmpty()) calcResult()

        isOperation = false
        lastOperation.clear()
        lastOperation.append(operatorName)
        num.clear()
        num.append(takeDoubleWithoutTail(result))
        operation.clear()
        operation.append(num)

        isCLR = true
        btnDel.text = getString(R.string.clr)
    }

    private fun calcDelete() {

        if (isCLR) {
            calcClear()
            isCLR = false
            btnDel.text = getString(R.string.del)
            return
        }

        if (lastOperation.toString() == "=") {
            num.deleteCharAt(num.length - 1)
            operation.deleteCharAt(operation.length - 1)
            return
        }

        if (isOperation) {
            lastOperation.clear()
            lastOperation.append("=")
            operation.deleteCharAt(operation.length - 1)
            num.append(takeDoubleWithoutTail(result))
            result = 0.0
            return
        }

        num.deleteCharAt(num.length - 1)
        operation.deleteCharAt(operation.length - 1)
        if (num.isEmpty() && lastOperation.toString() != "=") isOperation = true
    }

    fun takeDoubleWithoutTail(res: Double): String {
        result = Formatter().format("%.10f", res).toString().replace(",", ".").toDouble()
        val s = result.toString()
        val ms = s.split(".")
        val curTail = "0.${ms[ms.size - 1]}"
        if (curTail.toDouble() == 0.0) return ms[0]
        return s
    }

    fun calcResult() {
        val curDigit = num.toString().toDouble()
        when (lastOperation.toString()) {
            "+" -> result += curDigit
            "-" -> result -= curDigit
            "*" -> result *= curDigit
            ":" -> if (curDigit == 0.0) {
                Log.d(tag, "Divided by zero!!")
                isDivByZero = true
                return
            } else result /= curDigit
        }
    }

    private fun calcClear() {
        isOperation = false
        lastOperation.clear()
        lastOperation.append("=")
        operation.clear()
        num.clear()
        result = 0.0
    }

    private fun onBtnDelLongClick(): Boolean {
        calcClear()
        isCLR = false
        btnDel.text = getString(R.string.del)
        tvResult.text = operation
        Toast.makeText(activity, "Cleared", Toast.LENGTH_SHORT).show()
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

    /*
    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        operation.append(savedInstanceState!!.getString("operation"))
        num.append(savedInstanceState.getString("num"))
        lastOperation.clear()
        lastOperation.append(savedInstanceState.getString("lastOperation"))
        result = savedInstanceState.getDouble("result")
        isOperation = savedInstanceState.getBoolean("isOperation")
        isDivByZero = savedInstanceState.getBoolean("isDivByZero")
        isCLR = savedInstanceState.getBoolean("isCLR")

        tvResult.text = operation
    }
    */
}