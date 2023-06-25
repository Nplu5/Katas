# Configure your own wardrobe

[Source](https://kata-log.rocks/configure-wardrobe-kata)

## Description

- Need a new wardrobe right after moving
- Cannot find wardrobe that fits exactly wall size
- Furniture dealer offers configuration possibility for wardrobe
- elements are available in the following sizes:
    - 50cm (59€)
    - 75cm (62€)
    - 100cm (90€)
    - 120cm (111€)
- Wall length: 250cm
- With which combinations of wardrobe can you make the most of the space?
- Write a function that returns all combinations of wardrobe elements that
  exactly fill the wall.
- Write a function that returns the cheapest combination based on the prices

## Understanding the project

### Gradle

This configures a test dependency in this case the 'kotlin("test")' package. The
method `kotlin()` is a convenience function for defining kotlin-specific
dependencies.

```gradle
dependencies {
    testImplementation(kotlin("test"))
}
```