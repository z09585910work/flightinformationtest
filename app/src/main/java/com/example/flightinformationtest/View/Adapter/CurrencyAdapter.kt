package com.example.flightinformationtest.View.Adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.flightinformationtest.View.ui.CalculatorPopup
import com.example.flightinformationtest.databinding.CurrencyitemLayoutBinding

class CurrencyAdapter( private val onItemClicked: (View, Pair<String, Double>) -> Unit) :
    RecyclerView.Adapter<CurrencyAdapter.CurrencyViewHolder>() {

    private var rates: List<Pair<String, Double>> = emptyList()

    class CurrencyViewHolder(private val binding: CurrencyitemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(rate: Pair<String, Double>,clickListener:(View)->Unit) {
            binding.CurrenName.text = rate.first
            binding.ExchangRate.text = String.format("%.2f",rate.second)

            binding.root.setOnClickListener{clickListener(binding.root)}

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyViewHolder {
        var binding =
            CurrencyitemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CurrencyViewHolder(binding)
    }

    override fun getItemCount(): Int = rates.size

    override fun onBindViewHolder(holder: CurrencyViewHolder, position: Int) {
        val  contex=holder.itemView.context
        holder.bind(rates[position]){
            onItemClicked(it,rates[position])
        }
    }

    fun updateData(newRates: List<Pair<String, Double>>) {
        rates = newRates
        notifyDataSetChanged()
        Log.d("CurrencyAdapter","updateData: "+rates)
    }


}