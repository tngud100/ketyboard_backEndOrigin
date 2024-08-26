package com.example.keyboard.config.Filter;

import com.example.keyboard.config.JWT.JWTUtil;
import com.example.keyboard.config.Redis.RedisUtils;
import com.example.keyboard.entity.jwt.CustomUserDetails;
import com.example.keyboard.entity.member.MemberEntity;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Map;

public class JWTFilter extends OncePerRequestFilter {

    private final JWTUtil jwtUtil;
    private final RedisUtils redisUtils;

    // JWTUtil과 RedisUtils를 주입받는 생성자
    public JWTFilter(JWTUtil jwtUtil, RedisUtils redisUtils) {
        this.jwtUtil = jwtUtil;
        this.redisUtils = redisUtils;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 1. Authorization 헤더에서 토큰을 추출
        String authorization = request.getHeader("Authorization");

        // 2. Authorization 헤더가 존재하지 않거나 Bearer로 시작하지 않으면 필터를 통과시키고 메서드를 종료
        if (authorization == null || !authorization.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // 3. "Bearer " 부분을 제거하고 실제 토큰만 추출
        String[] parts = authorization.split(" ");
        if (parts.length < 2 || parts[1].trim().isEmpty()) {
            // 헤더 포맷이 잘못된 경우 에러 처리
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid Authorization header format");
            return;
        }
        String token = parts[1];

        // 4. 블랙리스트에 포함된 토큰인지 확인 (로그아웃된 토큰일 경우)
        if (redisUtils.isTokenBlacklisted(token)) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Your token is already logged out");
            return;
        }

        // 5. 토큰이 유효한지 확인 (만료 여부 포함)
        if (token == null || token.trim().isEmpty()) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid token");
            return;
        }

        // 6. 토큰이 만료되었는지 확인하고, 만료된 경우 리프레시 토큰을 통해 새로운 액세스 토큰 발급
        if (jwtUtil.isExpired(token)) {
            String refreshToken = request.getHeader("Refresh-Token");
            if (refreshToken != null && jwtUtil.validateRefreshToken(refreshToken)) {
                // Redis에서 리프레시 토큰 데이터를 가져옴
                String refreshTokenDTO = redisUtils.getData(refreshToken);

                ObjectMapper objectMapper = new ObjectMapper();
                Map<String, String> dataMap = objectMapper.readValue(refreshTokenDTO, new TypeReference<Map<String, String>>() {
                });

                String userId = dataMap.get("userId");
                String role = dataMap.get("role");

                // 새 액세스 토큰 생성
                String newAccessToken = jwtUtil.createAccessToken(userId, role, 60 * 60 * 100 * 10L);
                response.addHeader("Authorization", "Bearer " + newAccessToken);
                token = newAccessToken;
            } else {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid refresh token");
                return;
            }
        }

        // 7. 토큰에서 userId와 role을 추출
        String userId;
        String role;
        try {
            userId = jwtUtil.getUserId(token);
            role = jwtUtil.getRole(token);
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid token");
            return;
        }

        // 8. 추출된 정보를 바탕으로 사용자 엔티티 생성
        MemberEntity userEntity = new MemberEntity();
        userEntity.setLOGIN_ID(userId);
        userEntity.setPASSWORD("temppassword"); // 패스워드는 임시 값으로 설정
        userEntity.setROLE(role);

        // 9. CustomUserDetails 객체 생성
        CustomUserDetails customUserDetails = new CustomUserDetails(userEntity);

        // 10. 스프링 시큐리티 인증 토큰 생성
        Authentication authToken = new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());

        // 11. SecurityContextHolder에 인증 정보 설정
        SecurityContextHolder.getContext().setAuthentication(authToken);

        // 12. 다음 필터로 요청을 전달
        filterChain.doFilter(request, response);
    }
}