/*
 * Copyright 2006 Web Cohesion Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES
 * OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

package com.webcohesion.enunciate.sample.rs;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import com.webcohesion.enunciate.sample.model.Person;
import com.webcohesion.enunciate.sample.ws.PermissionDeniedException;

/**
 * @author Ryan Heaton
 */
@Path("/persons")
@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
public class PersonsEndpoint {

  @GET
  @Path("/{personId}")
  public Person readPerson(@PathParam("personId") String personId) {
    final Person person = new Person();
    // ...load the persona from the db, etc...
    return person;
  }

  @POST
  public void createPerson(Person person) {
    // ... store the persona in the db...
  }

  @DELETE
  @Path("/{personId}")
  public boolean deletePersona(@PathParam("personId")  String personaId) {
    // ..do the delete, throw permission denied as necessary...
    return true;
  }
}
