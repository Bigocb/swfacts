package com.bcloutier.swjam.controller

import com.bcloutier.swjam.data.SwCallService
import com.bcloutier.swjam.data.SwInfoRepository
import com.bcloutier.swjam.people.SwPersonResponse
import com.bcloutier.swjam.search.SearchResponse
import org.springframework.web.bind.annotation.*
import com.fasterxml.jackson.databind.ObjectMapper
import org.json.JSONObject
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.GetMapping
import java.util.*


@Controller
@RequestMapping(path = arrayOf("/swinfo"))
class SwInfoController(private val swInfoRepository: SwInfoRepository) {

    @Autowired
    private val jacksonObjectMapper: ObjectMapper? = null

    @GetMapping("/person")
    fun person(@RequestParam(value = "id", defaultValue = "1") id: String) =
            SwCallService().getPerson(id)

    @GetMapping("/people")
    fun people(@RequestParam(value = "search", defaultValue = "") search: String?): List<SearchResponse> {

        return SwCallService().getResponses(search)
    }

    @RequestMapping(value = ["/search"], method = [RequestMethod.GET])
    fun search(@RequestParam(value = "search", required = false, defaultValue = "") search: String, model: Model): String {
        val swCallService = SwCallService()
        if (search != "") {
            model.addAttribute("results", swCallService.getResponses(search))
        }
        return "index"
    }

    @RequestMapping(value = ["/marvel"], method = [RequestMethod.GET])
    fun msearch(model: Model): String {
        val swCallService = SwCallService()
            model.addAttribute("results", swCallService.getRespMar())
        return "marvel"
    }

    @RequestMapping(value = ["/detail"], method = [RequestMethod.GET])
    fun getDetails(
            @RequestParam(
                    value = "url", required = false, defaultValue = ""
            )
            url: String,
            @RequestParam(
                    value = "type", required = false, defaultValue = ""
            ) type: String, model: Model
    )
            : String {
        val swCallService = SwCallService()

        if(type.toLowerCase() == ("person")) {
            model.addAttribute("detail", swCallService.getPerson(url))
        }
        return "detail"
    }
}