package com.redditclone.redditclone.repository;

import com.redditclone.redditclone.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PostRepository extends JpaRepository<Post,Long> {

}
