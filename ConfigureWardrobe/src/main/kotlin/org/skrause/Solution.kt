package org.skrause

data class Solution(val wardrobes: List<Wardrobe>) {
    fun cost(priceCatalog: PriceCatalog): Double {
        return wardrobes.sumOf { priceCatalog.price(it) }
    }
}