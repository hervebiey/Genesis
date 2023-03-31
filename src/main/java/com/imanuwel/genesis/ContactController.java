package com.imanuwel.genesis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/contacts")
public class ContactController {
    @Autowired
    private ContactRepository contactRepository;

    // View all contacts

    @GetMapping("/")
    public List<Contact> getAllContacts() {
        return contactRepository.findAll();
    }

    // Create a contact

    @PostMapping("/")
    public Contact createContact(@RequestBody Contact contact) {
        return contactRepository.save(contact);
    }

    // Get contact by ID (Extra)

    @GetMapping("/{id}")
    public ResponseEntity<Contact> getContactById(@PathVariable(value = "id") Long contactId)
            throws RuntimeException {
        Contact contact = contactRepository.findById(contactId)
                .orElseThrow(() -> new RuntimeException("Contact not found for this id: " + contactId));
        return ResponseEntity.ok().body(contact);
    }

    // Update a contact

    @PutMapping("/{id}")
    public ResponseEntity<Contact> updateContact(@PathVariable(value = "id") Long contactId,
                                                 @RequestBody Contact contactDetails) throws RuntimeException {
        Contact contact = contactRepository.findById(contactId)
                .orElseThrow(() -> new RuntimeException("Contact not found for this id: " + contactId));

        contact.setFirstName(contactDetails.getFirstName());
        contact.setLastName(contactDetails.getLastName());
        contact.setAddress(contactDetails.getAddress());
        contact.setVatNumber(contactDetails.getVatNumber());
        contact.setFreelancer(contactDetails.isFreelancer());

        final Contact updatedContact = contactRepository.save(contact);
        return ResponseEntity.ok(updatedContact);
    }

    // Delete a contact

    @DeleteMapping("/{id}")
    public Map<String, Boolean> deleteContact(@PathVariable(value = "id") Long contactId)
            throws RuntimeException {
        Contact contact = contactRepository.findById(contactId)
                .orElseThrow(() -> new RuntimeException("Contact not found for this id :: " + contactId));

        contactRepository.delete(contact);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }
}
