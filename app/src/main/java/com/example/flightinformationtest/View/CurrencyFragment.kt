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
import com.example.flightinformationtest.ModelView.CurrencyViewModel
import com.example.flightinformationtest.View.Adapter.CurrencyAdapter
import com.example.flightinformationtest.databinding.FragmentCurrencyBinding

class CurrencyFragment : Fragment() {

    private var _binding:FragmentCurrencyBinding?=null
    private val binding get()=_binding!!
    private val viewModel:CurrencyViewModel by viewModels()
    private lateinit var adapter: CurrencyAdapter
//    private val API_KEY="fca_live_1dJaKxoSAJGMkXq9YgvFP5VLJc6bkJR0QeidcoM2"

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
        adapter= CurrencyAdapter()
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

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onResume() {
        super.onResume()
        viewModel.startAutoUpdateC()
    }

    override fun onPause() {
        super.onPause()
        viewModel.stopFetchingC()
    }
}