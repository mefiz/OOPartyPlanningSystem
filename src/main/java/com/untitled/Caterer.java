/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.java.com.untitled;

/**
 *
 * @author sath
 */
public class Caterer {
    
    private String caterer;
    private int price;
    
    //getters and setters
    //caterer
    public String getCaterer() {
        return caterer;
    }
    public void setCaterer(String caterer) {
        this.caterer = caterer;
    }
    
    //price
    public int getPrice() {
        return price;
    }
    public void setPrice(int price) {
        this.price = price;
    }
    
    //end getters and setters
    
    //constructors
    
    public Caterer(String caterer, int price) {
        this.caterer = caterer;
        this.price = price;
    }

    public Caterer(String caterer) {
        this.caterer = caterer;
    }

    public Caterer() {
        //empty
    }
    
    
}
