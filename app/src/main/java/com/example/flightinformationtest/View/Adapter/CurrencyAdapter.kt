package com.example.flightinformationtest.View.Adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.flightinformationtest.databinding.CurrencyitemLayoutBinding

class CurrencyAdapter( private val onItemClick: (View,Int) -> Unit// 傳出 itemView 和 position
) : RecyclerView.Adapter<CurrencyAdapter.CurrencyViewHolder>() {

    private var rates: List<Pair<String, Double>> = emptyList()

    class CurrencyViewHolder(private val binding: CurrencyitemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(rate: Pair<String, Double>) {
            binding.CurrenName.text = rate.first
            binding.ExchangRate.text = String.format("%.2f",rate.second)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyViewHolder {
        var binding =
            CurrencyitemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CurrencyViewHolder(binding)
    }

    override fun getItemCount(): Int = rates.size

    override fun onBindViewHolder(holder: CurrencyViewHolder, position: Int) {
        holder.bind(rates[position])

        holder.itemView.setOnClickListener {
            onItemClick(it,position)
        }
    }

    fun updateData(newRates: List<Pair<String, Double>>) {
        rates = newRates
        notifyDataSetChanged()
        Log.d("CurrencyAdapter","updateData: "+rates)
    }

    fun getItem(position: Int):Pair<String,Double>{
        return rates[position]
    }


}