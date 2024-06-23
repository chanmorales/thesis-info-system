package com.portfolio.mutex.tims.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Jwts.SIG;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class JwtService {

  @Value("${security.jwt.secret-key}")
  private String secretKey;

  @Value("${security.jwt.expiration-time}")
  private long jwtExpiration;

  /**
   * Extracts username from the token
   *
   * @param token the JWT token
   * @return the username
   */
  public String extractUsername(String token) {
    return extractClaim(token, Claims::getSubject);
  }

  /**
   * Generates JWT token for the user
   *
   * @param userDetails the details of the user the generated token is for
   * @return JWT token
   */
  public String generateToken(UserDetails userDetails) {
    return generateToken(new HashMap<>(), userDetails);
  }

  /**
   * Retrieve the duration of the expiration of the JWT token
   *
   * @return duration / expiration time
   */
  public long getExpirationTime() {
    return jwtExpiration;
  }

  /**
   * Checks if the provided JWT token of the user is valid
   *
   * @param token       the JWT token
   * @param userDetails the details of the user
   * @return <b>true</b> if token is valid, otherwise, <b>false</b>
   */
  public boolean isTokenValid(String token, UserDetails userDetails) {
    final String username = extractUsername(token);
    return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
  }

  /**
   * Extract a claim from a token
   *
   * @param token          the JWT token
   * @param claimsResolver the function on how to get / resolve a claim from the token
   * @param <T>            the type of claim
   * @return the claim value
   */
  private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
    final Claims claims = extractAllClaims(token);
    return claimsResolver.apply(claims);
  }

  /**
   * Extract all claims from the token
   *
   * @param token the JWT token
   * @return all claims present in the JWT token
   */
  private Claims extractAllClaims(String token) {
    return Jwts.parser()
        .verifyWith(getSecretKey())
        .build()
        .parseSignedClaims(token)
        .getPayload();
  }

  /**
   * Generate token with extra claims (if there are any) for the user
   *
   * @param extraClaims the extra claims
   * @param userDetails the user
   * @return generated JWT token
   */
  private String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
    return buildToken(extraClaims, userDetails, jwtExpiration);
  }

  /**
   * Retrieves the secret key
   *
   * @return the secret key
   */
  private SecretKey getSecretKey() {
    return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
  }

  /**
   * Builds the JWT token
   *
   * @param extraClaims the extra claims
   * @param userDetails the user
   * @param expiration  the token expiration
   * @return the JWT token
   */
  private String buildToken(Map<String, Object> extraClaims, UserDetails userDetails,
      long expiration) {
    return Jwts.builder()
        .claims(extraClaims)
        .subject(userDetails.getUsername())
        .issuedAt(new Date(System.currentTimeMillis()))
        .expiration(new Date(System.currentTimeMillis() + expiration))
        .signWith(getSecretKey(), SIG.HS256)
        .compact();
  }

  /**
   * Checks if token is already expired
   *
   * @param token the JWT token to be verified
   * @return <b>true</b> if token is already expired, otherwise, <b>false</b>
   */
  private boolean isTokenExpired(String token) {
    return extractExpiration(token).before(new Date());
  }

  /**
   * Extract expiration from the token
   *
   * @param token the token
   * @return the expiration of the token
   */
  private Date extractExpiration(String token) {
    return extractClaim(token, Claims::getExpiration);
  }
}
