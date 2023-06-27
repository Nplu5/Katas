package org.skrause

import org.skrause.Centimeter.Companion.cm

fun getSolutionForOption(
    currentOption: Wardrobe,
    options: Set<Wardrobe>,
    desiredLength: Centimeter
): List<Solution> {
    val remainingLength = desiredLength - currentOption.width
    return when {
        remainingLength == 0.cm -> listOf(Solution(listOf(currentOption)))
        remainingLength < 0.cm -> emptyList()
        remainingLength > 0.cm -> {
            calculateAllPossibleSolutions(options, remainingLength)
                .map { solution -> solution.copy(wardrobes = listOf(currentOption) + solution.wardrobes) }
        }
        else -> error {"remainingLength should never be reaching the else branch"}
    }
}

fun calculateAllPossibleSolutions(options: Set<Wardrobe>, desiredLength: Centimeter): List<Solution> {
    return options.sortedBy { it.width }
        .flatMap { option -> getSolutionForOption(option, options, desiredLength) }
}

