package com.angga.pokedex.presentation.utils

fun String.formatNumberWithLeadingZeros(): String {
    return when (this.length) {
        1 -> "#00$this"
        2 -> "#0$this"
        else -> "#$this"
    }
}