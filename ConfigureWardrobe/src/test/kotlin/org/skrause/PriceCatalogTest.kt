package org.skrause

import org.junit.jupiter.api.Test
import org.skrause.Centimeter.Companion.cm
import org.skrause.PriceCatalog.Companion.priceCatalog

class PriceCatalogTest {

    @Test
    fun `Price Catalog builder creates a price catalog`(){
        val wardrobe = Wardrobe(50.cm)
        val expectedPrice = 100.0
        val priceCatalog = priceCatalog {
            wardrobe costs expectedPrice
        }

        assert(priceCatalog.price(wardrobe) == expectedPrice)
    }

    @Test
    fun `Price catalog allows to add wardrobe only by width`(){
        val wardrobe = Wardrobe(50.cm)
        val expectedPrice = 125.0
        val priceCatalog = priceCatalog {
            50.cm costs expectedPrice
        }

        assert(priceCatalog.price(wardrobe) == expectedPrice)
    }
}