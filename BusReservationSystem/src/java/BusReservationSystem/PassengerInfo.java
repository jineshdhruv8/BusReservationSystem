/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BusReservationSystem;

import java.math.BigInteger;

/**
 *
 * @author JINESH
 */
public class PassengerInfo {
    String name;
    String email;
    int phone;
    int numberoftickets;
    
    public void setName(String name){
        this.name = name;
    }
    
    public String getName(){
        return name;
    }
    
    public void setEmail(String email){
        this.email = email;
    }
    
    public String getEmail(){
        return email;
    }
    
    public void setPhone(int phone){
        this.phone = phone;
    }
    
    public int getPhone(){
        return phone; 
    }
    
    public void setNumberOfTickets(int numberoftickets){
        this.numberoftickets = numberoftickets;
    }
    
    public int getNumberOfTickets(){
        return numberoftickets;
    }
    
    
}
