/**
 * Introduction to Spring - Relational DB
 * 
 * https://github.com/egalli64/spring-rdb
 */
package com.example.srd.m2.s5;

import org.springframework.data.repository.CrudRepository;

import com.example.srd.m2.entity.Employee;

public interface EmployeeExtraRepository extends CrudRepository<Employee, Long> {
    Iterable<Employee> findByFirstName(String name);

    Iterable<Employee> findByFirstNameStartingWith(String prefix);

    Iterable<Employee> findByFirstNameStartingWithOrLastNameContainingIgnoreCase(String prefixFirst, String inLast);
}
