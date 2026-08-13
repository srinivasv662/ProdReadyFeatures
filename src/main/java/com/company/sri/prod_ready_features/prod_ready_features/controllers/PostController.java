package com.company.sri.prod_ready_features.prod_ready_features.controllers;

import com.company.sri.prod_ready_features.prod_ready_features.dto.PostDTO;
import com.company.sri.prod_ready_features.prod_ready_features.services.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping
    public List<PostDTO> getAllPosts() {
        return postService.getAllPosts();
    }

    @GetMapping("/{postId}")
    public PostDTO getPostById(@PathVariable Long postId) {
        return postService.getPostById(postId);
    }

    @PostMapping
    public PostDTO createPost(@RequestBody PostDTO inputDTO) {
        return postService.createNewPost(inputDTO);
    }

    @PutMapping(path = "/{postId}")
    public PostDTO updatePost(@RequestBody PostDTO inputDTO, @PathVariable Long postId) {
        return postService.updatePost(inputDTO, postId);
    }

}
