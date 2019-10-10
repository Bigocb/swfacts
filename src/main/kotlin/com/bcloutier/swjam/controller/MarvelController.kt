package com.bcloutier.swjam.controller

import com.bcloutier.swjam.data.SwCallService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(path = arrayOf("/mvl"))
class MarvelController{

    @GetMapping("/person")
    fun person(@RequestParam(value = "id", defaultValue = "1") id: String) =
            SwCallService().getRespMar()
}