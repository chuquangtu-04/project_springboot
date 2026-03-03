package com.hkgroup.identity_service.service;

import com.hkgroup.identity_service.dto.request.AuthenticationRequest;
import com.hkgroup.identity_service.dto.request.IntrospectRequest;
import com.hkgroup.identity_service.dto.response.ApiResponse;
import com.hkgroup.identity_service.dto.response.AuthenticationResponse;
import com.hkgroup.identity_service.dto.response.IntrospectResponse;
import com.hkgroup.identity_service.dto.response.UserResponse;
import com.hkgroup.identity_service.entity.User;
import com.hkgroup.identity_service.exception.AppException;
import com.hkgroup.identity_service.exception.ErrorCode;
import com.hkgroup.identity_service.mapper.UserMapper;
import com.hkgroup.identity_service.repository.UserRepository;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.AccessLevel;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.StringJoiner;
import java.util.logging.Handler;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationService {
    private static final Logger log = LoggerFactory.getLogger(AuthenticationService.class);
    UserRepository userRepository;
    UserMapper userMapper;
    @NonFinal
    @Value("${jwt.signerKey}")
    protected String SIGNER_KEY;

    public IntrospectResponse introspect(IntrospectRequest request) {
        var token = request.getToken();
        try {
            JWSVerifier verifier = new MACVerifier(SIGNER_KEY.getBytes());
            SignedJWT signedJWT = SignedJWT.parse(token);
            Date expiryTime = signedJWT.getJWTClaimsSet().getExpirationTime();
            if(!expiryTime.after(new Date())) {
                throw new AppException(ErrorCode.TOKEN_EXPIRED);
            }
            var verified = signedJWT.verify(verifier);
            return IntrospectResponse.builder()
                    .valid(verified)
                    .build();
        } catch (JOSEException e) {
            log.error("Can not verifier token: "+e);
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public AuthenticationResponse authenticated(AuthenticationRequest request) {
        var user = userRepository.findByuserName(request.getUserName())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        boolean result = passwordEncoder.matches(request.getPassword(), user.getPassword());
        if(!result) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }
        var token = generateToken(user);
        return AuthenticationResponse.builder()
                .token(token)
                .authenticated(true)
                .build();
    }
    private String generateToken(User user) {
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);
        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(user.getUserName())
                .issuer("chuquangtu.com")
                .issueTime(new Date())
                .expirationTime(new Date(
                        Instant.now().plus(1, ChronoUnit.HOURS).toEpochMilli()
                ))
                .claim("scope", buildScope(user))
                .build();
        Payload payload = new Payload(jwtClaimsSet.toJSONObject());
        JWSObject jwsObject = new JWSObject(header, payload);
        try {
            jwsObject.sign(new MACSigner(SIGNER_KEY));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            log.error("Can not create token: ", e);
            throw new RuntimeException(e);
        }
    }
    private String buildScope(User user) {
        StringJoiner stringJoiner = new StringJoiner(" ");
        if(!CollectionUtils.isEmpty(user.getRole())) {
            user.getRole().forEach(stringJoiner::add);
        }
        return stringJoiner.toString();
    }
}
