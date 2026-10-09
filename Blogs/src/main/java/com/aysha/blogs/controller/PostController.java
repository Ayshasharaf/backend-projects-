package com.aysha.blogs.controller;

import com.aysha.blogs.dto.PostRequest;
import com.aysha.blogs.entity.Post;
import com.aysha.blogs.service.PostService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/posts")
public class PostController {
    private final PostService postService;
    public PostController(PostService postService) {
        this.postService = postService;
    }
    @PostMapping
    public ResponseEntity<Post> createPosts(@RequestBody PostRequest request) {
        Post saved = postService.createPost(request);
        return ResponseEntity.status(201).body(saved);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Post> getPostById(@PathVariable UUID id) {
        Post post = postService.getPostById(id);
        if (post == null) {
            return ResponseEntity.status(404).body(null);
        }
        else {
            return ResponseEntity.status(200).body(post);
        }
    }

    @GetMapping
    public ResponseEntity<List<Post>> getAllPosts(@RequestParam(required = false) String term) {
        if (term != null && !term.isBlank()) {
            List<Post> posts = postService.searchPosts(term);
            return ResponseEntity.status(200).body(posts);
        }
        List<Post> posts = postService.getAllPosts();
        return ResponseEntity.status(200).body(posts);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Post> updatePost(@PathVariable UUID id, @RequestBody PostRequest request) {
        Post updated = postService.updatePost(id, request);
        if (updated == null) {
            return ResponseEntity.status(404).body(null);
        }
        else {
            return ResponseEntity.status(200).body(updated);
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable UUID id) {
        boolean deleted = postService.deletePost(id);
        if (!deleted) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.status(204).build();
    }
  

}

