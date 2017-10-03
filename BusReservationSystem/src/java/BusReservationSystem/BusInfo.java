/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BusReservationSystem;

/**
 *
 * @author JINESH
 */
public class BusInfo {
    
    int busId;
    int routeID;
    Boolean ac;
    int fare;
    int availableseat;
    String departureTime;
    String arrivlTime;
    String errormsg;
    
    
    public void setBusID(int busId){
        this.busId = busId;
    }
    
    public int getBusID(){
        return busId;
    }   
    
    public void setRouteID(int id){
        routeID = id;
    }
    
    public int getRouteID(){
        return routeID;
    }
    
    public void setAc(boolean ac){
        this.ac = ac;
    }
    
    public boolean getAc(){
        return ac;
    }
    
    public void setFare(int fare){
        this.fare = fare;
    }
    
    public int getFare(){
        return fare;
    }
    
    
    public void setAvailableSeat(int availableseat){
        this.availableseat = availableseat;
    }
    
    public int getAvailableSeat(){
        return availableseat;
    }
    
    public void setDepartureTime(String departureTime){
        this.departureTime = departureTime;
    }
    
    public String getDepartureTime(){
        return departureTime;
    }
    
    public void setArrivalTime(String arrivalTime){
        this.arrivlTime = arrivalTime;
    }
    
    public String getArrivalTime(){
        return arrivlTime;                
    }        
    
    public void setErrorMsg(String errormsg){
        this.errormsg = errormsg;
    }
    
    public String getErrorMsg(){
        return errormsg;
    }
    
}
