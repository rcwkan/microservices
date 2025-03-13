package app.core.jwt;

import java.io.File;
import java.io.FileInputStream;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
 

public class JwtUtils {

	private static final Logger log = LoggerFactory.getLogger(JwtUtils.class);
 
	private String keyAlgo = "PKCS12";

	private static String keyPass = "password";

	private static String keyAlias = "default";
 

	private String privateKeyPath;

	private static String privateKeyClasspath = "key.p12";

	private Long expiration = 3000l;

	private Long skewSeconds;

	private static PublicKey publicKey;
	private static PrivateKey privateKey;

	private static JwtUtils instance;

	public static JwtUtils getInstance() {

		if (instance == null) {
			instance = new JwtUtils();
		}

		return instance;
	}

	public static JwtUtils getInstance(String path, String alias, String pass) {

		privateKeyClasspath = path;
		keyPass = pass;
		keyAlias = alias;

		if (instance == null) {
			instance = new JwtUtils();
		}

		return instance;

	}

	public void init(String path, String alias, String pass) {

		privateKeyClasspath = path;
		keyPass = pass;
		keyAlias = alias;
	}

	public Claims getClaims(String token) {

		Jws<Claims> claims = Jwts.parser()

				.verifyWith(this.getPublicKey())

				.build().parseSignedClaims(token);

		return claims.getPayload();

	}

	public boolean isVaildToken(String jwt) {
		Jwts.parser().verifyWith(this.getPublicKey()).build().parse(jwt);
		return true;
	}
	
	public String generateToken(String username) {
		return generateToken(username, Arrays.asList("user"), Arrays.asList("user"));
	}

	public String generateToken(String username, List<String> groups, List<String> roles) {

		Map<String, Object> claims = new HashMap<>();

		claims.put(Claims.EXPIRATION, (System.currentTimeMillis() / 1000) + expiration); // Expire time
		claims.put(Claims.ISSUED_AT, (System.currentTimeMillis() / 1000)); // Issued time
		claims.put(Claims.ID, Long.toHexString(System.nanoTime()));// Unique value
		claims.put(Claims.SUBJECT, username); // Subject
		claims.put("upn", username); // Subject again
		claims.put(Claims.ISSUER, "microservice.app.core");
		claims.put(Claims.AUDIENCE, "ms-services");
		if (skewSeconds != null) {
			claims.put(Claims.NOT_BEFORE, skewSeconds);
		}
		
		//openliberty use groups as permission/role
		if (groups != null && !groups.isEmpty())
			claims.put("groups", groups);
		if (roles != null && !roles.isEmpty())
			claims.put("roles", roles);

		return doGenerateToken(claims, username);
	}

	private String doGenerateToken(Map<String, Object> claims, String subject) {
		final Date createdDate = new Date();
		final Date expirationDate = calculateExpirationDate(createdDate);

		JwtBuilder builder = Jwts.builder();
		builder.subject(subject).issuedAt(createdDate).expiration(expirationDate).claims().add(claims);

		return builder.signWith(getPrivateKey(), Jwts.SIG.RS256).compact();

	}

	private Date calculateExpirationDate(Date createdDate) {
		return new Date(createdDate.getTime() + expiration * 1000);
	}

	private void loadKeyPair() {
		try {

			ClassLoader classLoader = JwtUtils.class.getClassLoader();
 

			// Read Private Key.
			File filePrivateKey;
			if (privateKeyClasspath != null) {
				filePrivateKey = new File(classLoader.getResource(privateKeyClasspath).getFile());
			} else {
				filePrivateKey = new File(privateKeyPath);
			}
			FileInputStream fis = new FileInputStream(filePrivateKey);
			byte[] encodedPrivateKey = new byte[(int) filePrivateKey.length()];
			fis.read(encodedPrivateKey);
			fis.close();

			KeyStore keystore = KeyStore.getInstance(keyAlgo);
			char[] password = new String(keyPass).toCharArray();
			keystore.load(new FileInputStream(filePrivateKey), password);
			JwtUtils.privateKey = (PrivateKey) keystore.getKey(keyAlias, password);

			final Certificate cert = keystore.getCertificate(keyAlias);
			JwtUtils.publicKey = cert.getPublicKey();

		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
		}
	}

	private PrivateKey getPrivateKey() {
		if (JwtUtils.privateKey == null) {
			loadKeyPair();
		}
		return JwtUtils.privateKey;
	}

	private PublicKey getPublicKey() {
		if (JwtUtils.publicKey == null) {
			loadKeyPair();
		}
		return JwtUtils.publicKey;
	}

//	public Boolean canTokenBeRefreshed(String token, Date lastPasswordReset) {
//		final Date created = getIssuedAtDateFromToken(token);
//		return !isCreatedBeforeLastPasswordReset(created, lastPasswordReset)
//				&& (!isTokenExpired(token)  );
//	}

//	public String refreshToken(String token) {
//		final Date createdDate = new Date();
//		final Date expirationDate = calculateExpirationDate(createdDate);
//
//		final Claims claims = getAllClaimsFromToken(token);
//		 
//		 
//		return Jwts.builder().setClaims(claims).signWith(SignatureAlgorithm.forName(jwtAlgo), getPrivateKey())
//				.compact();
//	}

//	public Boolean validateToken(String token, String username) {
//
//		// log.info("validateToken token : {}, userDetails:{}", token, userDetails);
//		// JwtUser user = (JwtUser) userDetails;
//		final String tUsername = getUsernameFromToken(token);
//		final Date created = getIssuedAtDateFromToken(token);
//		// final Date expiration = getExpirationDateFromToken(token);
//		return (username.equals(tUsername) && !isTokenExpired(token));
//
//		// && !isCreatedBeforeLastPasswordReset(created,
//		// user.getLastPasswordResetDate()));
//	}

	//
//	public String getUsernameFromToken(String token) {
//		String sub = getClaimFromToken(token, Claims::getSubject);
//
//		return sub;
//	}
//
//	public Date getIssuedAtDateFromToken(String token) {
//		return getClaimFromToken(token, Claims::getIssuedAt);
//	}
//
//	public Date getExpirationDateFromToken(String token) {
//		return getClaimFromToken(token, Claims::getExpiration);
//	}

//    public String getAudienceFromToken(String token) {
//        return getClaimFromToken(token, Claims::getAudience);
//    }

//	public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
//		final Claims claims = getAllClaimsFromToken(token);
//		return claimsResolver.apply(claims);
//	}

//	public Boolean isTokenExpired(String token) {
//		final Date expiration = getExpirationDateFromToken(token);
//		return expiration.before(new Date());
//	}

//	private Boolean isCreatedBeforeLastPasswordReset(Date created, Date lastPasswordReset) {
//		return (lastPasswordReset != null && created.before(lastPasswordReset));
//	}

}