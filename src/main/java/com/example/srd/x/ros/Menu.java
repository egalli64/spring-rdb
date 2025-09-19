/**
 * Introduction to Spring - Relational DB
 * 
 * https://github.com/egalli64/spring-rdb
 */
package com.example.srd.x.ros;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;

@Entity
public class Menu implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MENU_ID")
    private Integer id;

    private String name;
    private String description;
    private Double price;
    @Column(name = "CATEGORY_ID")
    private Integer category;
    @Transient
    private int quantity;

    public Menu() {
    }

    public Menu(Menu other) {
        this.id = other.id;
        this.name = other.name;
        this.description = other.description;
        this.price = other.price;
        this.category = other.category;
        this.quantity = other.quantity;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getCategory() {
        return category;
    }

    public void setCategory(Integer category) {
        this.category = category;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void changeQuantity(int delta) {
        quantity += delta;
    }

    @Override
    public int hashCode() {
        return Objects.hash(category, description, id, name, price);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Menu other = (Menu) obj;
        return Objects.equals(category, other.category) && Objects.equals(description, other.description)
                && Objects.equals(id, other.id) && Objects.equals(name, other.name)
                && Objects.equals(price, other.price);
    }

    @Override
    public String toString() {
        return "Menu [id=" + id + ", name=" + name + ", description=" + description + ", price=" + price + ", category="
                + category + ", quantity=" + quantity + "]";
    }
}