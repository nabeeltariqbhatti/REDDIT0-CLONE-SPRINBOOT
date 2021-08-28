package com.redditclone.redditclone.controllers;


import com.redditclone.redditclone.dto.SubredditDto;
import com.redditclone.redditclone.mapper.SubredditMapper;
import com.redditclone.redditclone.service.SubredditService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/subreddit")
@AllArgsConstructor
@Slf4j
public class SubredditController {

    private final SubredditService subredditService;


    @PostMapping("/create")
    public ResponseEntity<SubredditDto> createSubreddit(@RequestBody SubredditDto subredditDto){

    return  ResponseEntity.status(HttpStatus.CREATED).body(subredditService.save(subredditDto));
}

@GetMapping("/getsubreddits")
    public ResponseEntity<List<SubredditDto>> getAllSubreddits(){
    return ResponseEntity.status(HttpStatus.OK).body(subredditService.getAll());

}

@GetMapping("/{id}")
    public ResponseEntity<Object> getSubreddit(@PathVariable Long id){

        return ResponseEntity.ok().body(subredditService.find(id));
}
}
