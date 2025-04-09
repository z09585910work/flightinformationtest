package com.example.flightinformationtest.ModelView

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
//import net.objecthunter.exp4j.ExpressionBuilder


class CalculatorViewModel : ViewModel() {

    private val _output = MutableLiveData<String>()
    val output: LiveData<String> = _output

    private val inputBuilder = StringBuilder()

    fun onButtonClick(value: String) {
        inputBuilder.append(value)
        _output.value = inputBuilder.toString()
    }

    fun clearInput() {
        inputBuilder.clear()
        _output.value = ""
    }

    fun deleteLastChar() {
        if (inputBuilder.isNotEmpty()) {
            inputBuilder.deleteAt(inputBuilder.length - 1)
            _output.value = inputBuilder.toString()
        }
    }

//    fun calculateResult() {
//        try {
//            val result = eval(inputBuilder.toString())
//            _output.value = result.toString()
//            inputBuilder.clear()
//            inputBuilder.append(result.toString())
//        } catch (e: Exception) {
//            _output.value = "Error"
//        }
//    }

//    private fun eval(expr: String): Double {
//        val expression = ExpressionBuilder(expr).build()
//        return expression.evaluate()
//    }
}