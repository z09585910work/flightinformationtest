package com.example.flightinformationtest.View.Adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.flightinformationtest.databinding.CurrencyitemLayoutBinding

class CurrencyAdapter( private val onItemClick: (View,Int) -> Unit// 傳出 itemView 和 position
) : RecyclerView.Adapter<CurrencyAdapter.CurrencyViewHolder>() {

    private var rates: List<Pair<String, Double>> = emptyList()
    private var selectIndex:Int=0

    class CurrencyViewHolder(private val binding: CurrencyitemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(rate: Pair<String, Double>,isSelect:Boolean) {
            binding.CurrenName.text = rate.first
            binding.ExchangRate.text = String.format("%.2f",rate.second)

            // 改變被選 item 的樣式
            if(isSelect){

                binding.CurrenName.setTextColor(Color.BLUE)
            }else{
                binding.CurrenName.setTextColor(Color.BLACK)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyViewHolder {
        var binding =
            CurrencyitemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CurrencyViewHolder(binding)
    }

    override fun getItemCount(): Int = rates.size

    override fun onBindViewHolder(holder: CurrencyViewHolder, @SuppressLint("RecyclerView") position: Int) {
        holder.bind(rates[position],position == selectIndex)

        holder.itemView.setOnClickListener {

            val previousSelected = selectIndex
            selectIndex = position

            //if(selectIndex != previousSelected){
                notifyItemChanged(previousSelected)
                notifyItemChanged(selectIndex)
            //}

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