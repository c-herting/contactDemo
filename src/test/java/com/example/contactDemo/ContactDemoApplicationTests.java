/*
 *
 *  Copyright (c) 2020 Chuck Herting, 
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *
 */
package com.example.contactDemo;


import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.web.client.HttpClientErrorException;

import com.example.contactDemo.model.Contact;
import org.junit.jupiter.api.Assertions;


/**
 * @author Chuck Herting
 */
@SpringBootTest(classes = ContactDemoApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ContactDemoApplicationTests {

	@Autowired
	private TestRestTemplate restTemplate;

	@LocalServerPort
	private int port;

	private String getRootUrl() {
		return "http://localhost:" + port;
	}

	@Test
	public void contextLoads() {
	}

	@Test
	public void testGetAllUsers() {
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<String> entity = new HttpEntity<String>(null, headers);

		ResponseEntity<String> response = restTemplate.exchange(getRootUrl() + "/users",
				HttpMethod.GET, entity, String.class);

		Assertions.assertNotNull(response.getBody());
	}

	@Test
	public void testGetUserById() {
		Contact contact = restTemplate.getForObject(getRootUrl() + "/contact/1", Contact.class);
		System.out.println(contact.getFirstName());
		Assertions.assertNotNull(contact);
	}

	@Test
	public void testCreateUser() {
		Contact contact = new Contact();
		contact.setEmailAddress("test@gmail.com");
		contact.setFirstName("test");
		contact.setLastName("test");
		contact.setCreatedBy("test");
		contact.setUpdatedBy("test");

		ResponseEntity<Contact> postResponse = restTemplate.postForEntity(getRootUrl() + "/contact", contact, Contact.class);
		Assertions.assertNotNull(postResponse);
		Assertions.assertNotNull(postResponse.getBody());
	}

	@Test
	public void testUpdatePost() {
		int id = 1;
		Contact contact = restTemplate.getForObject(getRootUrl() + "/contact/" + id, Contact.class);
		contact.setFirstName("testFirst");
		contact.setLastName("testLast");

		restTemplate.put(getRootUrl() + "/contact/" + id, contact);

		Contact updatedContact = restTemplate.getForObject(getRootUrl() + "/contact/" + id, Contact.class);
		Assertions.assertNotNull(updatedContact);
	}

	@Test
	public void testDeletePost() {
		int id = 2;
		Contact contact = restTemplate.getForObject(getRootUrl() + "/contact/" + id, Contact.class);
		Assertions.assertNotNull(contact);

		restTemplate.delete(getRootUrl() + "/contact/" + id);

		try {
			contact = restTemplate.getForObject(getRootUrl() + "/contact/" + id, Contact.class);
		} catch (final HttpClientErrorException e) {
			Assertions.assertEquals(e.getStatusCode(), HttpStatus.NOT_FOUND);
		}
	}

}
