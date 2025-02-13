package com.maorellanag.java_articles.auth.security;

import com.maorellanag.java_articles.auth.config.properties.SecurityProperties;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JwtServiceTest {

    @Mock
    private SecurityProperties securityProperties;
    @InjectMocks
    private JwtService jwtService;

    @BeforeEach
    void setup() {
        when(securityProperties.getSecretKey())
            .thenReturn("88cd2108b5347d973cf39cdf9053d7dd42704876d8c9a9bd8e2d168259d3ddf7");
        when(securityProperties.getExpirationTime())
            .thenReturn(3600000L);
    }

    @Test
    void generateToken_thenExtractUsername() {
        // given
        String someUsername = "paul";

        // when
        String token = jwtService.generateToken(someUsername);
        String actualUsername = jwtService.extractUsername(token);

        // then
        assertEquals(someUsername, actualUsername);
    }
}