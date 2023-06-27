package org.skrause

class PriceCatalog(private val wardrobePrices: Map<Centimeter, Double>) {

    fun price(wardrobe: Wardrobe): Double {
        return wardrobePrices[wardrobe.width] ?: error("No price found for wardrobe with width ${wardrobe.width}")
    }
    class PriceCatalogBuilder {
        private val wardrobePrices = mutableMapOf<Centimeter, Double>()
        infix fun Wardrobe.costs(price: Double) {
            wardrobePrices[width] = price
        }

        fun build(): PriceCatalog {
            return PriceCatalog(wardrobePrices)
        }
    }
    companion object {
        fun priceCatalog(block: PriceCatalogBuilder.() -> Unit): PriceCatalog {
            return PriceCatalogBuilder()
                .apply(block)
                .build()
        }
    }
}
