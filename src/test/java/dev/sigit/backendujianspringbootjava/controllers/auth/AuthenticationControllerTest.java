package dev.sigit.backendujianspringbootjava.controllers.auth;

import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import dev.sigit.backendujianspringbootjava.dto.SignInRequest;
import dev.sigit.backendujianspringbootjava.dto.SignInResponse;
import net.minidev.json.parser.JSONParser;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.*;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.flash;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AuthenticationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private static final StringBuilder JWT_ADMIN = new StringBuilder();

    private static final String JWT_INVALID_FORMAT = "eyJhbGciOizI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJrYW5nYWRtaW5AZ21haWwuY29tIiwiaWF0IjoxNzExODIwNTI1LCJleHAiOjE3MTE5MDY5MjV9.kavpKWnKmfvLVMetBVDA2e6AvuK5B8j94S48-Ghxqz0";

    private static final String JWT_EXPIRED = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJrYW5nYWRtaW5AZ21haWwuY29tIiwiaWF0IjoxNzExODIwNzEyLCJleHAiOjE3MTE4MjA3MTN9.r5AN92QXI6FdnJDYhxigfSd8NIONMxibPOTZs5t0vuw";

    @Test
    @Order(1)
    void testLoginAdminShouldReturnOk() throws Exception {
        SignInRequest signInRequest = new SignInRequest();
        signInRequest.setEmailOrIdPes("kangadmin@gmail.com");
        signInRequest.setPassword("qwerty");

        String requestBody = objectMapper.writeValueAsString(signInRequest);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/signin")
                .contentType("application/json")
                .content(requestBody))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        String respBody = result.getResponse().getContentAsString();
        SignInResponse signInResponse = objectMapper.readValue(respBody, SignInResponse.class);
        JWT_ADMIN.append(signInResponse.getToken());

        this.writeLocalStorage("/src/main/resources/local_storage.json", JWT_ADMIN.toString());

    }

    @Test
    @Order(2)
    void testJwtAdminShouldReturnOk() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/admin")
                .contentType("application/json")
                .header("Authorization", "Bearer " + JWT_ADMIN.toString()))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @Order(3)
    void testJwtInvalid(){
        Exception exception = assertThrows(JWTDecodeException.class, () -> {
            mockMvc.perform(MockMvcRequestBuilders.get("/api/admin")
                    .contentType("application/json")
                    .header("Authorization", "Bearer " + JWT_INVALID_FORMAT))
                    .andExpect(status().isUnauthorized())
                    .andDo(print());
        });

        assertNotNull(exception.getMessage());
    }

    @Test
    @Order(4)
    void testJwtExpired(){
        Exception exception = assertThrows(TokenExpiredException.class, () -> {
            mockMvc.perform(MockMvcRequestBuilders.get("/api/admin")
                            .contentType("application/json")
                            .header("Authorization", "Bearer " + JWT_EXPIRED))
                    .andExpect(status().isUnauthorized())
                    .andDo(print());
        });

        assertNotNull(exception.getMessage());
    }

    @Test
    @Order(5)
    void testLocalStorage() throws FileNotFoundException {
        System.out.println("ADMIN Token : " + this.readTokenFromLocalStorage("/src/main/resources/local_storage.json", "jwt_admin"));
    }

    private void writeLocalStorage(String path, String token) throws JSONException, IOException {
        File currentDirFile = new File("");
        String helper = currentDirFile.getAbsolutePath();

        JSONObject object = new JSONObject();
        object.put("jwt_admin", token);

        FileWriter fileWriter = new FileWriter(helper + path);
        fileWriter.write(object.toString());

        fileWriter.flush();
        fileWriter.close();
    }

    private String readTokenFromLocalStorage(String path, String key) throws FileNotFoundException {
        File currentDirFile = new File("");
        String helper = currentDirFile.getAbsolutePath();

        FileReader reader = new FileReader(helper + path);
        JsonObject jsonObject = JsonParser.parseReader(reader).getAsJsonObject();

        return jsonObject.get(key).getAsString();
    }
}