package com.example.lab1_homework.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/")
public class HomeController {
    @GetMapping
    @ResponseBody
    public String helloWorld() {
        return "hello world";
    }

    @GetMapping("/random")
    @ResponseBody
    public String randomString() {
        final String alpha = "abcdefghijklmnopqrstuvwxyz";
        final String alphaUpper = alpha.toUpperCase();
        final String number = "0123456789";
        final String alphaNumber = alpha + alphaUpper + number;

        StringBuilder sb = new StringBuilder();
        int stringLength = 8;
        for (int i = 0; i < stringLength; i++) {
            int index = (int) (alphaNumber.length() * Math.random());
            sb.append(alphaNumber.charAt(index));
        }
        return sb.toString();
    }

    @GetMapping("/quote")
    @ResponseBody
    public String quote() {
        final String[] data = {"Kiến tha lâu đầy tổ",
                "Có công mài sắt, có ngày nên kim",
                "Không thầy đố mày làm nên",
                "Học thầy không tày học bạn" };
        int randomIndex = (int) (data.length*Math.random());
        
        return data[randomIndex];
    }

    @RequestMapping(value = "/bmi", method = RequestMethod.POST)
    @ResponseBody
    public double bmi(@RequestParam("height") double height,@RequestParam("weigh") double weigh){
        return weigh/Math.pow(height,2);
    }

    List<Student> list = new ArrayList<>();

    @RequestMapping(value = "/student", method = RequestMethod.GET)
    @ResponseBody

    public List<Student> students(){
        return list;
    }

    @RequestMapping(value="/student", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody

    public String student(@RequestBody Student student){
        list.add(student);

        return "New student has been added to the students list";
    }


}
