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
package com.example.contactDemo.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.contactDemo.exception.ResourceNotFoundException;
import com.example.contactDemo.model.Contact;
import com.example.contactDemo.repository.ContactRepository;


/**
 *  Contact controller.
 *
 * @author Chuck Herting
 */
@RestController
@RequestMapping("/api/v1")
public class ContactController {

	@Autowired
	private ContactRepository contactRepository;

	/**
	 * Gets Contact by id.
	 *
	 * @param contactId the contact id
	 * @return the contact by id
	 * @throws ResourceNotFoundException 
	 */
	@GetMapping("/contact/{id}")
	public ResponseEntity<Contact> getContactById(@PathVariable(value = "id") Long contactId) throws ResourceNotFoundException {
		Contact contact = contactRepository.findById(contactId)
				.orElseThrow(() -> new ResourceNotFoundException("Contact not found for => " + contactId));
		return ResponseEntity.ok().body(contact);
	}

	/**
	 * Get all contacts list.
	 *
	 * @return the list
	 */
	@GetMapping("/contact")
	public List<Contact> getAllContacts() {
		return contactRepository.findAll();
	}

	  /**
	   * Create contact.
	   *
	   * @param contact 
	   * @return the contact
	   */
	  @PostMapping("/contact")
	  public Contact createContact(@Valid @RequestBody Contact contact) {
	    return contactRepository.save(contact);
	  }

	  /**
	   * Update contact response entity.
	   *
	   * @param contactId the contact id
	   * @param contact details
	   * @return the response entity
	   * @throws ResourceNotFoundException 
	   */
	  @PutMapping("/contact/{id}")
	  public ResponseEntity<Contact> updateContact(@PathVariable(value = "id") Long contactId, @Valid @RequestBody Contact contactDetails)
	  throws ResourceNotFoundException {

	    Contact contact = contactRepository.findById(contactId)
	            .orElseThrow(() -> new ResourceNotFoundException("Contact not found for => " + contactId));

	    contact.setFirstName(contactDetails.getFirstName());
	    contact.setLastName(contactDetails.getLastName());
	    contact.setEmailAddress(contactDetails.getEmailAddress());
	    contact.setHomePhone(contactDetails.getHomePhone());
	    contact.setMobilePhone(contactDetails.getMobilePhone());
	    contact.setUpdatedAt(new Date());
	    final Contact updatedContact = contactRepository.save(contact);
	    return ResponseEntity.ok(updatedContact);
	  }

	  /**
	   * Delete contact map.
	   *
	   * @param contactId 
	   * @return the map
	   * @throws Exception the exception
	   */
	  @DeleteMapping("/contact/{id}")
	  public Map<String, Boolean> deleteContact(@PathVariable(value = "id") Long contactId) throws Exception {
	    Contact contact = contactRepository.findById(contactId)
	            .orElseThrow(() -> new ResourceNotFoundException("Contact not found for => " + contactId));

	    contactRepository.delete(contact);
	    Map<String, Boolean> response = new HashMap<>();
	    response.put("deleted", Boolean.TRUE);
	    return response;
	  }
}
