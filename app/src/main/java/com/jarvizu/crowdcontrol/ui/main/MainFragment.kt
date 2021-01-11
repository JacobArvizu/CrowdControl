package com.jarvizu.crowdcontrol.ui.main

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.common.api.ApiException
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.model.PlaceLikelihood
import com.google.android.libraries.places.api.net.FindCurrentPlaceRequest
import com.google.android.libraries.places.api.net.PlacesClient
import com.jarvizu.crowdcontrol.R
import com.jarvizu.crowdcontrol.data.PlaceItem
import com.jarvizu.crowdcontrol.databinding.FragmentMainBinding
import com.tapadoo.alerter.Alerter
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import timber.log.Timber
import javax.inject.Inject


@ExperimentalCoroutinesApi
@AndroidEntryPoint
class MainFragment : Fragment(R.layout.fragment_main) {
    private lateinit var binding: FragmentMainBinding

    @Inject
    lateinit var placesClient: PlacesClient

    val viewModel: MainViewModel by viewModels()

    // Use fields to define the data types to return.
    private val placeFields: List<Place.Field> = listOf(Place.Field.NAME)
    private var placeNames: ArrayList<String> = ArrayList()

    @SuppressLint("MissingPermission")

    // Use the builder to create a FindCurrentPlaceRequest.

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        Timber.d("Main Fragment")
        return binding.root
    }

    @SuppressLint("BinaryOperationInTimber", "MissingPermission")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val request: FindCurrentPlaceRequest = FindCurrentPlaceRequest.newInstance(placeFields)

        val placeResponse = placesClient.findCurrentPlace(request)

        placeResponse.addOnCompleteListener { task ->
            if (task.isSuccessful) {

                val response = task.result
                for (placeLikelihood: PlaceLikelihood in response?.placeLikelihoods
                    ?: emptyList()) {
                    try {
                        placeNames.add(placeLikelihood.place.name!!)
                    } catch (e: Exception) {
                        Timber.d(e)
                    }
                }
                initRecyclerView(placeNames)
            } else {
                val exception = task.exception
                if (exception is ApiException) {
                    Timber.d(exception)
                }
            }

            Alerter.create(requireActivity())
                .setTitle("Nearby venues loaded")
                .setText("Select current venue")
                .setBackgroundColorRes(R.color.dracula_green)
                .setLayoutGravity(Gravity.BOTTOM)
                .show()
        }
    }

    private fun initRecyclerView(items: ArrayList<String>) {

        val groupAdapter = GroupAdapter<GroupieViewHolder>()
        for (item in items.withIndex()) {
            groupAdapter.add(PlaceItem(item.value))
        }
        //set up the layout manager and set the adapter
        binding.placeRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireActivity())
            adapter = groupAdapter
        }
    }
}
