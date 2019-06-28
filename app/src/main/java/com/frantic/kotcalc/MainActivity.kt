package com.frantic.kotcalc

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.annotation.IntegerRes
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity(){

    val tag = "calc_log"

    lateinit var tvPreview:TextView
    lateinit var tvResult:TextView

    var operation:StringBuilder = StringBuilder()
    var num:StringBuilder = StringBuilder()

    var lastOperation:StringBuilder = StringBuilder()

    var result:Double = 0.0
    var isOperation:Boolean = false

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
        when(v.id){
            R.id.btnDot->{
                if(num.isEmpty()){
                    numberName = "0$numberName"
                    num.append(numberName)
                    operation.append(numberName)
                    isOperation = false
                    return
                }
                if(num.contains("-") && num.length == 1){
                    numberName = "0$numberName"
                    num.append(numberName)
                    operation.append(numberName)
                    isOperation = false
                    return
                }
            }
            else->{
                num.append(numberName)
                operation.append(numberName)
                isOperation = false
            }
        }

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

            }
            R.id.btnPlus->{
                calcPlus(operatorName)
            }
            R.id.btnMinus->{
                calcMinus(operatorName)
            }
            R.id.btnMult->{

            }
            R.id.btnDiv->{

            }
        }

        tvResult.text = operation
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
        val curDigit = num.toString().toDouble()
        when(lastOperation.toString()){
            "+"->result = result + curDigit
            "-"->result = result - curDigit
            "*"->result = result * curDigit
            ":"->if (curDigit == 0.0){
                    Log.d(tag, "Divided by zero!!")
                    return
                }else result = result / curDigit
        }

        lastOperation.clear()
        lastOperation.append("=")
        num.clear()
        num.append(operatorName)

        operation.clear()
        operation.append(result.toString())
        operation.append(operatorName)
    }

    private fun calcPlus(operatorName:String){
        //если последний оператор равно, представление числа содержит "+" и больше ничего
        //if(lastOperation.toString() == "=" && num.contains(operatorName) && num.length==1)return

        //если последний оператор равно и представление числа пустое
        if(lastOperation.toString() == "=" && num.isEmpty())return
        //если последний оператор равно, представление числа содержит тольо "-"
        if(lastOperation.toString() == "=" && num.contains("-") && num.length==1){
            num.clear()
            operation.deleteCharAt(operation.length-1)
            return
        }
        //если последний оператор равно, представление числа содержит "+" и еще числа
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
        val curDigit = num.toString().toDouble()
        when(lastOperation.toString()){
            "+"->result = result + curDigit
            "-"->result = result - curDigit
            "*"->result = result * curDigit
            ":"->if (curDigit == 0.0){
                Log.d(tag, "Divided by zero!!")
                return
            }else result = result / curDigit
        }

        lastOperation.clear()
        lastOperation.append("=")
        num.clear()
        num.append(operatorName)

        operation.clear()
        operation.append(result.toString())
        operation.append(operatorName)
    }

    private fun calcEqually(operatorName: String){

    }
}

