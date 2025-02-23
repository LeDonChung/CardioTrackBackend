package vn.edu.iuh.fit.post.services;

import vn.edu.iuh.fit.post.exceptions.PostException;
import vn.edu.iuh.fit.post.model.dto.reponse.PostResponse;
import vn.edu.iuh.fit.post.model.dto.request.PostRequest;

public interface PostService {
    //tạo bài post
PostResponse createPost(PostRequest  postRequest) throws PostException;
}
