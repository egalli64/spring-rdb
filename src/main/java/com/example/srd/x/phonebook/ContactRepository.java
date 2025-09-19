/**
 * Introduction to Spring - Relational DB
 * 
 * https://github.com/egalli64/spring-rdb
 */
package com.example.srd.x.phonebook;

import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public class ContactRepository {
    List<Contact> findAllByOrderByName() {
        return List.of(new Contact("Bob", "4212"));
    }

    public void deleteById(int id) {
    }

    public Contact save(Contact contact) {
        return contact;
    }
}
