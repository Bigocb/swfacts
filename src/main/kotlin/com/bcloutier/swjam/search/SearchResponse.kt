package com.bcloutier.swjam.search

import java.util.*
import kotlin.collections.ArrayList

data class SearchResponse(
        var name: String= "Default",
        var url: String= "Default",
        var type: String = "Default"
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