package com.alibou.security.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.apache.commons.lang3.RandomStringUtils.randomNumeric;

@RestController
@RequestMapping(value = "/api/foos")
@CrossOrigin(origins = "http://localhost:8084")
@RequiredArgsConstructor
public class FooController {

    @GetMapping(value = "/{id}")
    public FooDto findOne(@PathVariable Long id) {
        return new FooDto(Long.parseLong(randomNumeric(2)), randomAlphabetic(4));
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public void create(@RequestBody FooDto newFoo) {
    }
}