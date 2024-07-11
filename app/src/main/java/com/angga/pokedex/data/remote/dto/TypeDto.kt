package com.angga.pokedex.data.remote.dto

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class TypeDto(
    @SerializedName("name")
    val name : String = ""
)
