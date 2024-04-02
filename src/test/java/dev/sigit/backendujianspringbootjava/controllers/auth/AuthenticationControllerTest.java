package dev.sigit.backendujianspringbootjava.controllers.auth;

import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import dev.sigit.backendujianspringbootjava.dto.SignInRequest;
import dev.sigit.backendujianspringbootjava.dto.SignInResponse;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ComponentScan
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AuthenticationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;
    private static final StringBuilder JWT_ADMIN = new StringBuilder();
    private static final StringBuilder JWT_GURU = new StringBuilder();
    private static final StringBuilder JWT_SISWA = new StringBuilder();

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

        this.writeLocalStorage();
    }

    @Test
    @Order(2)
    void testLoginAdminBodyNoneShouldReturnForbidden() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/signin")
                        .contentType("application/json"))
                .andExpect(status().isForbidden())
                .andDo(print());
    }

    @Test
    @Order(3)
    void testLoginAdminBodyNullShouldReturnBadRequest() throws Exception {
        SignInRequest signInRequest = new SignInRequest();
        signInRequest.setEmailOrIdPes(null);
        signInRequest.setPassword(null);

        String requestBody = objectMapper.writeValueAsString(signInRequest);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/signin")
                        .contentType("application/json")
                        .content(requestBody))
                .andExpect(status().isBadRequest())
                .andDo(print())
                .andReturn();

        String jsonResponse = result.getResponse().getContentAsString();

        JsonObject jsonObject = new Gson().fromJson(jsonResponse, JsonObject.class);
        if (jsonObject.has("errors")){
            JsonObject errors = jsonObject.getAsJsonObject("errors");

            if (errors.has("emailOrIdPes")){
                String[] emailOrIdPesErrors = new Gson().fromJson(errors.get("emailOrIdPes"), String[].class);
                for (String actualMessage: emailOrIdPesErrors){
                    String expectedMessage = "Email Atau ID Peserta Kosong!";
                    assertTrue(actualMessage.contains(expectedMessage));
                }
            }

            if (errors.has("password")){
                String[] passwordErrors = new Gson().fromJson(errors.get("password"), String[].class);
                for (String actualMessage: passwordErrors){
                    String expectedMessage = "Password Kosong!";
                    assertTrue(actualMessage.contains(expectedMessage));
                }
            }
        }
    }

    @Test
    @Order(4)
    void testLoginAdminBodyEmptyShouldReturnBadRequest() throws Exception {
        SignInRequest signInRequest = new SignInRequest();
        signInRequest.setEmailOrIdPes("");
        signInRequest.setPassword("");

        String requestBody = objectMapper.writeValueAsString(signInRequest);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/signin")
                        .contentType("application/json")
                        .content(requestBody))
                .andExpect(status().isBadRequest())
                .andDo(print())
                .andReturn();

        String jsonResponse = result.getResponse().getContentAsString();

        JsonObject jsonObject = new Gson().fromJson(jsonResponse, JsonObject.class);
        if (jsonObject.has("errors")){
            JsonObject errors = jsonObject.getAsJsonObject("errors");

            if (errors.has("emailOrIdPes")){
                String[] emailOrIdPesErrors = new Gson().fromJson(errors.get("emailOrIdPes"), String[].class);
                for (String actualMessage: emailOrIdPesErrors){
                    String expectedMessage = "Email Atau ID Peserta Kosong!";
                    assertTrue(actualMessage.contains(expectedMessage));
                }
            }

            if (errors.has("password")){
                String[] passwordErrors = new Gson().fromJson(errors.get("password"), String[].class);
                for (String actualMessage: passwordErrors){
                    String expectedMessage = "Password Kosong!";
                    assertTrue(actualMessage.contains(expectedMessage));
                }
            }
        }
    }

    @Test
    @Order(5)
    void testJwtAdminShouldReturnOk() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/admin")
                        .contentType("application/json")
                        .header("Authorization", "Bearer " + JWT_ADMIN.toString()))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @Order(6)
    void testJwtAdminShouldReturnForbidden() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/guru")
                        .contentType("application/json")
                        .header("Authorization", "Bearer " + JWT_ADMIN.toString()))
                .andExpect(status().isForbidden())
                .andDo(print());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/siswa")
                        .contentType("application/json")
                        .header("Authorization", "Bearer " + JWT_ADMIN.toString()))
                .andExpect(status().isForbidden())
                .andDo(print());
    }

    @Test
    @Order(7)
    void testJwtInvalidShouldReturnUnauthorized() {
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
    @Order(8)
    void testJwtExpiredShouldReturnUnauthorized() {
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
    @Order(9)
    void testLocalStorage() throws FileNotFoundException {
        System.out.println("ADMIN Token : " + this.readTokenFromLocalStorage("jwt_admin"));
    }

    @Test
    @Order(10)
    void testLoginGuruShouldReturnOk() throws Exception {
        SignInRequest signInRequest = new SignInRequest();
        signInRequest.setEmailOrIdPes("kangguru@gmail.com");
        signInRequest.setPassword("ytrewq");

        String requestBody = objectMapper.writeValueAsString(signInRequest);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/signin")
                        .contentType("application/json")
                        .content(requestBody))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        String respBody = result.getResponse().getContentAsString();
        SignInResponse signInResponse = objectMapper.readValue(respBody, SignInResponse.class);
        JWT_GURU.append(signInResponse.getToken());

        this.writeLocalStorage();
    }

    @Test
    @Order(11)
    void testLoginSiswaShouldReturnOk() throws Exception {
        SignInRequest signInRequest = new SignInRequest();
        signInRequest.setEmailOrIdPes("SAJ-2003-33399");
        signInRequest.setPassword("qwertyu");

        String requestBody = objectMapper.writeValueAsString(signInRequest);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/signin")
                        .contentType("application/json")
                        .content(requestBody))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        String respBody = result.getResponse().getContentAsString();
        SignInResponse signInResponse = objectMapper.readValue(respBody, SignInResponse.class);
        JWT_SISWA.append(signInResponse.getToken());

        this.writeLocalStorage();
    }

    private void writeLocalStorage() throws JSONException, IOException {
        File currentDirFile = new File("");
        String helper = currentDirFile.getAbsolutePath();

        JSONObject object = new JSONObject();
        object.put("jwt_admin", JWT_ADMIN.toString());
        object.put("jwt_guru", JWT_GURU.toString());
        object.put("jwt_siswa", JWT_SISWA.toString());

        JSONArray array = new JSONArray();
        array.put(object);

        FileWriter fileWriter = new FileWriter(helper + "/src/main/resources/local_storage.json");
        fileWriter.write(array.toString(4));

        fileWriter.flush();
        fileWriter.close();
    }

    private String readTokenFromLocalStorage(String key) throws FileNotFoundException {
        File currentDirFile = new File("");
        String helper = currentDirFile.getAbsolutePath();

        FileReader reader = new FileReader(helper + "/src/main/resources/local_storage.json");
        JsonArray jsonArray = JsonParser.parseReader(reader).getAsJsonArray();

        return jsonArray.get(0).getAsJsonObject().get(key).getAsString();
    }
}