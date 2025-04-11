package com.example.flightinformationtest.View.Ui

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.PopupWindow
import android.widget.TextView
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.flightinformationtest.ModelView.CalculatorViewModel
import com.example.flightinformationtest.R
import com.example.flightinformationtest.View.Ui.Adapter.CalculatorAdapter
import com.example.flightinformationtest.databinding.CalculatorLayoutBinding

class CalculatorPopup(
    private val context: Context,
    private val lifecycleOwner: LifecycleOwner,
    private val viewModel: CalculatorViewModel
) {

    private lateinit var binding: CalculatorLayoutBinding
    private val popupWindow: PopupWindow

    private var onResultCallback: ((Double) -> Unit)? = null

    init {
        binding = CalculatorLayoutBinding.inflate(LayoutInflater.from(context))
        popupWindow = PopupWindow(
            binding.root,
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT,
            true
        ).apply {
            isFocusable = true
            isOutsideTouchable = true
            elevation = 20f
            animationStyle = R.style.PopupAnimation
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }

        setupGrid()
        observeViewModel()
    }

    fun setOnResult(callback: (Double) -> Unit) {
        onResultCallback = callback
    }

    fun show(anchorView: View) {
        val location = IntArray(2)
        anchorView.getLocationOnScreen(location)
        val anchorX = location[0]
        val anchorY = location[1]

        val displayMetrics = context.resources.displayMetrics
        val screenHeight = displayMetrics.heightPixels
        val popupHeight = 400  // 預估高度，或用 measure 動態取得

        val finalY = if (anchorY + anchorView.height + popupHeight > screenHeight) {
            screenHeight - popupHeight - 20
        } else {
            anchorY + anchorView.height
        }
        binding.inputText.text="0"
        popupWindow.showAtLocation(anchorView, Gravity.TOP or Gravity.START, anchorX, finalY)
    }

    fun dismiss() {
        popupWindow.dismiss()
        binding.inputText.text="0"
    }

    private fun setupGrid() {
        val buttons = listOf(
            "7", "8", "9", "←",
            "4", "5", "6", "C",
            "1", "2", "3", ".",
            "0", "=", "", ""
        )

        val adapter = ArrayAdapter(context, R.layout.item_grid_keybutton, R.id.btnText, buttons)
        binding.gridView.numColumns = 4
        binding.gridView.adapter = adapter

        binding.gridView.setOnItemClickListener { _, _, position, _ ->
            val value = buttons[position]
            if (value.isNotBlank()) {
                viewModel.onButtonClick(value)
            }
        }
    }

    private fun observeViewModel() {
        viewModel.inputText.observe(lifecycleOwner) { input ->
            binding.inputText.text = input
            Log.d(" CalculatorPopup","inding.inputText.text = input: $input")
        }

        viewModel.resultEvent.observe(lifecycleOwner) { result ->
            result?.let {
                onResultCallback?.invoke(it)
                viewModel.clearResultEvent()
                dismiss()
            }
        }
    }
}