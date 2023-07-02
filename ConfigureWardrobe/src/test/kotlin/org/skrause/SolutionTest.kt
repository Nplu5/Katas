package org.skrause

import org.junit.jupiter.api.Test
import org.skrause.Centimeter.Companion.cm
import org.skrause.PriceCatalog.Companion.priceCatalog

class SolutionTest {

    @Test
    fun `solution should calculate the price of the wardrobes`(){
        val wardrobe = Wardrobe(50.cm)
        val priceCatalog = priceCatalog {
            wardrobe costs 100.0
        }
        val solution = Solution(listOf(wardrobe))

        assert(solution.cost(priceCatalog) == 100.0)
    }
}