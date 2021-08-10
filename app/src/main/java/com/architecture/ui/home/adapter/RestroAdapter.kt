package com.architecture.ui.home.adapter

import com.architecture.base.BaseRecyclerAdapter
import com.architecture.databinding.RawRestroBinding
import com.architecture.ui.home.models.RestaurantWithMenu
import com.architecture.ui.home.models.Restaurants

class SearchAdapter(override val layoutId: Int) : BaseRecyclerAdapter<RawRestroBinding, RestaurantWithMenu>() {
    override fun bind(holder: ViewHolder, item: RestaurantWithMenu, position: Int) {
        holder.binding.model = item
    }
}
