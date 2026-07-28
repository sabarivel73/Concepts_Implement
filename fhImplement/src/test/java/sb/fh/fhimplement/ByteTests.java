package sb.fh.fhimplement;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@AutoConfigureMockMvc
public class ByteTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @Order(1)
    public void postTesting() throws Exception {
        Path path = Paths.get("C:\\Files\\Concepts_Implement\\fhImplement\\src\\test\\java\\sb\\fh\\fhimplement\\img.png");
        MockMultipartFile file = new MockMultipartFile(
                "file",
                "img.png",
                "image/png",
                Files.readAllBytes(path)
        );
        mockMvc.perform(multipart("/byte/post")
                        .file(file))
                .andExpect(status().isCreated());
    }

    @Test
    @Order(2)
    public void getTesting() throws Exception {
        mockMvc.perform(get("/byte/get")
                        .param("fileName", "img.png"))
                .andExpect(status().isOk());
    }

    @Test
    @Order(3)
    public void downloadTesting() throws Exception {
        mockMvc.perform(get("/byte/download")
                        .param("fileName", "img.png"))
                .andExpect(status().isOk());
    }

    @Test
    @Order(4)
    public void deleteTesting() throws Exception {
        mockMvc.perform(delete("/byte/delete")
                        .param("fileName", "img.png"))
                .andExpect(status().isOk());
    }

}
