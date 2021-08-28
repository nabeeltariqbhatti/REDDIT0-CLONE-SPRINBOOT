package com.redditclone.redditclone.service;


import com.redditclone.redditclone.dto.PostRequest;
import com.redditclone.redditclone.exceptions.SubredditNotFoundException;
import com.redditclone.redditclone.model.Post;
import com.redditclone.redditclone.model.Subreddit;
import com.redditclone.redditclone.repository.PostRepository;
import com.redditclone.redditclone.repository.SubredditRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
@Transactional
public class PostService {

private final PostRepository postRepository;


   private final SubredditRepository subredditRepository;
   private final AuthService authService;
    public Subreddit save(PostRequest postRequest) throws SubredditNotFoundException {
 Subreddit subreddit = subredditRepository.findByName(postRequest.getSubredditName());
 if(subreddit == null){
     throw  new SubredditNotFoundException("Subreddit not found");
 }else{
     return subreddit;
 }
//        postRepository.save(postMapper.map(postRequest, subreddit, authService.getCurrentUser()));
    }

    public List<Post> getAllPosts() {
      return  postRepository.findAll();
    }
//
//    @Transactional
//    public PostResponse getPost(Long id) {
//        Post post = postRepository.findById(id)
//                .orElseThrow(() -> new PostNotFoundException(id.toString()));
//        return postMapper.mapToDto(post);
//    }
//
//    @Transactional
//    public List<PostResponse> getAllPosts() {
//        return postRepository.findAll()
//                .stream()
//                .map(postMapper::mapToDto)
//                .collect(Collectors.toList());
//    }
//
//    @Transactional
//    public List<PostResponse> getPostsBySubreddit(Long subredditId) {
//        Subreddit subreddit = subredditRepository.findById(subredditId)
//                .orElseThrow(() -> new SubredditNotFoundException(subredditId.toString()));
//        List<Post> posts = postRepository.findAllBySubreddit(subreddit);
//        return posts.stream().map(postMapper::mapToDto).collect(toList());
//    }
//
//    @Transactional(readOnly = true)
//    public List<PostResponse> getPostsByUsername(String username) {
//        User user = userRepository.findByUsername(username)
//                .orElseThrow(() -> new UsernameNotFoundException(username));
//        return postRepository.findByUser(user)
//                .stream()
//                .map(postMapper::mapToDto)
//                .collect(Collectors.toList());
//    }
}
