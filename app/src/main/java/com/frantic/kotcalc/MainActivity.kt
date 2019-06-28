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

    var lastOperation:String = "="
    var result:Double = 0.0

    var isOperation:Boolean = false

    var operations:ArrayList<String> = ArrayList()
    var nums:ArrayList<Double> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvPreview = findViewById(R.id.tvPreview)
        tvResult = findViewById(R.id.tvResult)
    }

    fun onNumberClick(v:View){
        if(isOperation){
            num.clear()
            when(v.id){
                R.id.btnDot->{
                    num.append("0 ${(v as Button).text}")
                }
                R.id.btnEq->{
                    tvResult.text = result.toString()
                    clear()
                    return
                }
                else -> {
                    num.append((v as Button).text)
                }
            }

            val curDigit = num.toString().toDouble()

            nums.add(curDigit)

            when(lastOperation){
                "+" -> result = result + curDigit
                "-" -> result = result - curDigit
                "*" -> result = result * curDigit
                ":" -> result = result / curDigit
            }
            Log.d(tag, "isOperation = $isOperation" +
                    " curDigit = $curDigit" +
                    " lastOperation = $lastOperation" +
                    " result = $result")
            tvPreview.text = result.toString()
        }else{
            when(v.id){
                R.id.btnDot->{
                    if(num.isEmpty()) num.append("0 ${(v as Button).text}")
                        else{
                            if(num.contains("."))return
                                else num.append((v as Button).text)
                    }
                }
                R.id.btnEq->{
                    tvResult.text = result.toString()
                    clear()
                    return
                }
            else ->{
                    num.append((v as Button).text)
                    val curDigit = num.toString().toDouble()
                    if(lastOperation == "=")
                        result = curDigit
                    //иначе у нас последняя операция не "="
                    else{

                        val lastDigit = nums[nums.size-1]

                        if(num.isEmpty())nums.removeAt(nums.size-1)
                        else {
                            nums.removeAt(nums.size-1)
                            nums.add(curDigit)
                        }

                        when(lastOperation){
                            "+" -> result = result - lastDigit + curDigit
                            "-" -> result = result + lastDigit - curDigit
                            "*" -> result = if(lastDigit == 0.0)nums[nums.size-2] * curDigit else result / lastDigit * curDigit
                            ":" -> result = if(lastDigit == 0.0)nums[nums.size-2] / curDigit else result * lastDigit / curDigit
                        }
                        tvPreview.text = result.toString()
                    }
                }
            }
        }

        Log.d(tag, num.toString())
        operation.append((v as Button).text)
        tvResult.text = operation.toString()
        isOperation = false
    }

    fun onOperationClick(v:View){

        if(v.id == R.id.btnDel){

            Log.d(tag, "lastOperation = $lastOperation isOperation = $isOperation")

            if(lastOperation == "="){
                result = 0.0
                clear()
                tvResult.text = operation.toString()
                return
            }

            if(num.isEmpty()){
                if(operations.size>0)operations.removeAt(operations.size-1)
                if(operation.isNotEmpty())operation.deleteCharAt(operation.length-1)

                if(operations.size>0)lastOperation = operations[operations.size-1]
                    else lastOperation = "="

                if(nums.size>0){
                    var s = nums[nums.size-1].toString()
                    val ms = s.split(".")
                    val curTail = "0.${ms[ms.size-1]}"
                    Log.d(tag,"Tail = $curTail")
                    if(curTail.toDouble() == 0.0)s = ms[0]

                    num.append(s)
                }
            }else{
                if(operation.isNotEmpty())operation.deleteCharAt(operation.length-1)
                if(num.isNotEmpty())num.deleteCharAt(num.length-1)

                var curDigit:Double? = null
                if(num.isNotEmpty())
                    curDigit = num.toString().toDouble()

                val lastDigit = nums[nums.size-1]

                if(num.isEmpty())nums.removeAt(nums.size-1)
                else {
                    nums.removeAt(nums.size-1)
                    if(curDigit != null)nums.add(curDigit)
                }

                when (lastOperation) {
                    "+" -> result = result - lastDigit + if(curDigit == null)0.0 else curDigit
                    "-" -> result = result + lastDigit - if(curDigit == null)0.0 else curDigit
                    "*" ->{
                        if(lastDigit == 0.0){
                            if(curDigit == null){
                                result = nums[nums.size - 1] * 1
                            }else {
                                result = nums[nums.size - 2] * curDigit
                            }
                        }else{
                            result = result / lastDigit * if(curDigit == null)1.0 else curDigit
                        }
                        //result = result / lastDigit * if(curDigit == null)1.0 else curDigit
                    }
                    ":" -> result = result * lastDigit / if(curDigit == null)1.0 else curDigit
                }

                tvPreview.text = result.toString()
            }

            tvResult.text = operation.toString()
            return
        }

//---------------------------------------------------------------

        if(isOperation){
            if(operations.size>0)operations.removeAt(operations.size-1)
            operations.add((v as Button).text.toString())

            if(operation.isNotEmpty())operation.deleteCharAt(operation.length-1)
            operation.append(v.text)

            tvResult.text = operation.toString()
        }else{

            if(lastOperation == "="){
                //в числа добавляем резалт
                if(num.isEmpty()) {
                    nums.add(result)
                    operation.append(result.toString())
                }else{
                    val curDigit = num.toString().toDouble()
                    nums.add(curDigit)
                }
            } else {
                val curDigit = num.toString().toDouble()

                nums.add(curDigit)
            }

            operations.add((v as Button).text.toString())
            operation.append((v).text)
            tvResult.text = operation.toString()
        }
        lastOperation = v.text.toString()
        isOperation = true
    }

    private fun clear(){
        tvPreview.text = ""

        operations.clear()
        nums.clear()

        operation.clear()
        num.clear()
        isOperation = false
        lastOperation = "="
    }
}
