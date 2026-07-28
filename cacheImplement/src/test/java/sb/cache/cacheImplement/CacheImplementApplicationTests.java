package sb.cache.cacheImplement;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CacheImplementApplicationTests {
    @Autowired
    private MockMvc mockMvc;
    @Test
    public void fun() throws Exception {
        mockMvc.perform(post("/api/post")
                .param("name", "Sabari")
                .param("email", "sabari@gmail.com"))
                .andExpect(status().isOk());
    }
}
