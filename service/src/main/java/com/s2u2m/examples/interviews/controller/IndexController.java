package com.s2u2m.examples.interviews.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("")
public class IndexController {

  @PostMapping("/{ping}")
  public String login(@PathVariable String ping) {
    return ping;
  }
}
