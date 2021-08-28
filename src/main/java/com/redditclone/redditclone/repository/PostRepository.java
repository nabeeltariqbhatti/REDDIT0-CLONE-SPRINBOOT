package com.redditclone.redditclone.repository;

import com.redditclone.redditclone.dto.PostResponse;
import com.redditclone.redditclone.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Arrays;


@Repository
public interface PostRepository extends JpaRepository<Post,Long> {


}
