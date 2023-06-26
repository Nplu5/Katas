package org.skrause

import org.junit.jupiter.api.Test
import org.skrause.Centimeter.Companion.cm

class ConfigureWardrobeTest {


    @Test
    fun `calculate all possible Wardrobe options`(){
        val options = setOf(Wardrobe(50.cm), Wardrobe(75.cm))
        val desiredLength = 150
        val solutions = calculateAllPossibleSolutions(options, desiredLength.cm)

        assert(solutions.size == 2)
    }

    @Test
    fun `calculate solution for a given option`(){

    }

    @Test
    fun `calculate solution for a wardrobe that matches the given desiredLength`(){
        val option = Wardrobe(50.cm)
        val options = setOf(Wardrobe(50.cm), Wardrobe(75.cm))
        val desiredLength = 50
        val solutions = getSolutionForOption(option, options, desiredLength.cm)

        assert(solutions.size == 1)
        assert(solutions[0].wardrobes.size == 1)
        assert(solutions[0].wardrobes[0] == option)
    }

    @Test
    fun `calculate solution for a wardrobe that is bigger than the given desiredLength`(){
        val option = Wardrobe(75.cm)
        val options = setOf(Wardrobe(50.cm), Wardrobe(75.cm))
        val desiredLength = 50
        val solutions = getSolutionForOption(option, options, desiredLength.cm)

        assert(solutions.isEmpty())
    }

    @Test
    fun `calculate solution for a length that needs two wardrobes`(){
        val option = Wardrobe(50.cm)
        val options = setOf(Wardrobe(50.cm), Wardrobe(75.cm))
        val desiredLength = 100
        val solutions = getSolutionForOption(option, options, desiredLength.cm)

        assert(solutions.size == 1)
        assert(solutions[0].wardrobes.size == 2)
        assert(solutions[0].wardrobes[0] == option)
        assert(solutions[0].wardrobes[1] == option)
    }

    @Test
    fun `calculate empty solution if no solution is possible`(){
        val option = Wardrobe(50.cm)
        val options = setOf(Wardrobe(50.cm), Wardrobe(75.cm))
        val desiredLength = 110
        val solutions = getSolutionForOption(option, options, desiredLength.cm)

        assert(solutions.isEmpty())
    }
    // Test for calculating a solution when length cannot be solved
}