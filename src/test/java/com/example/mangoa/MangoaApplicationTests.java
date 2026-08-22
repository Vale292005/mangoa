package com.example.mangoa;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(properties = {
    "application.security.jwt.secret-key=404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970",
    "application.security.jwt.expiration=86400000"
})
@ActiveProfiles("test")
class MangoaApplicationTests {

	@Test
	void contextLoads() {
	}

}
