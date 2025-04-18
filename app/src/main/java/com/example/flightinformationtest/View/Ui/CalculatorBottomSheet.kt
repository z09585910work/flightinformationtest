package com.example.flightinformationtest.View.Ui

import android.content.Context
import android.view.LayoutInflater
import android.widget.ArrayAdapter
import androidx.lifecycle.LifecycleOwner
import com.example.flightinformationtest.ModelView.CalculatorViewModel
import com.example.flightinformationtest.R
import com.example.flightinformationtest.databinding.CalculatorLayoutBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog

class CalculatorBottomSheet(
    private val context: Context,
    private val lifecycleOwner: LifecycleOwner,
    private val viewModel: CalculatorViewModel
) {
    private lateinit var binding: CalculatorLayoutBinding
    private val bottomSheetDialog: BottomSheetDialog = BottomSheetDialog(context)

    private var onResultCallback: ((Double) -> Unit)? = null

    init {
        binding = CalculatorLayoutBinding.inflate(LayoutInflater.from(context))
        bottomSheetDialog.setContentView(binding.root)
        bottomSheetDialog.behavior.state = BottomSheetBehavior.STATE_EXPANDED
        bottomSheetDialog.setCancelable(true)
        bottomSheetDialog.setCanceledOnTouchOutside(true)

        setupGrid()
        observeViewModel()
    }

    fun setOnResult(callback: (Double) -> Unit) {
        onResultCallback = callback
    }

    fun show() {
        viewModel.clearResult()  // reset input text
        bottomSheetDialog.show()
    }

    fun dismiss() {
        bottomSheetDialog.dismiss()
        viewModel.clearResult()
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