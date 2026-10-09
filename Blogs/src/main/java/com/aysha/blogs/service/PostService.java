package com.aysha.blogs.service;

import com.aysha.blogs.dto.PostRequest;
import com.aysha.blogs.entity.Post;
import com.aysha.blogs.repository.PostRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class PostService {
    private  final PostRepository postRepository;
    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public Post createPost(PostRequest request) {
        UUID id = UUID.randomUUID();
        Post post = new Post();
        post.setId(id);

        post.setTitle(request.getTitle());
        post.setContent(request.getContent());
        post.setCategory(request.getCategory());
        post.setTags(request.getTags());
        post.setCreatedAt(LocalDateTime.now());
        post.setUpdatedAt(LocalDateTime.now());

        return postRepository.save(post);

    }
    public Post getPostById(UUID id) {
        return postRepository.findById(id).orElse(null);
    }
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    public Post updatePost(UUID id, PostRequest request) {
        Post post = getPostById(id);
        if (post == null) {
            return null;
        }
        post.setTitle(request.getTitle());
        post.setContent(request.getContent());
        post.setCategory(request.getCategory());
        post.setTags(request.getTags());
        post.setUpdatedAt(LocalDateTime.now());
        return postRepository.save(post);
    }

    public boolean deletePost(UUID id) {
        Post post = getPostById(id);
        if (post == null) {
            return false;
        }
        else {
            postRepository.deleteById(id);
            return true;
        }
    }
    public List<Post> searchPosts(String query) {
        return postRepository.findByTitleContainingIgnoreCaseOrContentContainingIgnoreCaseOrCategoryContainingIgnoreCase(query, query, query);
    }



}
