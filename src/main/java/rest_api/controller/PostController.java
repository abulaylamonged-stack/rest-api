package rest_api.controller;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rest_api.model.ApiResponse;
import rest_api.model.Post;
import rest_api.service.PostService;

@RestController
@RequestMapping("/api/posts")
@CrossOrigin(origins = "*")
public class PostController {

    private final PostService service;

    public PostController(PostService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Page<Post>>> getAllPosts(
            @PageableDefault(page = 0, size = 5, sort = "title", direction = Sort.Direction.ASC)
            Pageable pageable) {
        return ResponseEntity.ok(new ApiResponse<>(
                true, "Posts retrieved successfully", service.getAllPosts(pageable)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Post>> getPost(@PathVariable Long id) {
        return service.getPostById(id)
                .map(post -> ResponseEntity.ok(
                        new ApiResponse<>(true, "Post retrieved successfully", post)))
            .orElseThrow(() -> new ResourceNotFoundException("Post not found"));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Post>> createPost(@Valid @RequestBody Post post) {
        return ResponseEntity.ok(new ApiResponse<>(true, "Post created successfully", service.createPost(post)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Post>> updatePost(
            @PathVariable Long id,
            @Valid @RequestBody Post post) {

        return service.updatePost(id, post)
                .map(updatedPost -> ResponseEntity.ok(
                        new ApiResponse<>(true, "Post updated successfully", updatedPost)))
            .orElseThrow(() -> new ResourceNotFoundException("Post not found"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
        if (service.deletePost(id)) {
            return ResponseEntity.noContent().build();
        }

        throw new ResourceNotFoundException("Post not found");
    }
}