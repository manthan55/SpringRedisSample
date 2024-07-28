package org.manthan.springredissample.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostDoesNotExistException extends Exception {
    public PostDoesNotExistException(Long postId) {
        super("Post with id:"+postId+" was not found");
    }
}