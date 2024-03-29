package com.redditclone.redditclone.repository;


import com.redditclone.redditclone.model.Subreddit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubredditRepository extends JpaRepository<Subreddit,Long> {
    Subreddit findByName(String subredditName);
}
