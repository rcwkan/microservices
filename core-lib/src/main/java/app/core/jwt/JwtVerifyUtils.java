package app.core.jwt;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SecurityException;
import io.jsonwebtoken.security.SignatureException;

public class JwtVerifyUtils {

	Logger LOGGER = LogManager.getLogger(JwtVerifyUtils.class);

	private static PublicKey publicKey;

	private String publicKeyPath;

	private String publicKeyClasspath = "key.der";

	private String keyAlgo = "RSA";

	public boolean isVaildToken(String jwt) {
		try {
			Jwts.parser().verifyWith(this.getPublicKey()).build().parse(jwt);
			return true;
		} catch (ExpiredJwtException   | MalformedJwtException |SecurityException |  IllegalArgumentException e) {
			LOGGER.info(e.getMessage(), e);
		}
		return false;

	}

	public Claims getClaims(String jwt) {

		Jws<Claims> claims = Jwts.parser().verifyWith(this.getPublicKey()).build().parseSignedClaims(jwt);

		return claims.getPayload();

	}

	PublicKey getPublicKey() {

		// Read Public Key.
		File publicKeyFile;
		if (publicKeyPath != null) {
			publicKeyFile = new File(publicKeyPath);
		} else {
			ClassLoader classLoader = JwtUtils.class.getClassLoader();
			publicKeyFile = new File(classLoader.getResource(publicKeyClasspath).getFile());

		}
		FileInputStream fis;
		try {
			fis = new FileInputStream(publicKeyFile);

			byte[] encodedPublicKey = new byte[(int) publicKeyFile.length()];
			fis.read(encodedPublicKey);
			fis.close();

			KeyFactory keyFactory = KeyFactory.getInstance(keyAlgo);
			X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(encodedPublicKey);
			JwtVerifyUtils.publicKey = keyFactory.generatePublic(publicKeySpec);

			return publicKey;

		} catch (IOException | NoSuchAlgorithmException | InvalidKeySpecException e) {
			LOGGER.info(e.getMessage(), e);
		}

		return null;

	}

}
