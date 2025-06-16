// tag::copyright[]
/*******************************************************************************
 * Copyright (c) 2018, 2022 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *******************************************************************************/
// end::copyright[]
package ms.user.resources;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import ms.user.dynamo.model.User;
import ms.user.models.UserAccount;
import ms.user.service.UserService;

@RequestScoped
@Path("users")
public class UserResource {

	private static final Logger log = LoggerFactory.getLogger(UserResource.class);

	@Inject
	private SecurityContext securityContext;

	@Inject
	private UserService userService;

	@GET
	@Path("me")
	@Produces(MediaType.APPLICATION_JSON)
	@RolesAllowed({ "admin", "user" })
	@Transactional
	public JsonObject getMe() {
		
		log.info("getMe: {}" , securityContext.getUserPrincipal().getName());
		JsonObjectBuilder builder = Json.createObjectBuilder();
		 
		List<User> users = userService.findUser(securityContext.getUserPrincipal().getName());
		if (users != null && users.size() == 1) {
			
			User user = users.get(0);
			builder.add("username", user.getUsername()).add("displayName", user.getDisplayName())
					.add("createDate", user.getCreateDate().getTime()).add("id", user.getUserId());
		}
		return builder.build();
	}

	/**
	 * This method updates a new User from the submitted data by the user.
	 */
	@PUT
	@Path("{id}")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@RolesAllowed({ "admin", "user" })
	@Transactional
	public Response updateUser(@FormParam("username") String username, @FormParam("displayName") String displayName,
			@FormParam("email") String email, @PathParam("id") String id) {
		User prevUser = userService.readUser(id);
		if (prevUser == null) {
			return Response.status(Response.Status.NOT_FOUND).entity("User does not exist").build();
		}
		if (!userService.findUser(username).isEmpty()) {
			return Response.status(Response.Status.BAD_REQUEST).entity("User already exists").build();
		}
		prevUser.setUsername(username);
		prevUser.setDisplayName(displayName);
		prevUser.setEmail(email);

		userService.updateUser(prevUser);
		return Response.status(Response.Status.NO_CONTENT).build();
	}

	/**
	 * This method deletes a specific existing/stored User
	 */
	@DELETE
	@Path("{id}")
	@RolesAllowed({ "admin" })
	@Transactional
	public Response deleteUser(@PathParam("id") String userId) {
		User User = userService.readUser(userId);
		if (User == null) {
			return Response.status(Response.Status.NOT_FOUND).entity("User does not exist").build();
		}
		userService.deleteUser(User);
		return Response.status(Response.Status.NO_CONTENT).build();
	}

	/**
	 * This method returns a specific existing/stored User in Json format
	 */
	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@RolesAllowed({ "admin", "user" })
	@Transactional
	public JsonObject getUser(@PathParam("id") String userId) {
		JsonObjectBuilder builder = Json.createObjectBuilder();
		User user = userService.readUser(userId);
		if (user != null) {
			builder.add("username", user.getUsername()).add("displayName", user.getDisplayName())
					.add("createDate", user.getCreateDate().getTime()).add("id", user.getUserId());
		}
		return builder.build();
	}

	/**
	 * This method returns the existing/stored Users in Json format
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@RolesAllowed({ "admin", "user" })
	@Transactional
	public JsonArray getUsers() {
		JsonObjectBuilder builder = Json.createObjectBuilder();
		JsonArrayBuilder finalArray = Json.createArrayBuilder();
		for (User user : userService.readAllUsers()) {
			builder.add("username", user.getUsername()).add("displayName", user.getDisplayName())
					.add("createDate", user.getCreateDate().getTime()).add("id", user.getUserId());
			finalArray.add(builder.build());
		}
		return finalArray.build();
	}
}
