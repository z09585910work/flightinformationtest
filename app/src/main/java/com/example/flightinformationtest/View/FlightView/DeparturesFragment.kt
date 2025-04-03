package com.example.flightinformationtest.View.FlightView

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.flightinformationtest.ModelView.FlightViewModel
import com.example.flightinformationtest.R
import com.example.flightinformationtest.View.Adapter.FlightRecyclerViewAdapter
import com.example.flightinformationtest.databinding.FragmentDeparturesBinding

class DeparturesFragment : Fragment() {

    private var _binding:FragmentDeparturesBinding?=null
    private val binding get()=_binding!!
    private lateinit var viewModel:FlightViewModel



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel=ViewModelProvider(requireParentFragment()).get(FlightViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_departures, container, false)
        _binding=FragmentDeparturesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter=FlightRecyclerViewAdapter()
        binding.RecyclerViewD.layoutManager=LinearLayoutManager(requireContext())
        binding.RecyclerViewD.adapter=adapter

        viewModel.flights.observe(viewLifecycleOwner, Observer {
            flights->
            flights?.let {
                adapter.updateData(it)
            }?:run{
                Toast.makeText(requireContext(), "載入失敗", Toast.LENGTH_SHORT).show()
            }
        })

        viewModel.loadFlights()
    }
}