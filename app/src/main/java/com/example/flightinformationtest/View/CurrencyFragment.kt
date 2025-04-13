package com.example.flightinformationtest.View

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.flightinformationtest.ModelView.CalculatorViewModel
import com.example.flightinformationtest.ModelView.CurrencyViewModel
import com.example.flightinformationtest.View.Adapter.CurrencyAdapter
import com.example.flightinformationtest.View.Ui.CalculatorPopup
import com.example.flightinformationtest.databinding.FragmentCurrencyBinding

class CurrencyFragment : Fragment() {

    private var _binding:FragmentCurrencyBinding?=null
    private val binding get()=_binding!!
    private val viewModel:CurrencyViewModel by viewModels()
    private lateinit var adapter: CurrencyAdapter

    private var calculatorPopup: CalculatorPopup? = null

    // 將 CalculatorViewModel 宣告在 Fragment 中，讓 Popup 能共用
    private val calculatorViewModel: CalculatorViewModel by lazy {
        CalculatorViewModel()
    }

    private var selectedPosition: Int = -1 // 預設 -1 表示尚未選擇


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_currency, container, false)
        _binding = FragmentCurrencyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //viewModel.loadRates(API_KEY)
//        adapter= CurrencyAdapter(emptyList())
        //adapter= CurrencyAdapter()

        adapter=CurrencyAdapter{ itemView ,position ->
            selectedPosition=position
            viewModel.triggerCalculator(itemView)
        }

        binding.CurrentStateList.layoutManager=LinearLayoutManager(requireContext())
        binding.CurrentStateList.adapter=adapter

        viewModel.rates.observe(viewLifecycleOwner){
            rates->
            if (rates.isNotEmpty()){
                adapter.updateData(rates.toList())
                Log.d("CurrencyFragment", "loadRate: "+rates.toList())
            }else{
                Toast.makeText(requireContext(),"數據獲取失敗",Toast.LENGTH_SHORT)
            }
        }

        // 監聽是否要彈出計算機
        viewModel.showCalculatorEvent.observe(viewLifecycleOwner) { anchorView ->
            if (anchorView != null) {
                showCalculator(anchorView)
            } else {
                calculatorPopup?.dismiss()
            }
        }

        // 點外部關閉計算機（可點 RecyclerView 背景等）
        binding.root.setOnTouchListener { _, _ ->
            viewModel.clearCalculatorEvent()
            calculatorViewModel.clearResultEvent()
            false
        }

        // 監聽 Calculator 結果回傳
        calculatorViewModel.resultEvent.observe(viewLifecycleOwner) { result ->
            result?.let {
                //viewModel.setInputAmount(it) // 將輸入的數值交由 ViewModel 處理

                if(selectedPosition  !=-1){
                    viewModel.loadCURRRates(selectedPosition,it)
                }

                viewModel.clearCalculatorEvent()
                calculatorViewModel.clearResultEvent()

                Log.d("CurrencyFragment_2", " Calculator 結果回傳: $result")
            }
        }

        // 監聽計算後轉換的匯率
        viewModel.convertedRates.observe(viewLifecycleOwner) { converted ->
            adapter.updateData(converted.toList())
            Log.d("CurrencyFragment_2", "convertedRates updated: $converted")
        }

    }

    private fun showCalculator(anchorView: View) {
        calculatorPopup?.dismiss()

        calculatorPopup = CalculatorPopup(
            context = requireContext(),
            lifecycleOwner = viewLifecycleOwner,
            viewModel = calculatorViewModel // 使用同一個 viewModel
        )

        calculatorPopup?.show(anchorView)
        calculatorViewModel.clearResult()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onResume() {
        super.onResume()
        //viewModel.startAutoUpdateC()
        viewModel.loadRate()
    }

    override fun onPause() {
        super.onPause()
        viewModel.stopFetchingC()

    }
}