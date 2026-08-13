package com.company.sri.prod_ready_features.prod_ready_features.services;

import com.company.sri.prod_ready_features.prod_ready_features.dto.PostDTO;
import com.company.sri.prod_ready_features.prod_ready_features.entities.PostEntity;
import com.company.sri.prod_ready_features.prod_ready_features.exceptions.ResourceNotFoundException;
import com.company.sri.prod_ready_features.prod_ready_features.repositories.PostRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService{

    private final PostRepository postRepository;

    private final ModelMapper modelMapper;

    @Override
    public List<PostDTO> getAllPosts() {
        return postRepository
                .findAll()
                .stream()
                .map(postEntity -> modelMapper.map(postEntity, PostDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public PostDTO createNewPost(PostDTO inputPost) {
        PostEntity postEntity = modelMapper.map(inputPost, PostEntity.class);
        return modelMapper.map(postRepository.save(postEntity), PostDTO.class);
    }

    @Override
    public PostDTO getPostById(Long postId) {
        PostEntity postEntity = postRepository
                .findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found with id " + postId));

        return modelMapper.map(postEntity, PostDTO.class);

    }

    @Override
    public PostDTO updatePost(PostDTO inputDTO, Long postId) {
        PostEntity olderPost = postRepository
                .findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found with id " + postId));
        inputDTO.setId(postId);
        modelMapper.map(inputDTO, olderPost);
        PostEntity savedPostEntity = postRepository.save(olderPost);
        return modelMapper.map(savedPostEntity, PostDTO.class);

    }
}
