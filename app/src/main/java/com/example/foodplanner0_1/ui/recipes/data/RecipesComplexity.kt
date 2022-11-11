package com.example.foodplanner0_1.ui.recipes.data

data class ComplexityValue (
    var name: String = "",
    var color: String = ""
)  {
}

enum class RecipesComplexity(val v: ComplexityValue) {
    all(ComplexityValue("alle", "#FFFFFF")),
    high(ComplexityValue("hoch", "#E34732")),
    medium(ComplexityValue("mittel", "#E0B100")),
    low(ComplexityValue("niedrig", "#099161")),
    NA(ComplexityValue("ohne", "#FFFFFF"))
}