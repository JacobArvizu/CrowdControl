package com.jarvizu.crowdcontrol.ui.place

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.jarvizu.crowdcontrol.databinding.FragmentPlaceDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PlaceDetailFragment : Fragment() {

    val viewModel: PlaceViewModel by viewModels()
    private lateinit var binding: FragmentPlaceDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPlaceDetailBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        with(binding) {

        }
    }
}
