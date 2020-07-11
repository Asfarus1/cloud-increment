package asfarus.example.cloudincrement.web;

import asfarus.example.cloudincrement.repository.IncrementRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        properties = "spring.liquibase.enabled=false")
@ActiveProfiles("test")
@AutoConfigureMockMvc
class IncrementControllerTest {
    @MockBean
    private IncrementRepository incrementRepository;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void get() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/"))
                .andExpect(status().isOk());
        verify(incrementRepository).get();
    }

    @Test
    void inc() throws Exception {
        mockMvc.perform(post("/"))
                .andExpect(status().isOk());
        verify(incrementRepository).incrementAndGet();
    }

    @Test
    void reset() throws Exception {
        mockMvc.perform(delete("/"))
                .andExpect(status().isNoContent());
        verify(incrementRepository).reset();
    }
}