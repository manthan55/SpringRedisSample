package org.manthan.springredissample.dtos;

import lombok.Getter;
import lombok.Setter;
import org.manthan.springredissample.models.Post;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class PostDTO {
    private Long id;
    private String title;
    private String content;

    public static PostDTO fromPostEntity(Post post){
        PostDTO postDTO = new PostDTO();
        postDTO.setId(post.getId());
        postDTO.setTitle(post.getTitle());
        postDTO.setContent(post.getContent());
        return postDTO;
    }

    public static List<PostDTO> fromPostEntityList(List<Post> posts){
        List<PostDTO> postDTOs = new ArrayList<>();
        for (Post post : posts){
            postDTOs.add(fromPostEntity(post));
        }
        return postDTOs;
    }
}
