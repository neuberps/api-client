package com.ms.client.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ms.client.ClientApplicationTests;
import com.ms.client.dto.ClientDTO;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@SpringBootTest
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ClientControllerTests extends ClientApplicationTests {

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private MockMvc mockMvc;

    private String id;

    @Autowired
    private ClientController controller;

    @BeforeEach
    public void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        this.id = "65f1e370e2294445ec27eafa";
    }

    @Test
    @Order(1)
    public void testCreate() throws Exception {
        log.info("testCreate");
        ClientDTO clientDTO = new ClientDTO(id,"Neuber", "neuber.paiva@gmail.com", "9994545429", "440120165656");
        this.mockMvc.perform( MockMvcRequestBuilders
                        .post("/api/clients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(clientDTO))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
                .andReturn();
    }

    @Test
    @Order(2)
    public void testFindAll() throws Exception {
        log.info("testFindAll");
        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/clients"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isNotEmpty());
    }

    @Test
    @Order(3)
    public void testFindById() throws Exception {
        log.info("testFindById");
        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/clients/getId/" + id))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists());;
    }
    @Test
    @Order(4)
    public void testFindByEmail() throws Exception {
        log.info("testFindByEmail");
        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/clients/getEmail/neuber.paiva@gmail.com"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").exists());;
    }

    @Test
    @Order(5)
    public void testUpdate() throws Exception {
        log.info("testUpdate");
        this.mockMvc.perform( MockMvcRequestBuilders
                        .put("/api/clients/" + id)
                        .content(asJsonString(new ClientDTO(null, "Maria", "maria.p@gmail.com", "24394545429", "440120165656")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists());
    }

    @Test
    @Order(6)
    public void testDelete() throws Exception {
        log.info("testDelete");
        this.mockMvc.perform( MockMvcRequestBuilders
                        .delete("/api/clients/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").doesNotExist());
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @ComponentScan(basePackages = {"com.ms.client.repository"})
    @SpringBootApplication
    public static class ClientApplication {

        public static void main(String[] args) {
            SpringApplication.run(ClientApplication.class, args);
        }

    }
}
