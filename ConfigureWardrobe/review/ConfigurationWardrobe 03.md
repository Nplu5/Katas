# Aufräumen und Kosten berechnen

Heute stehen zwei, bzw. eigentlich drei stories an: 

- Refactoring der `when`-clause und des Codes insgesamt
- Berechnung der Kosten einer Lösung
- Identifikation der kostengünstigsten Lösung

## Refactoring oder Ordnung hinterlassen

Ich möchte mit dem Refactoring starten und den Code damit etwas aufgeräumter 
hinterlassen für die nächsten Aufgaben. Das Problem ist, dass die 
`when`-clause erschöpfend sein muss, aber nicht erkannt wird, dass die 
Bedingungen erschöpfend sind. Hier die entsprechende `when`-clause:

```kotlin
return when  {
    remainingLength == 0.cm -> listOf(Solution(listOf(currentOption)))
    remainingLength < 0.cm -> emptyList()
    remainingLength > 0.cm -> {
        calculateAllPossibleSolutions(options, remainingLength)
            .map { solution -> solution.copy(wardrobes = listOf(currentOption) + solution.wardrobes) }
    }
    else -> TODO("Not yet implemented, but should not happen")
}
```

Eine Möglichkeit ist es eine entsprechende `sealed class` mit entsprechenden 
drei `data class`es zu erstellen, welche die drei Bedingungen abbilden. 
Somit wäre die `when`-clause erschöpfend und der Code würde. Trotzdem 
scheint mir eine extra Klasse für diese drei Bedingungen etwas viel zu sein. 
Es fällt mir aber keine andere Lösung diesbezüglich ein. Deshalb werde ich 
das mal implementieren und dann schauen, wie es mir gefällt.

Beim Implementieren fällt mir auf, dass damit das Problem nur in die Klasse, 
bzw. deren Fabrik-Methode verschoben wird. Das ist nicht wirklich eine 
Verbesserung. Somit enden wir wieder bei der `when`-clause und dieser Lösung:

```kotlin
return when {
    remainingLength == 0.cm -> listOf(Solution(listOf(currentOption)))
    remainingLength < 0.cm -> emptyList()
    remainingLength > 0.cm -> {
        calculateAllPossibleSolutions(options, remainingLength)
            .map { solution -> solution.copy(wardrobes = listOf(currentOption) + solution.wardrobes) }
    }
    else -> error {"remainingLength should never be reaching the else branch"}
}
```

## Kostenberechnung

Eine Lösung sollte ihre Kosten berechnen können. Wie kann jedoch die 
Zuordnung der Wardrobe zu den Kosten erfolgen? Eine Lösung benötigt für die 
Berechnung des Preises einen Preiskatalog, welcher die Breite eines 
Schrankmoduls mit dem Preis verknüpft. Somit kann die Lösung den 
Preiskatalog verwenden, um die kostengünstigste Lösung zu bestimmen. Um den 
Preiskatalog aufzubauen würde ich gerne eine kleine DSL schreiben, die es 
einfacher macht einen Preiskatalog zu erstellen. Das werde ich wohl morgen 
machen müssen, da ich heute bei der Bearbeitung zu sehr abgelenkt war.

## Ergänzung

Jetzt hat mich die Lust auf eine kleine DSL doch gepackt und ich habe sie 
implementiert. Mit dem Test

```kotlin
@Test
fun `Price Catalog builder creates a price catalog`(){
    val wardrobe = Wardrobe(50.cm)
    val expectedPrice = 100.0
    val priceCatalog = priceCatalog {
        wardrobe costs expectedPrice
    }

    assert(priceCatalog.price(wardrobe) == expectedPrice)
}
```

Ergab sich folgende Implementierung

```kotlin
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
```

Jetzt reicht es mir aber für heute. Morgen geht es weiter mit eventuellen 
Verbesserungen für die DSL, sowie der Berechnung der Kosten für eine Lösung.

## Weitere Ideen

Voreilige Optimierungen:

- Reduzierung der Anzahl an Objekten die erstellt werden müssen bei der 
  Bestimmung der Lösungen