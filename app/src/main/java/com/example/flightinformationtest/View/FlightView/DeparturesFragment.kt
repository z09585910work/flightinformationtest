package com.example.flightinformationtest.View.FlightView

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.flightinformationtest.ModelView.FlightViewModel
import com.example.flightinformationtest.R
import com.example.flightinformationtest.View.Adapter.FlightRecyclerViewAdapter
import com.example.flightinformationtest.databinding.FragmentDeparturesBinding

//class DeparturesFragment : Fragment() {
//
//    private var _binding:FragmentDeparturesBinding?=null
//    private val binding get()=_binding!!
//    private lateinit var viewModel:FlightViewModel
//   // private val viewModel:FlightViewModel by viewModels()
//
//
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        viewModel=ViewModelProvider(requireParentFragment()).get(FlightViewModel::class.java)
//    }
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        // Inflate the layout for this fragment
////        return inflater.inflate(R.layout.fragment_departures, container, false)
//        _binding=FragmentDeparturesBinding.inflate(inflater, container, false)
//        return binding.root
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        val adapter=FlightRecyclerViewAdapter()
//        binding.RecyclerViewD.layoutManager=LinearLayoutManager(requireContext())
//        binding.RecyclerViewD.adapter=adapter
//
//        viewModel.flights.observe(viewLifecycleOwner, Observer {
//            flights->
//            flights?.let {
//                //viewModel.startAutoUpdate()
//                adapter.updateData(it)
//                Log.d("DeparturesFragment"," adapter.updateData(it): "+it)
//            }?:run{
//
//                Toast.makeText(requireContext(), "載入失敗", Toast.LENGTH_SHORT).show()
//            }
//        })
//
//        // 🔥 監聽 flights 變化，確保 RecyclerView 更新
////        viewModel.flights.observe(viewLifecycleOwner) { it->
////
////            if (it != null) {
////
////                adapter.cha
////                adapter.updateData(it)  // 🚀 確保 UI 會刷新
////            } else {
////                Toast.makeText(requireContext(), "載入失敗", Toast.LENGTH_SHORT).show()
////            }
////
////            Log.d("DeparturesFragment"," adapter.updateData(it): "+ it)
////        }
//
//
//
//        viewModel.loadFlights()
//    }
//
//    override fun onResume() {
//        super.onResume()
//
//        viewModel.startAutoUpdate()
//    }
//
//    override fun onPause() {
//        super.onPause()
//
//        viewModel.stopFetching()
//    }
//}


class DeparturesFragment : Fragment() {

    private var _binding: FragmentDeparturesBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: FlightViewModel
    private lateinit var adapter: FlightRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(requireParentFragment()).get(FlightViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDeparturesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = FlightRecyclerViewAdapter()
        binding.RecyclerViewD.layoutManager = LinearLayoutManager(requireContext())
        binding.RecyclerViewD.adapter = adapter

        // 🔥 監聽 flights 變化，確保 RecyclerView 更新
        viewModel.flights.observe(viewLifecycleOwner) { flights ->
            if (flights != null) {
                //adapter.updateData(flights)  // 確保 UI 會刷新
                flights?.let {adapter.updateData(it) } ?: Log.d("DeparturesFragment", "資料為空")
                Log.d("DeparturesFragment","adapter.updateData(flights) flights: "+flights)
            } else {
                Toast.makeText(requireContext(), "載入失敗", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.startAutoUpdate()  // 進入畫面時啟動自動更新
    }

    override fun onPause() {
        super.onPause()
        viewModel.stopFetching()  // 🔥 離開畫面時停止更新，避免內存洩漏
    }
}