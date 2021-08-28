package com.redditclone.redditclone.controllers;


import com.redditclone.redditclone.dto.PostRequest;
import com.redditclone.redditclone.dto.PostResponse;
import com.redditclone.redditclone.exceptions.SubredditNotFoundException;
import com.redditclone.redditclone.model.Post;
import com.redditclone.redditclone.service.PostService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<Void> createPost(@RequestBody PostRequest postRequest) throws SubredditNotFoundException {
        postService.save(postRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Post>> getAllPosts() {
        return ResponseEntity.ok().body(postService.getAllPosts());
    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<PostResponse> getPost(@PathVariable Long id) {
//        return status(HttpStatus.OK).body(postService.getPost(id));
//    }
//
//    @GetMapping("by-subreddit/{id}")
//    public ResponseEntity<List<PostResponse>> getPostsBySubreddit(Long id) {
//        return status(HttpStatus.OK).body(postService.getPostsBySubreddit(id));
//    }
//
//    @GetMapping("by-user/{name}")
//    public ResponseEntity<List<PostResponse>> getPostsByUsername(@PathVariable String name) {
//        return status(HttpStatus.OK).body(postService.getPostsByUsername(name));
//    }
}