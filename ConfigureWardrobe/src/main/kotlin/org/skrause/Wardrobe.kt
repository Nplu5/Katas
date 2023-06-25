package org.skrause

@JvmInline
value class Centimeter(private val value: Int) : Comparable<Centimeter> {
    override fun compareTo(other: Centimeter): Int {
        return value.compareTo(other.value)
    }

    companion object{
        val Int.cm get() = Centimeter(this)
    }

    operator fun minus(other: Centimeter): Centimeter {
        return (value - other.value).cm
    }
}

data class Wardrobe(val width: Centimeter)
