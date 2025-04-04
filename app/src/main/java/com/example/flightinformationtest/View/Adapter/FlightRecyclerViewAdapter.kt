package com.example.flightinformationtest.View.Adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.flightinformationtest.Data.FlightInfo
import com.example.flightinformationtest.R
import com.example.flightinformationtest.databinding.ItemFlightBinding

class FlightRecyclerViewAdapter :
    RecyclerView.Adapter<FlightRecyclerViewAdapter.FlightViewHolder>() {

    //private var flightList: List<FlightInfo> = emptyList()
    private var flightList: List<FlightInfo> = emptyList()
    class FlightViewHolder(private val binding: ItemFlightBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(datas: FlightInfo) {

            binding.AirLineNum.text = "航機班號: ${datas.airLineNum}"
            binding.ExpectTime.text = datas.expectTime
            binding.RealTime.text = datas.realTime
            binding.BoardingGate.text = "航廈/登機門: ${datas.airBoardingGate}"
            binding.upAirportName.text = datas.upAirportName
            binding.AirFlyStatus.text = "航機狀態: ${datas.airFlyStatus}"

            Glide.with(itemView.context)
                .load(datas.airLineLogo ?: R.drawable.ic_launcher_foreground)
                .into(binding.AirLogo)

        }
    }

    fun updateData(data: List<FlightInfo>) {

        flightList = data
        Log.d("FlightRecyclerViewAdapter","data"+data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FlightViewHolder {
        var inflater = LayoutInflater.from(parent.context)
        var binding = ItemFlightBinding.inflate(inflater, parent, false)

        return FlightViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FlightViewHolder, position: Int) {
        holder.bind(flightList[position])
    }

    override fun getItemCount(): Int = flightList.size


}