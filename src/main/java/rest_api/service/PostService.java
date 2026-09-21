package rest_api.service;

import org.springframework.stereotype.Service;
import rest_api.model.Post;
import rest_api.repository.PostRepository;

import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    private final PostRepository repository;

    public PostService(PostRepository repository) {
        this.repository = repository;
    }

    public List<Post> getAllPosts() {
        return repository.findAll();
    }

    public Optional<Post> getPostById(Long id) {
        return repository.findById(id);
    }

    public Post createPost(Post post) {
        return repository.save(post);
    }

    public Optional<Post> updatePost(Long id, Post updatedPost) {
        return repository.findById(id).map(post -> {
            post.setTitle(updatedPost.getTitle());
            post.setContent(updatedPost.getContent());
            return repository.save(post);
        });
    }

    public boolean deletePost(Long id) {
        if (!repository.existsById(id)) {
            return false;
        }

        repository.deleteById(id);
        return true;
    }
}