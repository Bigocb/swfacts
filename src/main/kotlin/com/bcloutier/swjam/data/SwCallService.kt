package com.bcloutier.swjam.data

import com.bcloutier.swjam.people.SwPersonResponse
import com.bcloutier.swjam.search.SearchResponse
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import khttp.get
import khttp.responses.Response
import org.json.JSONArray
import org.json.JSONObject
import java.io.ByteArrayOutputStream
import com.fasterxml.jackson.core.JsonEncoding
import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.core.JsonFactory
import org.hibernate.hql.internal.ast.tree.Statement
import org.springframework.orm.jpa.vendor.Database
import java.math.BigInteger
import java.sql.Connection
import java.sql.Timestamp
import java.security.MessageDigest
import javax.xml.bind.DatatypeConverter






class SwCallService {

    val public_key:String = "80b5c4ef85fe7ca73ba1b89e8dc552a6"
    val private_key:String = "4ca93e8f87780170f41d585621254721a8fa1e5d"
    var tsLong: String = "thesoer"
    var hashString:String = tsLong.toLowerCase() + private_key.toLowerCase() + public_key.toLowerCase()
    var md = MessageDigest.getInstance("MD5")
    var testHash = md.update(hashString.toByteArray());
    var digest = md.digest()
    var myHash = DatatypeConverter
            .printHexBinary(digest).toLowerCase()

    fun getPerson(url: String): List<SwPersonResponse> {

        val r = get(url)
        println(r.jsonObject)
        val sp = SwPersonResponse()
        val list:ArrayList<SwPersonResponse> = mutableListOf<SwPersonResponse>() as ArrayList<SwPersonResponse>
        sp.homeworld = r.jsonObject.getString("homeworld")
        sp.name = r.jsonObject.getString("name")
        sp.gender = r.jsonObject.getString("gender")
        sp.skin_color = r.jsonObject.getString("skin_color")
        sp.edited = r.jsonObject.getString("edited")
        sp.created = r.jsonObject.getString("created")
        sp.mass = r.jsonObject.getString("mass")
        sp.hair_color = r.jsonObject.getString("hair_color")
        sp.height = r.jsonObject.getString("height")

        //films
        val films = r.jsonObject.getJSONArray("films")
        for (i in 0 until films.length()) {
            sp.films.add(films.getString(i))
        }
        //species
        val species = r.jsonObject.getJSONArray(("species"))
        for (i in 0 until species.length()) {
            sp.species.add((species.getString(i)))
        }
        //starships
        val starships = r.jsonObject.getJSONArray(("starships"))
        for (i in 0 until starships.length()) {
            sp.starships.add((starships.getString(i)))
        }
        //vehicles
        val vehicles = r.jsonObject.getJSONArray(("vehicles"))
        for (i in 0 until vehicles.length()) {
            sp.vehicles.add((vehicles.getString(i)))
        }
        sp.eye_color = r.jsonObject.getString("eye_color")
        sp.birth_year = r.jsonObject.getString("birth_year")
        sp.url = r.jsonObject.getString("url")

        list.add(sp)
        return list
    }

    private fun processPeople(response: JSONObject): List<SearchResponse> {
        val people = response.getJSONArray("people")
        val starships: JSONArray = response.getJSONArray("starships")
        val films: JSONArray = response.getJSONArray("films")
        val vehicles: JSONArray = response.getJSONArray("vehicles")
        val species: JSONArray = response.getJSONArray("species")
        val planets: JSONArray = response.getJSONArray("planets")
        val list:ArrayList<SearchResponse> = mutableListOf<SearchResponse>() as ArrayList<SearchResponse>

        for (i in 0 until people.length()) {
            val d = SearchResponse()
            val test = people[i] as JSONObject
            d.name = test.getString("name")
            d.url = test.getString("url")
            d.type = "Person"
            list.add(d)
        }
        for (i in 0 until starships.length()) {
            val d = SearchResponse()
            val test = starships[i] as JSONObject
            d.name = test.getString("name")
            d.url = test.getString("url")
            d.type = "Starship"
            list.add(d)
        }
        for (i in 0 until films.length()) {
            val d = SearchResponse()
            val test = films[i] as JSONObject
            d.name = test.getString("title")
            d.url = test.getString("url")
            d.type = "Film"
            list.add(d)
        }
        for (i in 0 until vehicles.length()) {
            val d = SearchResponse()
            val test = vehicles[i] as JSONObject
            d.name = test.getString("name")
            d.url = test.getString("url")
            d.type = "Vehicles"
            list.add(d)
        }
        for (i in 0 until species.length()) {
            val d = SearchResponse()
            val test = species[i] as JSONObject
            d.name = test.getString("name")
            d.url = test.getString("url")
            d.type = "Species"
            list.add(d)
        }
        for (i in 0 until planets.length()) {
            val d = SearchResponse()
            val test = planets[i] as JSONObject
            d.name = test.getString("name")
            d.url = test.getString("url")
            d.type = "Planet"
            list.add(d)
        }
        return list
    }

    fun getRespMar(): List<MarvelPerson> {
        var limit = 1
        val r = get("https://gateway.marvel.com/v1/public/characters?limit=" + limit + "&ts="+tsLong+"&apikey="+public_key+"&hash="+myHash)
        val data = r.jsonObject.getJSONObject(("data"))
        val people = data.getJSONArray(("results"))
        val list:ArrayList<MarvelPerson> = mutableListOf<MarvelPerson>() as ArrayList<MarvelPerson>
        val count: Int = data.getInt("total")
        var loops: Int = count/100
        var offset = 0
        for (i in loops downTo 0) {
            var limit = 100
            val q = get("https://gateway.marvel.com/v1/public/characters?limit=" + limit + "&offset="+ offset + "&ts="+tsLong+"&apikey="+public_key+"&hash="+myHash)
            offset += 100
            val d = q.jsonObject.getJSONObject(("data"))
            val re = d.getJSONArray(("results"))
            for (i in 0 until re.length()) {
                val m = MarvelPerson()
                val test = re[i] as JSONObject
                m.name = test.getString("name")
                val thumbnails = test.getJSONObject(("thumbnail"))
                for (i in 0 until thumbnails.length()) {
                    m.thumbnail = thumbnails.getString("path")
                    m.extension = thumbnails.getString("extension")
                }
                m.description = test.getString("description")
                m.id = test.getInt("id")
                list.add(m)
            }
        }
        return list
    }

    fun getResponses(search: String?): List<SearchResponse> {

        return if(search == ""){
            val r = get("https://swapi.co/api/people/")
            val people = r.jsonObject.getJSONArray(("people"))
            val combined = JSONObject()
            combined.put("Object1", people);
            val swPersonResponse  = processPeople(combined)
            return swPersonResponse

        } else {
                val p = get("https://swapi.co/api/people/?search=$search")
                val s = get("https://swapi.co/api/starships/?search=$search")
                val f = get("https://swapi.co/api/films/?search=$search")
                val v = get("https://swapi.co/api/vehicles/?search=$search")
                val sp = get("https://swapi.co/api/species/?search=$search")
                val pl = get("https://swapi.co/api/planets/?search=$search")

                val people = p.jsonObject.getJSONArray(("results"))
                val starships = s.jsonObject.getJSONArray(("results"))
                val films = f.jsonObject.getJSONArray(("results"))
                val vehicles = v.jsonObject.getJSONArray(("results"))
                val species = sp.jsonObject.getJSONArray(("results"))
                val planets = pl.jsonObject.getJSONArray(("results"))
                val combined = JSONObject()

                combined.put("people", people);
                combined.put("starships", starships);
                combined.put("films", films);
                combined.put("vehicles", vehicles);
                combined.put("species", species);
                combined.put("planets", planets);

                val swPersonResponse = processPeople(combined)
                return swPersonResponse

        }
    }
}

