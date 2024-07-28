package org.manthan.springredissample.controllers;

import org.manthan.springredissample.dtos.PostDTO;
import org.manthan.springredissample.exceptions.PostDoesNotExistException;
import org.manthan.springredissample.models.Post;
import org.manthan.springredissample.services.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/post")
public class PostController {
    private PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }


    @GetMapping("/")
    public ResponseEntity<List<PostDTO>> getAllPosts(){
        List<PostDTO> postDTOs = null;
        HttpStatus httpStatus = HttpStatus.OK;

        try{
            List<Post> posts = this.postService.getAllPosts();
            postDTOs = PostDTO.fromPostEntityList(posts);

        }
        catch(Exception ex){
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return ResponseEntity.status(httpStatus).body(postDTOs);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostDTO> getPost(@PathVariable(name = "postId") Long postId){
        PostDTO postDTO = null;
        HttpStatus httpStatus = HttpStatus.OK;

        try{
            Post post = this.postService.getPost(postId);
            postDTO = PostDTO.fromPostEntity(post);
        }
        catch(PostDoesNotExistException ex){
            httpStatus = HttpStatus.NOT_FOUND;
        }
        catch(Exception ex){
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return ResponseEntity.status(httpStatus).body(postDTO);
    }
}