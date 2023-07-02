# Weitermachen nach einer längeren Pause

Nach ein paar Tagen Pause habe ich nun wieder Zeit gefunden mich mit dem 
Kata auseinanderzusetzen. Ich bin froh, dass ich die Notizen habe, dass 
macht es einfacher wieder in den Flow zu kommen. Heute stehen folgende 
Stories zur Verfügung: 

- Verbesserung der DSL zur Definition eines Preiskatalogs
- Berechnung der Gesamtlösungskosten
- Identifikation der kostengünstigsten Lösung

Der dritte Punkt hängt klar vom zweiten Punkt ab, weshalb dieser zuerst 
umgesetzt werden sollte. Aber zunächst möchte ich mit der Verbesserung der 
DSL starten. 

## Verbesserung

Aktuell muss eine wardrobe erstellt werden, um diese dann mit einem Preis zu 
verknüpfen. Aktuell würde es aber auch schon ausreichend sein, die Breite 
mit dem Preis zu verknüpfen. Somit könnte die DSL wie folgt aussehen: 

```kotlin
priceCatalog {
    50.cm costs 100.00
    100.cm costs 200.00
}
```

## Berechnung der Lösungskosten

Die Berechnung der Lösungskosten ist relativ einfach. Die Lösung bekommt 
einen priceCatalog bei der Berechnung übergeben und iteriert dann über die 
Schrankmodule und summiert die Kosten. Ganz nach TDD schreiben wir zunächst 
den Test:

```kotlin
@Test
fun `solution should calculate the price of the wardrobes`(){
    val wardrobe = Wardrobe(50.cm)
    val priceCatalog = priceCatalog {
        wardrobe costs 100.0
    }
    val solution = Solution(listOf(wardrobe))

    assert(solution.cost(priceCatalog) == 100.0)
}
```

Was dann die folgende Implementierung nach sich zieht:

```kotlin
fun cost(priceCatalog: PriceCatalog): Any {
    return wardrobes.sumOf { priceCatalog.price(it) }
}
```

## Abschluss des Katas

Zum Abschluss des Katas werden noch 2 Tests definiert, welche einerseits 
alle möglichen Lösungen definieren und andererseits die kostengünstigste 
Lösung identifizieren. Der Test sieht wie folgt aus: 

```kotlin
 @Test
fun `Kata solution`(){
    val options = setOf(
        Wardrobe(50.cm),
        Wardrobe(75.cm),
        Wardrobe(100.cm),
        Wardrobe(120.cm),
    )
    val priceCatalog = priceCatalog {
        Wardrobe(50.cm) costs 59.0
        Wardrobe(75.cm) costs 62.0
        Wardrobe(100.cm) costs 90.0
        Wardrobe(120.cm) costs 111.0
    }
    val desiredLength = 250.cm
    val solutions = calculateAllPossibleSolutions(options, desiredLength)
    val cheapestSolution = solutions.map { it to it.cost(priceCatalog) }
        .minBy { it.second }
    println("Cheapest solution")
    println(cheapestSolution.first)
    println("For: ${cheapestSolution.second}€")
}
```

## Kleines Problem

Leider ist zeigt die Ausgabe, das unterschiedliche Permutationen mehrmals 
hinzugefügt werden. Das wird beim nächsten Mal bearbeiten als Einschränkung 
verwendet.

## Fazit

Outside-Int Testing 
Copilot influence on TDD
Gedanken nebenher dokumentieren ist sehr hilfreich um Fehler frühzeitig zu 
erkennen
