package dev.ynnk.m295;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.ynnk.m295.model.User;
import dev.ynnk.m295.repository.UserRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @Value("${dev.ynnk.test.oauth.user}")
    private String oauthUser;

    @Value("${dev.ynnk.test.oauth.password}")
    private String oauthPassword;

    @Value("${spring.security.oauth2.resourceserver.jwt.issuer-uri}")
    private String oauth2Url;

    private User user;

    @BeforeAll
    void setup() {
        this.user = this.userRepository.save(new User(-1L, this.oauthUser, "Test", "User"));
    }

    @Test
    public void testWhoami() throws Exception{
        String token = obtainAccessToken();

        String json = mvc.perform(get("/api/v1/user/whoami").header("Authorization", "Bearer " + token)
                        .with(csrf()))
                .andDo(print()).andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

        User user = objectMapper.readValue(json, User.class);

        assertEquals(user, this.user);
    }


    private String obtainAccessToken() {

        RestTemplate rest = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        String body = "client_id=demoapp&" +
                "grant_type=password&" +
                "scope=openid profile roles offline_access&" +
                "username=" + this.oauthUser +
                "&password=" + this.oauthPassword;

        HttpEntity<String> entity = new HttpEntity<>(body, headers);


        ResponseEntity<String> resp = rest.postForEntity(this.oauth2Url + "/protocol/openid-connect/token",
                entity, String.class);

        JacksonJsonParser jsonParser = new JacksonJsonParser();
        return jsonParser.parseMap(resp.getBody()).get("access_token").toString();
    }
}
