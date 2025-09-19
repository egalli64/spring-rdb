/**
 * Introduction to Spring - Relational DB
 * 
 * https://github.com/egalli64/spring-rdb
 */
package com.example.srd.x.ros;

public class Order {
    private int categoryId;
    private int menuId;
    private int quantity;

    public Order() {
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getMenuId() {
        return menuId;
    }

    public void setMenuId(int menuId) {
        this.menuId = menuId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Order [categoryId=" + categoryId + ", menuId=" + menuId + ", quantity=" + quantity + "]";
    }
}