package com.example.flightinformationtest.View.Ui

import android.content.Context
import android.view.LayoutInflater
import android.widget.ArrayAdapter
import androidx.lifecycle.LifecycleOwner
import com.example.flightinformationtest.ModelView.CalculatorViewModel
import com.example.flightinformationtest.R
import com.example.flightinformationtest.databinding.CalculatorLayoutBinding
import com.example.flightinformationtest.databinding.CalculatorcustomBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog

class CalculatorBottomSheet(
    private val context: Context,
    private val lifecycleOwner: LifecycleOwner,
    private val viewModel: CalculatorViewModel
) {
    private lateinit var binding: CalculatorcustomBinding
    private val bottomSheetDialog: BottomSheetDialog = BottomSheetDialog(context)

    private var onResultCallback: ((Double) -> Unit)? = null

    init {
        binding = CalculatorcustomBinding.inflate(LayoutInflater.from(context))
        bottomSheetDialog.setContentView(binding.root)
        bottomSheetDialog.behavior.state = BottomSheetBehavior.STATE_EXPANDED
        bottomSheetDialog.setCancelable(true)
        bottomSheetDialog.setCanceledOnTouchOutside(true)

        //setupGrid()
        setupButtons()
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

//    private fun setupGrid() {
//        val buttons = listOf(
//            "7", "8", "9", "←",
//            "4", "5", "6", "C",
//            "1", "2", "3", ".",
//            "0", "=", "", ""
//        )
//
//        val adapter = ArrayAdapter(context, R.layout.item_grid_keybutton, R.id.btnText, buttons)
//        binding.gridView.numColumns = 4
//        binding.gridView.adapter = adapter
//
//        binding.gridView.setOnItemClickListener { _, _, position, _ ->
//            val value = buttons[position]
//            if (value.isNotBlank()) {
//                viewModel.onButtonClick(value)
//            }
//        }
//    }


    private fun setupButtons() {
        val buttonMap = mapOf(
            binding.btn0 to "0",
            binding.btn1 to "1",
            binding.btn2 to "2",
            binding.btn3 to "3",
            binding.btn4 to "4",
            binding.btn5 to "5",
            binding.btn6 to "6",
            binding.btn7 to "7",
            binding.btn8 to "8",
            binding.btn9 to "9",
            binding.btnDecimal to ".",
            binding.btnDel to "←",
            binding.btnClear to "C",
            binding.btnResult to "="
        )

        for ((button, value) in buttonMap) {
            button.setOnClickListener {
                viewModel.onButtonClick(value)
            }
        }
    }


    private fun observeViewModel() {
        viewModel.inputText.observe(lifecycleOwner) { input ->
//            binding.inputText.text = input
            binding.Output.text=input
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