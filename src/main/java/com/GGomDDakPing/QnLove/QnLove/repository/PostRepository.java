package com.GGomDDakPing.QnLove.QnLove.repository;

import com.GGomDDakPing.QnLove.QnLove.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author : frozzun
 * @filename :PostRepository.java
 * @since 10/12/24
 */
@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

}
