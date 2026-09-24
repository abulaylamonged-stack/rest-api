package rest_api;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import rest_api.model.Post;
import rest_api.repository.PostRepository;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PostRepository postRepository;

    @BeforeEach
    void setUp() {
        postRepository.deleteAll();

        Post zebra = new Post();
        zebra.setTitle("Zebra");
        zebra.setContent("third post");
        postRepository.save(zebra);

        Post alpha = new Post();
        alpha.setTitle("Alpha");
        alpha.setContent("first post");
        postRepository.save(alpha);

        Post bravo = new Post();
        bravo.setTitle("Bravo");
        bravo.setContent("second post");
        postRepository.save(bravo);
    }

    @Test
    void getAllPosts_defaultsToTitleAscendingPageSizeFive() throws Exception {
        mockMvc.perform(get("/api/posts").with(user("user").roles("USER")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.size").value(5))
                .andExpect(jsonPath("$.data.number").value(0))
                .andExpect(jsonPath("$.data.content[0].title").value("Alpha"))
                .andExpect(jsonPath("$.data.content[1].title").value("Bravo"))
                .andExpect(jsonPath("$.data.content[2].title").value("Zebra"));
    }

    @Test
    void getAllPosts_supportsSortQueryParameters() throws Exception {
        mockMvc.perform(get("/api/posts?page=0&size=5&sort=title,asc").with(user("user").roles("USER")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.size").value(5))
                .andExpect(jsonPath("$.data.content[0].title").value("Alpha"))
                .andExpect(jsonPath("$.data.content[1].title").value("Bravo"))
                .andExpect(jsonPath("$.data.content[2].title").value("Zebra"));
    }
}
