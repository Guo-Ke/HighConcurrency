package com.example.concurrency.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 郑启
 * @date 2020/07/21 15:06
 */
@RestController
@RequestMapping("/test")
@Slf4j
public class TestController {
	@PostMapping("/")
	public String hello(){
		return "hello";
	}

}
