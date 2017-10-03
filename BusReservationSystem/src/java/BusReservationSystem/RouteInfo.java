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
public class RouteInfo {
    String origin;
    String destination;
    int routeID;
    
    public void setRouteID(int id){
        routeID = id;
    }
    
    public int getRouteID(){
        return routeID;
    }
    
    public void setOrigin(String origin){
        this.origin = origin;
    }
    
    public String getOrigin(){
        return origin;
    }
    
    public void setDestination(String destination){
        this.destination = destination;
    }
    
    public String getDestination(){
        return destination;
    }
}
