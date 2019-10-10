package com.bcloutier.swjam.people

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonView
import org.hibernate.annotations.Table
import java.util.*
import javax.persistence.Entity
import javax.swing.text.View
import kotlin.collections.ArrayList

@javax.persistence.Table(name="person")
data class SwPersonResponse(
        var name: String= "Default",
        var height: String= "Default",
        var mass: String= "Default",
        var hair_color: String= "Default",
        var skin_color: String= "Default",
        var eye_color: String= "Default",
        var birth_year: String= "Default",
        var gender: String= "Default",
        var vehicles: ArrayList<String> = arrayListOf(""),
        var starships: ArrayList<String> = arrayListOf(""),
        var created: String= "Default",
        var edited: String= "Default",
        var url: String= "Default",
        var homeworld: String = "Default",
        var films: ArrayList<String> = arrayListOf("Default"),
        var species: ArrayList<String> = arrayListOf(""),
        var test: String = "Default"
)
// {
//    override fun equals(other: Any?): Boolean {
//        if (this === other) return true
//        if (javaClass != other?.javaClass) return false
//
//        other as SwPersonResponse
//
//        if (!Arrays.equals(films, other.films)) return false
//
//        return true
//    }
//
//    override fun hashCode(): Int {
//        return Arrays.hashCode(films)
//    }
//}