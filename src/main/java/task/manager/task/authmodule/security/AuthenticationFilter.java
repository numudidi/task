package task.manager.task.authmodule.security;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.google.gson.Gson;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import task.manager.task.authmodule.Utils.JwtUtils;
import task.manager.task.authmodule.dtos.payload.request.LoginPayloadDto;
import task.manager.task.authmodule.dtos.payload.response.LoginResponse;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;


public class AuthenticationFilter  extends UsernamePasswordAuthenticationFilter {
    private final JwtUtils jwtUtils;


    public AuthenticationFilter(AuthenticationManager authenticationManager, JwtUtils jwtUtils) {
        super(authenticationManager);
        this.jwtUtils = jwtUtils;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res) throws AuthenticationException {
        try {
            LoginPayloadDto credentials = new ObjectMapper().readValue(req.getInputStream(), LoginPayloadDto.class);

            return getAuthenticationManager().authenticate(
                    new UsernamePasswordAuthenticationToken(
                            credentials.getEmail(),
                            credentials.getPassword(),
                            new ArrayList<>()
                    )
            );

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain, Authentication auth) throws IOException{

        String token = jwtUtils.generateToken(auth);

         LoginResponse authResponse = LoginResponse.builder()
                .accessToken(token)
                .build();

        res.setContentType("application/json");

        // write the JSON response to the response body
        PrintWriter out = res.getWriter();
        out.print(new Gson().toJson(authResponse));
        out.flush();

    }
}
