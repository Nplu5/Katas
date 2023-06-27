package org.skrause

import org.junit.jupiter.api.Test
import org.skrause.Centimeter.Companion.cm

class SolutionTest {

    @Test
    fun `solution should calculate the price of the wardrobes`(){
        val wardrobe = Wardrobe(50.cm)
        val priceCatalog = PriceCatalog(mapOf(wardrobe.width to 100.0))
        val solution = Solution(listOf(wardrobe))

        assert(solution.price == 100.0)
    }

}