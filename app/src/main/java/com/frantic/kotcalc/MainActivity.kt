package com.frantic.kotcalc

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.annotation.IntegerRes
import android.view.View
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity(){

    lateinit var tvPreview:TextView
    lateinit var tvResult:TextView


    var operation:StringBuilder = StringBuilder()
    var num:StringBuilder = StringBuilder()

    var lastOperation:String = "="
    var result:Double = 0.0

    var isOperation:Boolean = false

    var operations:ArrayList<String> = ArrayList<String>()
    var nums:ArrayList<Double> = ArrayList<Double>()

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvPreview = findViewById(R.id.tvPreview)
        tvResult = findViewById(R.id.tvResult)
    }

    fun onNumberClick(v:View){
        if(isOperation){
            when(v.id){
                R.id.btnDot->{
                    num.append("0 ${(v as Button).text}")
                }
                else -> num.append((v as Button).text)
            }
            val curDigit = num.toString().replace(",",".").toDouble()
            when(lastOperation){
                "+" -> result.plus(curDigit)
                "-" -> result.minus(curDigit)
                "*" -> result = result * curDigit
                ":" -> result.div(curDigit)
            }
            operation.append(num)
            tvResult.text = operation.toString()
            tvPreview.text = result.toString()
        }else{
            when(v.id){
                R.id.btnDot->{
                    num.append("0 ${(v as Button).text}")
                }
            else -> num.append((v as Button).text)
            }
            operation.append(num)
            tvResult.text = operation.toString()
        }
        isOperation = false
    }

    fun onOperationClick(v:View){

        if(v.id == R.id.btnDel){
            clear()
            return
        }

        if(isOperation){
            if(operations.size>0)operations.removeAt(operations.size-1)
            operations.add((v as Button).text.toString())

            if(operation.length>0)operation.deleteCharAt(operation.length-1)
            operation.append(v.text)

            tvResult.text = operation.toString()
        }else{
            val curDigit = num.toString().replace(",", ".").toDouble()

            nums.add(curDigit)
            operations.add((v as Button).text.toString())
            operation.append(v.text)

            tvResult.text = operation.toString()
        }
        isOperation = true
    }

    fun clear(){

    }
}
