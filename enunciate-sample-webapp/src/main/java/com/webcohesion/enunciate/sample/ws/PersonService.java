/*
 * Copyright 2006 Web Cohesion Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES
 * OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

package com.webcohesion.enunciate.sample.ws;

import javax.jws.WebService;

import com.webcohesion.enunciate.sample.model.Person;

/**
 * @author Ryan Heaton
 */
@WebService
public class PersonService {

  public Person readPerson(String personId) {
    final Person person = new Person();
    // ...load the persona from the db, etc...
    return person;
  }

  public void createPerson(Person person) {
    // ... store the persona in the db...
  }

  public boolean deletePerson(String personaId) throws PermissionDeniedException {
    // ..do the delete, throw permission denied as necessary...
    return true;
  }
}
