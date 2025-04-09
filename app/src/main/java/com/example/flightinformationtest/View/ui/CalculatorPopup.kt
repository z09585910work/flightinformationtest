package com.example.flightinformationtest.View.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import com.example.flightinformationtest.ModelView.CalculatorViewModel
import com.example.flightinformationtest.R

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Gravity

import android.widget.Button
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LifecycleOwner
import com.example.flightinformationtest.databinding.PopupCalculatorBinding
import android.view.ViewGroup.LayoutParams

class CalculatorPopup(
    context: Context,
    private val lifecycleOwner: LifecycleOwner,
    private val viewModel: CalculatorViewModel
) {
    private val popupViewBinding: PopupCalculatorBinding
    private val popupWindow: PopupWindow

    init {
        val inflater = LayoutInflater.from(context)
        popupViewBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.popup_calculator,
            null,
            false
        )

        // 設定 DataBinding 的 ViewModel 和 LifecycleOwner

        popupViewBinding.lifecycleOwner = lifecycleOwner
        popupViewBinding.viewModel = viewModel

        popupWindow = PopupWindow(
            popupViewBinding.root,
            LayoutParams.MATCH_PARENT,
            LayoutParams.WRAP_CONTENT,
            true
        )
    }

    fun show(anchorView: View) {
        popupWindow.showAsDropDown(anchorView)
    }

    fun dismiss() {
        popupWindow.dismiss()
    }

    fun isShowing(): Boolean = popupWindow.isShowing
}
