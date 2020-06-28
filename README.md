# RandomCity

Random City App

Functionality: 
 - Every 5 sec. `SomeProducer` will produce the next value with random City and Color combination from defined
below lists. This only happens when the app is in the foreground : 

```kotlin
private val cities = listOf("Gdańsk", "Warszawa", "Poznań", "Białystok", "Wrocław", "Katowice", "Kraków")
private val colors = listOf("Yellow", "Green", "Blue", "Red", "Black", "White")
```

- The app starts with `SplashScreen` and on the first emission from `SomeProducer` it goes to `MainScreen`.
- `MainScreen` will be a sorted by city alphabetically list of views represented by `TextView` where 
(`text = city` and `textColor = color`) and another `TextView` with Date & Time of emission.
- Those two `TextView`s should be side by side when viewed on a Tablet on the landscape orientation.
- When new item is emitted it should be added to the list in the correct place.
- On item click opens `DetailsActivity`.
- `DetailsActivity` has got a Name of City as Toolbar title and the toolbar color should be in `Color`. Below toolbar there is a map centered on the chosen city.

| Animation | 
| ---- | 
| ![App presentation](https://github.com/jackszm/RandomCity/blob/master/readme_gif.gif) | 
