## Wenig Zeit, aber trotzdem Fortschritt

Heute habe ich nicht viel Zeit, aber versuche trotzdem noch die beiden 
Stories von gestern aufzuarbeiten. Das wären die beiden Stories:

- Länge kann mit den Optionen nicht gelöst werden
- Länge, die mit zwei Optionen gelöst werden kann

Vor allem bei der ersten Lösung bin ich mir noch nicht sicher, wie ich das 
von korrekten Lösungen unterscheiden kann. Deshalb erstmal mit dem, was ich 
schon besser im Kopf habe anfangen.

Der Test 

```kotlin
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
```

hat zur Ergänzung der Funktion für den Fall geführt, dass die Restlänge noch 
größer null ist. Wichtig ist dabei, dass allen gefundenen Lösungen die 
aktuelle Option noch hinzugefügt wird:

```kotlin
remainingLength > 0.cm -> {
    val solutions = calculateAllPossibleSolutions(options, remainingLength)
    solutions.map { solution -> solution.copy(wardrobes = listOf(currentOption) + solution.wardrobes) }
}
```

## Keine Lösung möglich

Nun kommen wir zu dem Fall, dass keine Lösung möglich ist. Um zu erkennen, 
dass keine Lösung möglich ist, muss man überprüfen, ob die Liste der 
möglichen Lösungen leer (keine Lösung gefunden) ist oder mindestens eine 
Lösung enthält. Nachdem ich den Test eingebaut habe, fällt mir auf, dass die 
Lösung diesen Weg bereits eingebaut habe. Um so besser :). 

## Reflektion

Heute habe ich sehr kurzer Zeit ein paar Tests geschrieben und mich einer guten 
Lösung genähert. Bleibt nur noch das Problem, dass die `when`-clause noch 
einen nicht notwendige `else`-Zweig hat. Diesen sollte ich noch entfernen. 
Beim nächsten Mal geht es dann ums Refactoring.