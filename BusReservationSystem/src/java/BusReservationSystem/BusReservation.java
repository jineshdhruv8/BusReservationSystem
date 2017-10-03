/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BusReservationSystem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jws.HandlerChain;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;

/**
 *
 * @author JINESH
 */
@WebService(serviceName = "BusReservation")
@HandlerChain(file = "BusReservation_handler.xml")
public class BusReservation {
    
    DatabaseConnection db;
    /**
     *
     * @param origin
     * @param destination
     * @return
     */
    @WebMethod (operationName="getRouteInfo")
    public ArrayList<RouteInfo> getRouteInfo(@WebParam(name="origin") String origin, @WebParam(name="destination") String destination ){
        db = new DatabaseConnection();        
        ArrayList<RouteInfo> routeInfo= null;
        try {
            routeInfo = db.getRouteInfo(origin, destination);
        } catch (SQLException ex) {
            Logger.getLogger(BusReservation.class.getName()).log(Level.SEVERE, null, ex);
        }        
        return routeInfo;
    }
    
    
    
    /**
     *
     * @param routeid
     * @return
     */
    @WebMethod(operationName = "getBusInfo")
    public ArrayList<BusInfo> getBusInfo(@WebParam(name = "routeid") int routeid){
        
//        DatabaseConnection db = new DatabaseConnection();              
        ArrayList<BusInfo> busInfo = new ArrayList();
        try {            
            busInfo = db.getBusInfo(routeid);
        } catch (SQLException ex) {
            Logger.getLogger(BusReservation.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NullPointerException npe){
            
            
            BusInfo obj = new BusInfo();
            obj.setErrorMsg("Select Route before selecting bus option");
            busInfo.add(obj);
            
        }       
        return busInfo;        
    }
    
      
    /**
     *
     * @param name
     * @param email
     * @param phone
     * @param qty
     * @param busid
     * @return
     *
     */
    @WebMethod(operationName = "InsertReserveInfo")
    public ArrayList<ReserveInfo> InsertReserveInfo(@WebParam(name = "name") String name,
            @WebParam(name = "email") String email, @WebParam(name = "phone") int phone,
            @WebParam(name = "qty") int qty, @WebParam(name = "busid") int busid){
        
        ArrayList<ReserveInfo> reserveInfo = new ArrayList();
        
        try {
            reserveInfo = db.InsertReserveInfo(name, email, phone, qty,busid);
        } catch (SQLException ex) {
            Logger.getLogger(BusReservation.class.getName()).log(Level.SEVERE, null, ex);
        }
            
            
        System.out.println(reserveInfo);
        return reserveInfo;
    
    }
}


