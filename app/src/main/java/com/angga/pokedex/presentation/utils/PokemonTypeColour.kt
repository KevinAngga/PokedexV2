package com.angga.pokedex.presentation.utils

import androidx.compose.ui.graphics.Color
import com.angga.pokedex.presentation.ui.theme.BugType
import com.angga.pokedex.presentation.ui.theme.DarkType
import com.angga.pokedex.presentation.ui.theme.DragonType
import com.angga.pokedex.presentation.ui.theme.ElectricType
import com.angga.pokedex.presentation.ui.theme.FairyType
import com.angga.pokedex.presentation.ui.theme.FightType
import com.angga.pokedex.presentation.ui.theme.FireType
import com.angga.pokedex.presentation.ui.theme.FlyingType
import com.angga.pokedex.presentation.ui.theme.GhostType
import com.angga.pokedex.presentation.ui.theme.GrassType
import com.angga.pokedex.presentation.ui.theme.GroundType
import com.angga.pokedex.presentation.ui.theme.IceType
import com.angga.pokedex.presentation.ui.theme.NormalType
import com.angga.pokedex.presentation.ui.theme.PoisonType
import com.angga.pokedex.presentation.ui.theme.PsychicType
import com.angga.pokedex.presentation.ui.theme.RockType
import com.angga.pokedex.presentation.ui.theme.SteelType
import com.angga.pokedex.presentation.ui.theme.WaterType

sealed class PokemonTypeColour(
    var typeColor: Color
) {

    object Normal : PokemonTypeColour(
        typeColor = NormalType
    )

    object Fire : PokemonTypeColour(
        typeColor = FireType
    )

    object Fight : PokemonTypeColour(
        typeColor = FightType
    )

    object Water : PokemonTypeColour(
        typeColor = WaterType
    )

    object Flying : PokemonTypeColour(
        typeColor = FlyingType
    )

    object Grass : PokemonTypeColour(
        typeColor = GrassType
    )

    object Poison : PokemonTypeColour(
        typeColor = PoisonType
    )

    object Electric : PokemonTypeColour(
        typeColor = ElectricType
    )

    object Ground : PokemonTypeColour(
        typeColor = GroundType
    )

    object Psychic : PokemonTypeColour(
        typeColor = PsychicType
    )

    object Rock : PokemonTypeColour(
        typeColor = RockType
    )

    object Ice : PokemonTypeColour(
        typeColor = IceType
    )

    object Bug : PokemonTypeColour(
        typeColor = BugType
    )

    object Dragon : PokemonTypeColour(
        typeColor = DragonType
    )

    object Ghost : PokemonTypeColour(
        typeColor = GhostType
    )

    object Dark : PokemonTypeColour(
        typeColor = DarkType
    )

    object Steel : PokemonTypeColour(
        typeColor = SteelType
    )

    object Fairy : PokemonTypeColour(
        typeColor = FairyType
    )

    companion object {
        fun String.getTypeBackground(): PokemonTypeColour {
            return when (this.lowercase()) {
                "normal" -> Normal
                "fire" -> Fire
                "fight" -> Fight
                "water" -> Water
                "flying" -> Flying
                "grass" -> Grass
                "poison" -> Poison
                "electric" -> Electric
                "ground" -> Ground
                "psychic" -> Psychic
                "rock" -> Rock
                "ice" -> Ice
                "bug" -> Bug
                "dragon" -> Dragon
                "ghost" -> Ghost
                "dark" -> Dark
                "steel" -> Steel
                "fairy" -> Fairy
                else -> Normal
            }
        }
    }
}