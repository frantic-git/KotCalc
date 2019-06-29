package com.frantic.kotcalc

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.annotation.IntegerRes
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity(){

    private val tag = "calc_log"

    lateinit var tvPreview:TextView
    lateinit var tvResult:TextView

    var operation:StringBuilder = StringBuilder()
    var num:StringBuilder = StringBuilder()

    var lastOperation:StringBuilder = StringBuilder()

    var result:Double = 0.0
    var isOperation:Boolean = false

    var isDivByZero:Boolean = false

    var operations:ArrayList<String> = ArrayList()
    var nums:ArrayList<Double> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvPreview = findViewById(R.id.tvPreview)
        tvResult = findViewById(R.id.tvResult)

        lastOperation.append("=")
    }

    //обработчик чисел
    fun onNumberClick(v:View){
        var numberName = (v as Button).text.toString()

        if(v.id == R.id.btnDot){
            if(num.isEmpty()) numberName = "0$numberName"
            if(num.contains("-") && num.length == 1) numberName = "0$numberName"
            if(num.contains("."))return
        }

        num.append(numberName)
        operation.append(numberName)
        isOperation = false
        tvResult.text = operation
    }

    //обработчик операций
    fun onOperationClick(v:View){

        val operatorName = (v as Button).text.toString()

        when(v.id){
            R.id.btnEq->{
                calcEqually(operatorName)
            }
            R.id.btnDel->{
                calcDelete(operatorName)
            }
            R.id.btnPlus->{
                calcPlus(operatorName)
            }
            R.id.btnMinus->{
                calcMinus(operatorName)
            }
            R.id.btnMult->{
                calcMult(operatorName)
            }
            R.id.btnDiv->{
                calcDiv(operatorName)
            }
        }
        if(isDivByZero){
            tvResult.text = getString(R.string.dividedByZero)
            isDivByZero = false
            calcClear()
        }else tvResult.text = operation
    }

    private fun calcMinus(operatorName:String){
        //если последний оператор равно, представление числа содержит "-" и больше ничего
        if(lastOperation.toString() == "=" && num.contains(operatorName) && num.length==1)return
        //если последний оператор равно и представление числа пустое
        if(lastOperation.toString() == "=" && num.isEmpty()){
            num.append(operatorName)
            operation.append(operatorName)
            return
        }
        //если последний оператор равно, представление числа содержит "-" и еще числа
        if(lastOperation.toString() == "=" && num.contains(operatorName) && num.length>1){
            lastOperation.clear()
            lastOperation.append(operatorName)
            isOperation = true

            result = num.toString().toDouble()
            num.clear()

            operation.append(operatorName)
            return
        }
        //если последний оператор равно, представление не содержит "-", но имеются числа
        if(lastOperation.toString() == "=" && !num.contains(operatorName) && num.isNotEmpty()){
            lastOperation.clear()
            lastOperation.append(operatorName)
            isOperation = true

            result = num.toString().toDouble()
            num.clear()

            operation.append(operatorName)
            return
        }
        //если последний оператор не равно, последнее событие "операция"
        if(isOperation){
            when(lastOperation.toString()){
                operatorName->return
                "+"->{
                    lastOperation.clear()
                    lastOperation.append(operatorName)

                    operation.deleteCharAt(operation.length-1)
                    operation.append(operatorName)
                    return
                }
                else->{
                    isOperation=false
                    num.append(operatorName)
                    operation.append(operatorName)
                    return
                }
            }
        }
        //если последний оператор не равно, последнее событие "число"
        calcResult()

        isOperation = true
        lastOperation.clear()
        lastOperation.append(operatorName)
        num.clear()

        operation.clear()
        operation.append(takeDoubleWithoutTail(result))
        operation.append(operatorName)
    }

    private fun calcPlus(operatorName:String){
        if(lastOperation.toString() == "=" && num.isEmpty())return
        if(lastOperation.toString() == "=" && num.contains("-") && num.length==1){
            num.clear()
            operation.deleteCharAt(operation.length-1)
            return
        }
        if(lastOperation.toString() == "=" && num.isNotEmpty()){
            lastOperation.clear()
            lastOperation.append(operatorName)
            isOperation = true

            result = num.toString().toDouble()
            num.clear()

            operation.append(operatorName)
            return
        }
        //если последний оператор не равно, последнее событие "операция"
        if(isOperation){
            when(lastOperation.toString()){
                operatorName->return
                else->{
                    lastOperation.clear()
                    lastOperation.append(operatorName)

                    operation.deleteCharAt(operation.length-1)
                    operation.append(operatorName)
                    return
                }
            }
        }
        //если последний оператор не равно, последнее событие "число"
        calcResult()

        isOperation = true
        lastOperation.clear()
        lastOperation.append(operatorName)
        num.clear()

        operation.clear()
        operation.append(takeDoubleWithoutTail(result))
        operation.append(operatorName)
    }

    private fun calcMult(operatorName: String){
        if(lastOperation.toString() == "=" && num.isEmpty())return
        if(lastOperation.toString() == "=" && num.contains("-") && num.length==1){
            num.clear()
            operation.deleteCharAt(operation.length-1)
            return
        }
        if(lastOperation.toString() == "=" && num.isNotEmpty()){
            lastOperation.clear()
            lastOperation.append(operatorName)
            isOperation = true

            result = num.toString().toDouble()
            num.clear()

            operation.append(operatorName)
            return
        }
        //если последний оператор не равно, последнее событие "операция"
        if(isOperation){
            when(lastOperation.toString()){
                operatorName->return
                else->{
                    lastOperation.clear()
                    lastOperation.append(operatorName)

                    operation.deleteCharAt(operation.length-1)
                    operation.append(operatorName)
                    return
                }
            }
        }
        calcResult()

        isOperation = true
        lastOperation.clear()
        lastOperation.append(operatorName)
        num.clear()

        operation.clear()
        operation.append(takeDoubleWithoutTail(result))
        operation.append(operatorName)
    }

    private fun calcDiv(operatorName: String){
        if(lastOperation.toString() == "=" && num.isEmpty())return
        if(lastOperation.toString() == "=" && num.contains("-") && num.length==1){
            num.clear()
            operation.deleteCharAt(operation.length-1)
            return
        }
        if(lastOperation.toString() == "=" && num.isNotEmpty()){
            lastOperation.clear()
            lastOperation.append(operatorName)
            isOperation = true

            result = num.toString().toDouble()
            num.clear()

            operation.append(operatorName)
            return
        }
        //если последний оператор не равно, последнее событие "операция"
        if(isOperation){
            when(lastOperation.toString()){
                operatorName->return
                else->{
                    lastOperation.clear()
                    lastOperation.append(operatorName)

                    operation.deleteCharAt(operation.length-1)
                    operation.append(operatorName)
                    return
                }
            }
        }
        calcResult()

        isOperation = true
        lastOperation.clear()
        lastOperation.append(operatorName)
        num.clear()

        operation.clear()
        operation.append(takeDoubleWithoutTail(result))
        operation.append(operatorName)
    }

    private fun calcEqually(operatorName: String){
        //если последний оператор равно и последнее действие число
        if(lastOperation.toString() == operatorName && !isOperation)return
        //если последний оператор не равно, последнее событие "число"
        if(num.isNotEmpty())calcResult()

        isOperation = false
        lastOperation.clear()
        lastOperation.append(operatorName)
        num.clear()
        num.append(takeDoubleWithoutTail(result))
        operation.clear()
        operation.append(num)
    }

    private fun calcDelete(operatorName: String){
        calcClear()
    }

    fun takeDoubleWithoutTail(res:Double):String{
        result = Formatter().format("%.10f",res).toString().replace(",",".").toDouble()
        val s = result.toString()
        val ms = s.split(".")
        val curTail = "0.${ms[ms.size-1]}"
        if(curTail.toDouble() == 0.0)return ms[0]
        return s
    }

    fun calcResult(){
        val curDigit = num.toString().toDouble()
        when(lastOperation.toString()){
            "+"-> result += curDigit
            "-"->result -= curDigit
            "*"->result *= curDigit
            ":"->if (curDigit == 0.0){
                Log.d(tag, "Divided by zero!!")
                isDivByZero = true
                return
            }else result /= curDigit
        }
    }

    private fun calcClear(){
        isOperation=false
        lastOperation.clear()
        lastOperation.append("=")
        operation.clear()
        num.clear()
        result=0.0
    }
}

