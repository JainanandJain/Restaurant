package com.architecture.ui.home.models

data class Restaurants(
    val restaurants: List<Restaurant>
) {
    data class Restaurant(
        val address: String = "",
        val cuisine_type: String = "",
        val id: Int = 0,
        val name: String = "",
        val menu_name: String = "",
        val description: String = "",
        val price: String = "",
        val neighborhood: String = ""
    )

}


data class RestaurantWithMenu(
    val menu_items: List<Menus.Menu.Category>,
    val menu_name: String = "",

    val address: String = "",
    val cuisine_type: String = "",
    val id: Int = 0,
    val name: String = "",
    val description: String = "",
    val price: String = "",
    val neighborhood: String = ""
)