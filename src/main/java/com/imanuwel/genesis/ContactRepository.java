package com.imanuwel.genesis;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class ContactRepository {

    private final List<Contact> contactList = new ArrayList<>();

    public ContactRepository() {
    }

    public Optional<Contact> findById(Long contactId) {
        return contactList.stream().filter(c -> c.getId().equals(contactId)).findFirst();
    }

    public List<Contact> findAll() {
        return contactList;
    }

    public Contact save(Contact contact) {
        contactList.add(contact);
        return contact;
    }

    public void delete(Contact contact) {
        contactList.remove(contact);
    }
}
