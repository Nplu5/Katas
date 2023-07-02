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