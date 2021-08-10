package com.architecture.ui.home

import androidx.lifecycle.MutableLiveData
import com.architecture.R
import com.architecture.base.*
import com.architecture.ui.home.models.Menus
import com.architecture.ui.home.models.RestaurantWithMenu
import com.architecture.ui.home.models.Restaurants

class HomeViewModel(controller: AsyncViewController) : BaseViewModel(controller) {

    private lateinit var modelRestaurants: Restaurants
    private lateinit var modelMenus: Menus
    private lateinit var result: ArrayList<RestaurantWithMenu>

    init {
        loadJsonFiles()
    }

    fun loadJsonFiles() {
        modelRestaurants = R.string.restaurant_json_file.get().loadJSONFromAssets().toModel(Restaurants::class.java) as Restaurants
        modelMenus = R.string.menus_json_file.get().loadJSONFromAssets().toModel(Menus::class.java) as Menus

        loadMergedResults()
    }

    private fun loadMergedResults() {
        val restroById: Map<Int, Restaurants.Restaurant> = modelRestaurants.restaurants.associateBy { it.id }

        result = modelMenus.menus.filter { restroById[it.restaurantId] != null }.map { cat ->
            restroById[cat.restaurantId]?.let { restro ->
                RestaurantWithMenu(name = restro.name,
                                   menu_items = cat.categories,
                                   cuisine_type = restro.cuisine_type,
                                   description = restro.description,
                                   id = restro.id,
                                   neighborhood = restro.neighborhood)
            }
        } as ArrayList<RestaurantWithMenu>
    }

    fun getFilteredRestaurantsList(str: String): MutableLiveData<ArrayList<RestaurantWithMenu>> {
        var combinedList = MutableLiveData<ArrayList<RestaurantWithMenu>>()

        if (str.isEmptyy()) combinedList.value = getAllItemsList()
        else combinedList.value = getFilteredItemsList(str) as ArrayList<RestaurantWithMenu>

        return combinedList
    }

    private fun getFilteredItemsList(str: String): List<RestaurantWithMenu> {
        var keyword = str.trim().toLowerCase()
        return result.filter {
            it.cuisine_type.trim().toLowerCase().contains(keyword) ||
                    it.name.trim().toLowerCase().contains(keyword) ||
                    it.neighborhood.trim().toLowerCase().contains(keyword) ||
                    isMenuMatched(it, keyword)
        }
    }


    private fun isMenuMatched(a: RestaurantWithMenu, keyword: String): Boolean {
        return a.menu_items.any { b ->
            b.menu_items.any { c ->
                c.name.trim().toLowerCase().contains(keyword) || c.description.trim().toLowerCase().contains(keyword)
            }
        }
    }

    private fun getAllItemsList(): java.util.ArrayList<RestaurantWithMenu>? {
        return result
    }

    fun getAllItemsLiveData(): MutableLiveData<java.util.ArrayList<RestaurantWithMenu>>? {
        var combinedList = MutableLiveData<ArrayList<RestaurantWithMenu>>()
        combinedList.value = result
        return combinedList
    }


}