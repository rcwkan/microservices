// tag::copyright[]
/*******************************************************************************
 * Copyright (c) 2020, 2022 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *******************************************************************************/
// end::copyright[]
package app.core.jwt;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.Key;
import java.security.KeyStore;
import java.util.Base64;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.auth.PubSecKeyOptions;
import io.vertx.ext.auth.User;
import io.vertx.ext.auth.authentication.Credentials;
import io.vertx.ext.auth.authentication.TokenCredentials;
import io.vertx.ext.auth.JWTOptions;
import io.vertx.ext.auth.jwt.JWTAuth;
import io.vertx.ext.auth.jwt.JWTAuthOptions;

import io.vertx.core.json.JsonArray;

public class JwtVertxUtils {

	Logger LOGGER = LogManager.getLogger(JwtVertxUtils.class);

	private static JwtVertxUtils instance;
	private static String keystorePath = "key.p12";
	private static String keystorePass = "";
	private static String keyAlias = "default";

	private static JWTAuth provider;

	private static Vertx vertx = Vertx.vertx();

	public static JwtVertxUtils getInstance(String path, String alias, String pass) {

		if (Objects.equals(path, keystorePath) && Objects.equals(alias, keyAlias)
				&& Objects.equals(pass, keystorePass)) {
			if (instance == null)
				instance = new JwtVertxUtils(path, alias, pass);

			return instance;
		}

		instance = new JwtVertxUtils(path, alias, pass);
		return instance;

	}

	public JwtVertxUtils() {

	}

	public JwtVertxUtils(String path, String alias, String pass) {
		this.init(path, alias, pass);
	}

	public void init(String path, String alias, String pass) {

		keystorePath = path;
		keystorePass = pass;
		keyAlias = alias;

		JWTAuthOptions config;

		try {
			config = new JWTAuthOptions()
					.addPubSecKey(new PubSecKeyOptions().setAlgorithm("RS256").setBuffer(getPrivateKey()));

			provider = JWTAuth.create(vertx, config);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public boolean isVaildToken(String jwt) throws InterruptedException {

		Credentials creds = new TokenCredentials(jwt);

		LOGGER.info("isVaildToken 1 :{}", System.currentTimeMillis());
		Future<User> fUSer = provider.authenticate(creds).onSuccess(user -> {

			LOGGER.info("isVaildToken 2 :{}", System.currentTimeMillis());
//				    System.out.println(" onSuccess User: " + user.principal()))
		}).onFailure(err -> {
			LOGGER.info("isVaildToken 3 :{} {}", System.currentTimeMillis(), err);
//					System.out.println("onFailure err: " + err);
		});

		while (!fUSer.isComplete()) {
			Thread.sleep(50);
		}

		LOGGER.info("isVaildToken 4 :{}, {}", System.currentTimeMillis(), fUSer.succeeded());
		return fUSer.succeeded();

	}

	public JsonObject getPrincipal(String jwt) throws InterruptedException {

		Credentials creds = new TokenCredentials(jwt);

		Future<User> fUSer = provider.authenticate(creds)
				.onSuccess(user -> System.out.println(" onSuccess User: " + user.principal())).onFailure(err -> {

					System.out.println("onFailure err: " + err);
				});

		while (!fUSer.isComplete()) {
			Thread.sleep(100);
		}

		System.out.println("fUSer: " + fUSer.result().principal());

		return fUSer.result().principal();

	}

	public String getUsername(String token) throws InterruptedException {

		return getPrincipal(token).getString("sub");
	}

	public String createUserJwt(String username) throws GeneralSecurityException, IOException {
		Set<String> groups = new HashSet<String>();
		groups.add("user");
		return createJwt(username, groups);
	}

	public String createUserJwt(String username, Set<String> groups) throws GeneralSecurityException, IOException {
		groups.add("user");
		return createJwt(username, groups);
	}

	public String createAdminJwt(String username) throws GeneralSecurityException, IOException {
		Set<String> groups = new HashSet<String>();
		groups.add("admin");
		groups.add("user");
		return createJwt(username, groups);
	}

	private String createJwt(String username, Set<String> groups) throws IOException {

		io.vertx.core.json.JsonObject claimsObj = new JsonObject().put("exp", (System.currentTimeMillis() / 1000) + 300) // Expire
																															// time
				.put("iat", (System.currentTimeMillis() / 1000)) // Issued time
				.put("jti", Long.toHexString(System.nanoTime())) // Unique value
				.put("sub", username) // Subject
				.put("upn", username) // Subject again
				.put("iss", "microservice.app.core").put("aud", "ms-services").put("groups", getGroupArray(groups));

		String token = provider.generateToken(claimsObj, new JWTOptions().setAlgorithm("RS256"));

		return token;
	}

	private String getPrivateKey() throws IOException {
		try {

			ClassLoader classLoader = JwtVertxUtils.class.getClassLoader();
			File file = new File(classLoader.getResource(keystorePath).getFile());

			KeyStore keystore = KeyStore.getInstance("PKCS12");
			char[] password = new String(keystorePass).toCharArray();
			keystore.load(new FileInputStream(file), password);
			Key key = keystore.getKey(keyAlias, password);
			String output = "-----BEGIN PRIVATE KEY-----\n" + Base64.getEncoder().encodeToString(key.getEncoded())
					+ "\n" + "-----END PRIVATE KEY-----";
			return output;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	private JsonArray getGroupArray(Set<String> groups) {
		JsonArray arr = new JsonArray();
		if (groups != null) {
			for (String group : groups) {
				arr.add(group);
			}
		}
		return arr;
	}

 

}
