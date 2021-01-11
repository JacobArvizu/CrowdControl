package com.jarvizu.crowdcontrol.data

import android.view.View
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.jarvizu.crowdcontrol.R
import com.jarvizu.crowdcontrol.databinding.ItemPlaceBinding
import com.xwray.groupie.viewbinding.BindableItem
import timber.log.Timber

class PlaceItem(private val placeName: String) : BindableItem<ItemPlaceBinding>() {

    override fun initializeViewBinding(view: View): ItemPlaceBinding {
        view.setOnClickListener {
            Timber.d("Clicked view")
            Navigation.findNavController(view).navigate(R.id.placeDetailFragment)
        }
        return ItemPlaceBinding.bind(view)
    }

    override fun getLayout() = R.layout.item_place

    override fun bind(viewBinding: ItemPlaceBinding, position: Int) {
        viewBinding.placeTitle.text = placeName
        Glide.with(viewBinding.root).load("url")
            .placeholder(R.drawable.ic_business_placeholder)
            .into(viewBinding.placeImageView)
    }

}