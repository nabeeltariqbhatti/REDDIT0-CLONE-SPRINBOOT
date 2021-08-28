package com.redditclone.redditclone.service;


import com.redditclone.redditclone.dto.SubredditDto;
import com.redditclone.redditclone.mapper.SubredditMapper;
import com.redditclone.redditclone.model.Subreddit;
import com.redditclone.redditclone.repository.SubredditRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class SubredditService {

    private final SubredditRepository subredditRepository;
    private final SubredditMapper subredditMapper;

    @Transactional
    public SubredditDto save(SubredditDto subredditDto) {
        Subreddit save = subredditRepository.save(subredditMapper.mapDtoToSubreddit(subredditDto));
        subredditDto.setId(save.getId());
        return subredditDto;
    }



    private Subreddit mapSubredditDto(SubredditDto subredditDto) {


       return  Subreddit.builder().name(subredditDto.getName()).description(subredditDto.getDescription()).build();
}


        private SubredditDto mapDto(Subreddit subreddit){

        return SubredditDto.builder().name(subreddit.getName())
                .id(subreddit.getId())
                .numberOfPosts(subreddit.getPosts().size())
                .build();
        }

    @Transactional
    public List<SubredditDto> getAll() {

       return subredditRepository.findAll().stream().map(this::mapDto).collect(Collectors.toList());
    }

    public Object find(Long id) {
        return subredditRepository.findById(id);
    }
}
