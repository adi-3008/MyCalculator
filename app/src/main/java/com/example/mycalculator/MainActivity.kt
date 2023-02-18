package com.example.mycalculator

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.text.DecimalFormat

class MainActivity : AppCompatActivity() {

    private var tvInput : TextView? = null
    private var lastNumeric : Boolean = false
    private var lastDot : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvInput = findViewById(R.id.tvInput)
    }

    fun onDigit(view : View){
        tvInput?.append((view as Button).text)
        lastNumeric = true
    }

    fun onClear(view: View){
        tvInput?.text = ""
        lastDot = false
        lastNumeric = false
    }

    fun onDecimalPoint(view: View){

        if(lastNumeric && !lastDot){
            tvInput?.append(".")
            lastDot = true
            lastNumeric = false
        }
    }

    fun onOperator(view: View){
        tvInput?.text?.let {
            if(lastNumeric && !isOperatorAdded(it.toString())){
                tvInput?.append((view as Button).text)
                lastNumeric = false
                lastDot = false
            }
        }
    }

    fun onEqual(view: View){
        tvInput?.text?.let {
            if (lastNumeric){
                var tvValue = it.toString()
                var prefix = ""
                if (tvValue.startsWith("-")){
                    prefix = "-"
                    tvValue = tvValue.substring(1)
                }

                try {
                    if(tvValue.contains("-")){
                        var split = tvValue.split("-")
                        var result = (prefix + split[0]).toDouble() - split[1].toDouble()
                        tvInput?.text = result.toString()
                    }
                    else if(tvValue.contains("+")){
                        var split = tvValue.split("+")
                        var result = (prefix + split[0]).toDouble() + split[1].toDouble()
                        tvInput?.text = result.toString()
                    }
                    else if(tvValue.contains("/")){
                        var split = tvValue.split("/")
                        var result = (prefix + split[0]).toDouble() / split[1].toDouble()
                        tvInput?.text = result.toString()
                    }
                    else if(tvValue.contains("*")){
                        var split = tvValue.split("*")
                        var result = (prefix + split[0]).toDouble() * split[1].toDouble()
                        tvInput?.text = result.toString()
                    }

                }catch (e : java.lang.ArithmeticException){
                    e.printStackTrace()
                }
            }
        }
    }

    private fun removeZeroAfterDot(result: String): String{
        return if (result.contains(".0"))
            result.substring(0, result.indexOf(".0"))
        else
            result
    }

    private fun isOperatorAdded(value: String) : Boolean{
        return if(value.startsWith("-")) false
        else{
            value.contains("/")
                    || value.contains("*")
                    || value.contains("+")
                    || value.contains("-")
        }
    }
}