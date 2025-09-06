/**
 * Introduction to Spring - Relational DB
 * 
 * https://github.com/egalli64/spring-rdb
 */
package com.example.srd.m2.s6;

import java.math.BigDecimal;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.example.srd.m2.entity.Employee;

public interface EmployeeQueryRepository extends CrudRepository<Employee, Long> {
    @Query("select e from Employee e where e.salary between ?1 and ?2 order by salary desc, hired desc")
    Iterable<Employee> findBySalaryRange(BigDecimal low, BigDecimal high);

    @Query("select e from Employee e where e.salary between :low and :high order by salary desc, hired desc")
    Iterable<Employee> findBySalaryRangeNames(BigDecimal low, BigDecimal high);

    @Query("select e from Employee e where e.firstName like ?1%")
    Iterable<Employee> findByFirstName(String prefix);

    @Query("select e from Employee e where e.firstName like %?1%")
    Iterable<Employee> findByFirstNameIn(String infix);

    @Query(value = "select * from employee where salary between ?1 and ?2 order by salary desc, hired desc", nativeQuery = true)
    Iterable<Employee> findBySalaryRangeNative(BigDecimal low, BigDecimal high);
}
