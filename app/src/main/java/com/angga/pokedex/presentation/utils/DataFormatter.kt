package com.angga.pokedex.presentation.utils

fun String.formatNumberWithLeadingZeros(): String {
    return when (this.length) {
        1 -> "#00$this"
        2 -> "#0$this"
        else -> "#$this"
    }
}

fun Int.getWeightString(): String = String.format("%.1f kg", this.toFloat() / 10)
fun Int.getHeightString(): String = String.format("%.1f m", this.toFloat() / 10)