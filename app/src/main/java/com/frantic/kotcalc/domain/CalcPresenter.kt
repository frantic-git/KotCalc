package com.frantic.kotcalc.domain

import com.frantic.kotcalc.R
import com.frantic.kotcalc.presentation.CalcView
import java.util.*
import kotlin.collections.HashMap

class CalcPresenter(
    private val mView: CalcView
) {

    var operation: StringBuilder = StringBuilder()
    var num: StringBuilder = StringBuilder()
    var lastOperation: StringBuilder = StringBuilder()

    var result: Double = 0.0

    var isOperation: Boolean = false
    var isDivByZero: Boolean = false
    var isCLR: Boolean = false

    private val DEL: String = "DEL"
    private val CLR: String = "CLR"

    fun onNumberClick(viewId: Int, name: String) {

        if (isCLR) {
            isCLR = false
            mView.setBtnDelText(DEL)
        }

        var numberName = name
        if (viewId == R.id.btnDot) {
            if (num.isEmpty()) numberName = "0$numberName"
            if (num.contains("-") && num.length == 1) numberName = "0$numberName"
            if (num.contains(".")) return
        }

        if (viewId == R.id.btn0 && num.toString() == "0") return

        num.append(numberName)
        operation.append(numberName)
        isOperation = false

        mView.showResult(operation.toString())
    }

    fun onOperationClick(viewId: Int, operatorName: String) {
        when (viewId) {
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

        if (viewId != R.id.btnEq) {
            isCLR = false
            mView.setBtnDelText(DEL)
        }

        if (isDivByZero) {
            mView.showResult("Divided by zero")
            isDivByZero = false
            calcClear()
        } else mView.showResult(operation.toString())
    }

    fun onBtnDelLongClick() {
        calcClear()
        isCLR = false
        mView.setBtnDelText(DEL)
        mView.showResult(operation.toString())
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
        mView.setBtnDelText(CLR)
    }

    private fun calcDelete() {

        if (isCLR) {
            calcClear()
            isCLR = false
            mView.setBtnDelText(DEL)
            return
        }

        if (lastOperation.toString() == "=" && num.isNotEmpty()) {
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
            isOperation = false
            return
        }

        if (num.isNotEmpty()) {
            num.deleteCharAt(num.length - 1)
            operation.deleteCharAt(operation.length - 1)
        }

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

    fun onSaveInstanceState() {
        val outState: HashMap<String, Any> = HashMap<String, Any>()
        outState.put("operation", operation.toString())
        outState.put("num", num.toString())
        outState.put("lastOperation", lastOperation.toString())
        outState.put("result", result)
        outState.put("isOperation", isOperation)
        outState.put("isDivByZero", isDivByZero)
        outState.put("isCLR", isCLR)
    }

    fun initLastOperation() {
        lastOperation.append("=")
    }

    fun restoreSaveInstanceState(savedInstanceState: HashMap<String, Any>) {
        operation.append(savedInstanceState.get("operation"))
        num.append(savedInstanceState.get("num"))
        lastOperation.clear()
        lastOperation.append(savedInstanceState.get("lastOperation"))
        result = savedInstanceState.get("result") as Double
        isOperation = savedInstanceState.get("isOperation") as Boolean
        isDivByZero = savedInstanceState.get("isDivByZero") as Boolean
        isCLR = savedInstanceState.get("isCLR") as Boolean

        mView.showResult(operation.toString())
    }

}