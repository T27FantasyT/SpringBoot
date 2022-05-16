package com.t27.findjob.controller;

import com.t27.findjob.dto.JobRequest;
import com.t27.findjob.model.Job;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;
import java.util.stream.Stream;

@RestController
@RequestMapping(value = "/job")
public class JobController {
    private ConcurrentHashMap<String, Job> jobs;

    public JobController() {
        jobs = new ConcurrentHashMap<>();
        jobs.put("1",new Job("1","JavaDev","description", "HaNoi",8000000,14000000,"headhunter@gmail.com"));
        jobs.put("2",new Job("2","frontEnd","description", "HaiPhong",5000000,12000000,"headhungter@gmail.com"));
        jobs.put("3",new Job("3","SQL","description", "HaNoi",2000000,11000000,"headhungter@gmail.com"));
        jobs.put("4",new Job("4","Interm","description", "HoChiMinh",1000000,4000000,"headhungter@gmail.com"));
    }

    @GetMapping
    public List<Job> showAllJob(){
        return jobs.values().stream().toList();
    }

    @PostMapping
    public Job addNewJob(@RequestBody JobRequest jobRequest){
        String id = UUID.randomUUID().toString();
        Job job = new Job(id,jobRequest.title(),jobRequest.description(),jobRequest.location(),jobRequest.min_salary(), jobRequest.max_salary(), jobRequest.email_to());
        jobs.put(id,job);

        return job;
    }

    @PutMapping
    public Job updateJob(@RequestParam("id") String id,@RequestBody JobRequest jobRequest){
        Job updateJob = new Job(id,jobRequest.title(),jobRequest.description(),jobRequest.location(), jobRequest.min_salary(), jobRequest.max_salary(), jobRequest.email_to());

        jobs.put(id,updateJob);

        return updateJob;
    }

    @DeleteMapping
    public String deleteJob(@RequestParam("id") String id){
        jobs.remove(id);

        return "this job has been removed";
    }

    @GetMapping(value = "/sortbylocation")
    public List<Job> sortByLocation(){
       Stream<Job> sortedJob = jobs.values().stream().sorted(new Comparator<Job>() {
            @Override
            public int compare(Job o1, Job o2) {
                return o1.getLocation().compareTo(o2.getLocation());
            }
        });
       return sortedJob.toList();
    }

    @GetMapping(value = "/keyword/{keyword}")
    public List<Job> findByKeyWord(@PathVariable("keyword") String keyword){
        List<Job> result = new ArrayList<>();
       for(Map.Entry<String,Job> entry : jobs.entrySet()){
           if(entry.getValue().getTitle().contains(keyword) || entry.getValue().getDescription().contains(keyword)){
               result.add(entry.getValue());
           }
       }
       return result;
    }

    @GetMapping(value = "/query")
    public List<Job> findByKeyWordAndLocation(@RequestParam("location") String location,@RequestParam("keyword") String keyword){
        List<Job> findByKeyWord = findByKeyWord(keyword);
        List<Job> result = new ArrayList<>();
        for(Job j : findByKeyWord){
            if(j.getLocation().equals(location)){
                result.add(j);
            }
        }
        return result;
    }
}
