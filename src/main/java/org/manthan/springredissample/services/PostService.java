package org.manthan.springredissample.services;

import org.manthan.springredissample.exceptions.PostDoesNotExistException;
import org.manthan.springredissample.models.Post;
import org.manthan.springredissample.repositories.PostRepository;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
public class PostService {
    private PostRepository postRepository;
    private RedisTemplate<Long, Post> redisTemplate;

    public PostService(PostRepository postRepository, RedisTemplate<Long, Post> redisTemplate) {
        this.postRepository = postRepository;
        this.redisTemplate = redisTemplate;
    }

    public List<Post> getAllPosts(){
        return this.postRepository.findAll();
    }

    // both @Cacheable and this.redisTemplate are doing the same thing here
    // so disabling this.redisTemplate
//    https://premika-17.medium.com/implementing-redis-in-spring-boot-3d2756e5ab69
//    @Cacheable(value = "post")
    public Post getPost(Long postId) throws PostDoesNotExistException, InterruptedException {
        Post post = null;
        post = this.redisTemplate.opsForValue().get(postId);
        if(post != null) return post;

        // intentional 4 seconds delay to simulate proper DB call
        Thread.sleep(4 * 1000);
        Optional<Post> postOptional = this.postRepository.findById(postId);
        if(postOptional.isEmpty()) throw new PostDoesNotExistException(postId);
        post = postOptional.get();
        // https://stackoverflow.com/a/64161239
        redisTemplate.opsForValue().set(postId, post, 30, TimeUnit.SECONDS);
//        redisTemplate.opsForValue().set(postId, post);
        return post;
    }

    public Post addPost(Post post){
        Post savedPost = this.postRepository.save(post);
        return savedPost;
    }

    public void deleteAllPosts(){
        this.postRepository.deleteAll();
    }
}
