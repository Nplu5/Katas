# Endlich anfangen

Ich habe dieses Kata gestartet, um meinen Spaß am Programmieren nicht zu 
verlieren und um besser zu werden. Deshalb möchte ich euch bei meiner 
Bearbeitung dieses Katas teilnehmen lassen. 

## Auswahl

Als ich mich nach möglichen Katas umgeschaut habe, bin ich über das 
[`Configure wardrobe Kata`](https://kata-log.rocks/configure-wardrobe-kata) 
gestolpert und habe den Tag `Outside-In` gesehen, was direkt meine neugier 
befeuert hat. Nach einer kurzen initialen Recherche habe ich gemerkt, dass 
ich vor kurzem selbst angefangen habe eine ähnliche Methodik anzuwenden, wo 
ich mit dem Test für ein Feature angefangen habe und dann die 
Implementierung mithilfe von TDD entwickelt habe. Mithilfe von 
`Outside-In-TDD` lässt sich die Implementierung noch besser leiten und 
steuern, weshalb ich das in diesem Kata probieren möchte. 

Inspiriert durch die Beiträge von **Ron Jeffries** zu [Kotlin]
(https://ronjeffries.com/categories/kotlin/) oder [Python]
(https://ronjeffries.com/categories/python/) werde ich versuchen meine 
Gedanken zur Lösung parallel zu dokumentieren.

## Der Outside Test

Startend mit Outside Test wird als Erstes die API der Lösung definiert. Am 
einfachsten lässt sic mit dem Input der Lösung starten. Basierend auf einer 
Liste an verschiedenen Schrankmodulvarianten und einer Zielbreite soll die 
Liste aller möglichen Schrankmodul-Kombinationen bestimmt werden, welche genau 
die Zielbreite ergeben. 

Daraus ergibt sich folgender Test:

```kotlin
@Test
fun `calculate all possible Wardrobe options`(){
    val sut = ConfigurationCalculator()
    val options = listOf(Wardrobe(50.centimeter), Wardrobe(75.centimeter))
    val desiredLength = 150
    val solutions = sut.calculateAllPossibileSolutions(options, desiredLength.centimeter)

    assert(solutions.size == 2)
}
```

Um einen einfacheren Outside Test zu haben, habe ich die Anzahl der 
Schrankmodulvarianten reduziert, sodass sich die Lösungen schnell und 
einfach per Hand bestimmen lassen. Die entsprechenden Klassen habe ich 
rudimentär implementiert und alle Funktion mit einem `TODO("Not yet 
implemented")` implementiert, sodass ich eindeutig auf die Stellen 
hingewiesen werden, welche ich implementieren muss.

Man könnte darüber diskutieren, ob die erwünschte Breite nicht ein field der 
`ConfigurationCalculator` sein sollte, aber ich möchte versuchen so wenig 
fields wie möglich zu verwenden. Jetzt wo darüber nachdenke, kann man sich 
so die Klasse insgesamt sparen und Kotlins Möglichkeit ausnutzen Funktionen 
direkt in einem File zu definieren. Was uns zu diesem Testfall bringt:

```kotlin
@Test
fun `calculate all possible Wardrobe options`(){
    val options = setOf(Wardrobe(50.centimeter), Wardrobe(75.centimeter))
    val desiredLength = 150
    val solutions = calculateAllPossibleSolutions(options, desiredLength.centimeter)

    assert(solutions.size == 2)
}
```

## Implementierung

Wie kann man alle Kombinationen berechnen? Das klingt zunächst nach einem 
kombinatorischen Problem, aber es gibt noch die Nebenbedingung der Länge, 
die darauf Einfluss nimmt, wie viele Elemente kombiniert werden 
können/müssen. So kann man bei einer Auswahl von folgenden Optionen: 

- 50 cm
- 100 cm
- 125 cm

und einer zielbreite von 250 cm eine Kombination mit 2 Elementen und 3 
Elemente, beziehungsweise sogar mit 5 Elementen finden. Deshalb scheint es 
mir sinnvoll zu sein, die Schrankmodulvarianten nach Größe zu sortieren, 
bevor diese Kombinationen bestimmt werden. Aufgrund der unterschiedlichen 
Menge an Kombinationen ist es sinnvoll ein Abbruchkriterium zu definieren. 
Dieses Abbruchkriterium soll die restliche Länge sein, welche noch durch 
Schrankmodulvarianten gefüllt werden muss. Wenn diese Länge 0 ist, dann hat 
man eine valide Lösung gefunden, und wenn sie größer null ist muss man die 
nächste Kombination finden, welche noch in diese Restlänge reinpasst. So 
viel zum Plan jetzt beginnen wir mit den Tests.

Beim Implementieren kommt mir direkt wieder der Gedanke, dass ich frühzeitig 
redundante Lösungen eliminieren muss. So gibt es beispielsweise keinen 
Unterschied zwischen der Lösung (50, 50, 100) und (50, 100, 50) oder (100, 
50, 50).

Ich lasse mich nicht wirklich durch Tests führen, sondern versuche direkt 
die perfekte Lösung zu finden. Dieses Verhalten blockiert mich eher und 
führt zu recht komplexem Code. Einer der Schritte, bei dem ich gelandet bin, 
sieht wie folgt aus: 

```kotlin
fun calculateSolutionBasedOn(option: Wardrobe, options: Set<Wardrobe>, desiredLength: Centimeter): List<Solution> {
    return when {
        option.width == desiredLength -> listOf(Solution(listOf(option)))
        option.width > desiredLength -> emptyList()
        else -> calculateAllPossibleSolutions(options.minus(option), desiredLength - option.width)
            .map { solution -> solution.copy(wardrobes = solution.wardrobes.plus(option)) }
    }
}
```

Ich sollte anfangen mich an die Lösung heranzutasten, anstatt direkt mit der 
perfekten Lösung zu starten, selbst wenn ich denke, dass die Lösung dann 
nicht perfekt aussieht, aber dann kann ich diese Lösung immer noch refactorn.

Ich muss anfangen mich an die Lösung heranzutasten, anstatt direkt mit der 
Komplettlösung zu starten. Also nochmals von vorne. Ich sollte mit kleineren 
Tests anfangen, um mich der Lösung zu näheren. Also starten wir mit der 
Funktion, welche für eine gewählte Option entscheidet, ob basierend auf den 
möglichen Optionen und der Zielbreite eine Lösung zurückgibt oder nicht. Am 
einfachsten startet man mit dem Fall, dass die gewählte Option genau die 
Lösung erfüllt: 

```kotlin
fun `calculate solution for a wardrobe that matches the given desiredLength`(){
    val option = Wardrobe(50.cm)
    val options = setOf(Wardrobe(50.cm), Wardrobe(75.cm))
    val desiredLength = 50
    val solutions = getSolutionForOption(option, options, desiredLength.cm)

    assert(solutions.size == 1)
    assert(solutions[0].wardrobes.size == 1)
    assert(solutions[0].wardrobes[0] == option)
}
```

Zusätzlich mit dem Test, wenn die gewählte Option zu groß ist:

```kotlin
@Test
fun `calculate solution for a wardrobe that is bigger than the given desiredLength`(){
    val option = Wardrobe(75.cm)
    val options = setOf(Wardrobe(50.cm), Wardrobe(75.cm))
    val desiredLength = 50
    val solutions = getSolutionForOption(option, options, desiredLength.cm)

    assert(solutions.isEmpty())
}
```

Um das nächste Mal weiterzumachen, habe ich mir noch zwei Kommentare für 
entsprechende Tests geschrieben:

```kotlin
// Test for calculating a solution when two wardrobes are needed
// Test for calculating a solution when length cannot be solved
```

## Reflektion

Heute habe ich das erste Mal meinen Gedankenprozess dokumentiert und gemerkt,
wo ich am meisten Zeit verliere. Ich versuche immer sofort die perfekte 
Lösung zu finden und mich nicht der Lösung schrittweise zu nähern. Ansonsten 
bin ich mit dem entstandenen Code, bzw. der entstandenen Lösung sehr 
zufrieden. Für das nächste Mal sollte ich schnelle und einfache Tests 
bevorzugen und diese lieber wieder wegwerfen, anstatt die perfekte Lösung zu 
suchen. 

## Methoden-Reflektion

Es hilft mir diese Texte in meiner Muttersprache zu schreiben und nicht in 
Englisch. In Englisch schreiben ist zwar möglich, aber benötigt definitiv 
mehr Gehirnkapazität. Deshalb werde ich die Begleitung für diese Katas auf 
Deutsch schreiben und mithilfe von AI-Tool (ChatGPT) übersetzen und leicht 
umformulieren lassen, sodass die Texte auch angenehm zu lesen sind. 