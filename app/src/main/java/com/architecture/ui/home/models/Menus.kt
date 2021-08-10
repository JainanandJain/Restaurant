package com.architecture.ui.home.models

data class Menus(
    val menus: List<Menu>
) {
    data class Menu(
        val categories: List<Category>,
        val restaurantId: Int
    ) {
        data class Category(
            val id: String,
            val restaurantId: Int,
            val menu_items: List<MenuItem>,
            val name: String
        ) {
            data class MenuItem(
                val description: String,
                val id: String,
                val images: List<Any>,
                val name: String,
                val price: String
            )
        }
    }
}