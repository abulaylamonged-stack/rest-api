package rest_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rest_api.model.Post;

public interface PostRepository extends JpaRepository<Post, Long> {
}