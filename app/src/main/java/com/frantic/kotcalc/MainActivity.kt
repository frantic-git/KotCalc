package com.frantic.kotcalc

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.annotation.IntegerRes
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity(),View.OnLongClickListener{

    private val tag = "calc_log"

    lateinit var tvResult:TextView
    lateinit var btnDel:Button

    var operation:StringBuilder = StringBuilder()
    var num:StringBuilder = StringBuilder()
    var lastOperation:StringBuilder = StringBuilder()

    var result:Double = 0.0

    var isOperation:Boolean = false
    var isDivByZero:Boolean = false
    var isCLR:Boolean = false

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvResult = findViewById(R.id.tvResult)
        btnDel = findViewById(R.id.btnDel)
        tvResult.setOnLongClickListener(this)
        btnDel.setOnLongClickListener(this)

        lastOperation.append("=")
    }

    //обработчик чисел
    fun onNumberClick(v:View){

        if(isCLR) {
            isCLR = false
            btnDel.text = getString(R.string.del)
        }

        var numberName = (v as Button).text.toString()

        if(v.id == R.id.btnDot){
            if(num.isEmpty()) numberName = "0$numberName"
            if(num.contains("-") && num.length == 1) numberName = "0$numberName"
            if(num.contains("."))return
        }

        if(v.id == R.id.btn0 && num.toString() == "0")return

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
                calcDelete()
            }
            R.id.btnMinus->{
                calcMinus(operatorName)
            }
            else->calcOperation(operatorName)
        }

        if(v.id != R.id.btnEq){
            isCLR = false
            btnDel.text = getString(R.string.del)
        }

        if(isDivByZero){
            tvResult.text = getString(R.string.dividedByZero)
            isDivByZero = false
            calcClear()
        }else tvResult.text = operation
    }

    private fun calcMinus(operatorName:String){

        if(lastOperation.toString() == "=" && num.contains(operatorName) && num.length==1)return

        if(lastOperation.toString() == "=" && num.isEmpty()){
            num.append(operatorName)
            operation.append(operatorName)
            return
        }

        if(lastOperation.toString() == "=" && num.contains(operatorName) && num.length>1){
            lastOperation.clear()
            lastOperation.append(operatorName)
            isOperation = true
            result = num.toString().toDouble()
            num.clear()
            operation.append(operatorName)
            return
        }

        if(lastOperation.toString() == "=" && !num.contains(operatorName) && num.isNotEmpty()){
            lastOperation.clear()
            lastOperation.append(operatorName)
            isOperation = true
            result = num.toString().toDouble()
            num.clear()
            operation.append(operatorName)
            return
        }

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

        calcResult()
        updateOperationView(operatorName)
    }

    private fun calcOperation(operatorName:String){

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
        updateOperationView(operatorName)
    }

    private fun updateOperationView(operatorName: String){
        isOperation = true
        lastOperation.clear()
        lastOperation.append(operatorName)
        num.clear()
        operation.clear()
        operation.append(takeDoubleWithoutTail(result))
        operation.append(operatorName)
    }

    private fun calcEqually(operatorName: String){

        if(lastOperation.toString() == operatorName && !isOperation)return

        if(num.isNotEmpty())calcResult()

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

    private fun calcDelete(){

        if(isCLR){
            calcClear()
            isCLR = false
            btnDel.text = getString(R.string.del)
            return
        }

        if(lastOperation.toString() == "="){
            num.deleteCharAt(num.length-1)
            operation.deleteCharAt(operation.length-1)
            return
        }

        if(isOperation){
            lastOperation.clear()
            lastOperation.append("=")
            operation.deleteCharAt(operation.length-1)
            num.append(takeDoubleWithoutTail(result))
            result = 0.0
            return
        }

        num.deleteCharAt(num.length-1)
        operation.deleteCharAt(operation.length-1)
        if(num.isEmpty() && lastOperation.toString() != "=")isOperation = true
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
            "+"->result += curDigit
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

    override fun onLongClick(v: View?): Boolean {
        if(v!=null){
            when(v.id){
                R.id.btnDel->{
                    calcClear()
                    isCLR = false
                    btnDel.text = getString(R.string.del)
                    tvResult.text = operation
                    Toast.makeText(this,"Cleared",Toast.LENGTH_SHORT).show()
                }
                R.id.tvResult->{
                    val clipBoard:ClipboardManager = this.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                    val clip = ClipData.newPlainText("",tvResult.text.toString())
                    clipBoard.primaryClip = clip
                    Toast.makeText(this,"Copied to the ClipBoard",Toast.LENGTH_SHORT).show()
                }
            }
        }

        return true
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState!!.putString("operation", operation.toString())
        outState.putString("num", num.toString())
        outState.putString("lastOperation", lastOperation.toString())
        outState.putDouble("result", result)
        outState.putBoolean("isOperation", isOperation)
        outState.putBoolean("isDivByZero", isDivByZero)
        outState.putBoolean("isCLR", isCLR)
    }

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
}

