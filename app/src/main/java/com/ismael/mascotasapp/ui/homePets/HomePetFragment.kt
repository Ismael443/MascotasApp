package com.ismael.mascotasapp.ui.homePets

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ismael.mascotasapp.PetsAdapter
import com.ismael.mascotasapp.R

class HomePetFragment : Fragment() {

    private lateinit var searchView: SearchView
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: PetsAdapter


    private val viewModel by viewModels<HomePetVM>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_view_pets, container, false)


        searchView = rootView.findViewById(R.id.search_bar)
        recyclerView = rootView.findViewById(R.id.recycler_view)
        adapter = PetsAdapter(mutableListOf())
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(activity)

        viewModel.getPetList().observe(viewLifecycleOwner, Observer { petList ->
            adapter.updatePetList(petList)
            adapter.notifyDataSetChanged()
        })

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.loadPetList()
    }

}

