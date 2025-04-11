package com.example.flightinformationtest.ModelView

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CalculatorViewModel : ViewModel() {

    private val _inputText = MutableLiveData<String>().apply { value = "0" }
    val inputText: LiveData<String> get() = _inputText

    private val _resultEvent = MutableLiveData<Double?>()
    val resultEvent: LiveData<Double?> get() = _resultEvent

    // 按下 "=" 後，將輸入值轉換為 Double，拋出給外部處理
    fun onButtonClick(buttonText: String) {
        when (buttonText) {
            "C" -> _inputText.value = "0"
            "←" -> backspace()
            "=" -> calculateResult()
            "." -> appendDot()
            else -> appendNumber(buttonText)
        }
    }

    private fun appendNumber(num: String) {
        val current = _inputText.value ?: "0"
        _inputText.value = if (current == "0") num else current + num
    }

    private fun appendDot() {
        val current = _inputText.value ?: "0"
        if (!current.contains(".")) {
            _inputText.value = "$current."
        }
    }

    private fun backspace() {
        val current = _inputText.value ?: "0"
        if (current.length > 1) {
            _inputText.value = current.dropLast(1)
        } else {
            _inputText.value = "0"
        }
    }

    private fun calculateResult() {
        val input = _inputText.value ?: return
        try {
            val result = input.toDouble()
            _resultEvent.value = if(result==0.0) 1.0 else result

            Log.d("CalculatorViewModel","計算機輸入: "+result)
        } catch (e: NumberFormatException) {
            _resultEvent.value = null
        }
    }

    fun clearResultEvent() {
        _resultEvent.value = null
        _inputText.value="0"
    }

    fun clearResult(){
        _inputText.value="0"
    }
}
