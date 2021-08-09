package com.sidarta.myalarme

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.sidarta.myalarme.adapter.AlarmeAdapter
import com.sidarta.myalarme.dao.RoomManager
import com.sidarta.myalarme.data.Alarme
import com.sidarta.myalarme.databinding.FragmentHomeBinding

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    private val alarmeAdapter : AlarmeAdapter by lazy {
        AlarmeAdapter()
    }

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        setupRecyclerview()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val db = RoomManager.getInstance(view.context)
        val alarmeList: List<Alarme> = db?.getAlarme()?.allAlarmes()!!
        if (alarmeList.isEmpty()){
            binding.semListaText.isVisible = true
        }else{
            binding.semListaText.isVisible = false
            alarmeAdapter.setData(alarmeList)
        }

        //setupRecyclerview(alarmeList)

        binding.btnNewAlarme.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }
    }


    private fun setupRecyclerview() {
        binding.recyclerViewAlarme.adapter = alarmeAdapter
        val layoutManager = LinearLayoutManager(context)
        binding.recyclerViewAlarme.layoutManager = layoutManager
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}