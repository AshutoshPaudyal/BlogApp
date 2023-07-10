package com.blogapp.blog.app.security;


import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenHelper jwtTokenHelper;
    private final UserDetailsService userDetailsService;
    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {

        String requestToken = request.getHeader("Authorization");

        //Bearer 66537673BC
        System.out.println(requestToken);

         String username = null ;
         String token  = null;

        if (requestToken != null && requestToken.startsWith("Bearer")){

            token = requestToken.substring(7);
            try {
                username = jwtTokenHelper.getUsernameFromToken(token);
            }catch (IllegalArgumentException e){
                System.out.println("Unable to get Token");
            }catch (ExpiredJwtException e){
                System.out.println("Jwt Token has expired");
            }catch (MalformedJwtException e){
                System.out.println("Invalid jwt");
            }
        }else {
            System.out.println("Jwt does not begin with Bearer");
        }
//        if(requestToken == null || !requestToken.startsWith("Bearer ")){ //authHeader should always start with Bearer
//            filterChain.doFilter(request,response);
//            return;
//        }
//        token = requestToken.substring(7);    //extract jwt token from Authorization Header
//
//        username = jwtTokenHelper.getUsernameFromToken(token);


        //once we get token , we validate
        if(username !=null && SecurityContextHolder.getContext().getAuthentication() == null){

            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            //validate Token
            if(jwtTokenHelper.validateToken(token,userDetails)){

                //if token is valid then authenticate
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
            else {
                System.out.println("Invalid jwt token");
            }
        }
        else{
            System.out.println("User is null and SecurityContextHolder is not null");
        }

        filterChain.doFilter(request,response);
    }
}
