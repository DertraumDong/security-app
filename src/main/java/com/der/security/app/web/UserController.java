package com.der.security.app.web;

import com.der.security.core.properties.SecurityDefaultProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private SecurityDefaultProperties securityDefaultProperties;

    @GetMapping("/me")
    public Object getCurrentUser(Authentication user, HttpServletRequest request) throws UnsupportedEncodingException {
        String header = request.getHeader("Authorization");
        String token = StringUtils.substringAfter(header,"bearer ");
        Claims claims = Jwts.parser().setSigningKey(securityDefaultProperties.getOauth2().getJwtSigningKey().getBytes("UTF-8")).parseClaimsJws(token).getBody();
        String name = (String) claims.get("company");
        return user;
    }

}
