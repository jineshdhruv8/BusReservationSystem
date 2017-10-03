/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BusReservationSystem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
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

/**
 *
 * @author JINESH
 */
public class DatabaseConnection {
    Connection con;
    
    int busid;
    Boolean route_flag;
    Boolean bus_flag;
    Boolean passenger_flag;
    
    
    public void setBusId(int busid){
        this.busid = busid;
    }
    
    public int geBusId(){
        return busid;
    }
    
    
    public void setRouteFlag(Boolean route_flag){
        this.route_flag = route_flag;
    }
    
    public Boolean getRouteFlag(){
        return route_flag;
    }
    
    public void setBusFlag(Boolean bus_flag){
        this.bus_flag = bus_flag;
    }
    
    public Boolean getBusFlag(){
        return bus_flag;
    }
    
    public void setPassengerFlag(Boolean passenger_flag){
        this.passenger_flag = passenger_flag;
    }
    
    public Boolean getPassengerFlag(){
        return passenger_flag;
    }
    
 
    
    Connection connectMysql(String database_name, String username, String pwd) throws SQLException, ClassNotFoundException{
        Class.forName("com.mysql.jdbc.Driver");
        con=DriverManager.getConnection(  "jdbc:mysql://localhost:3306/"+database_name,username,pwd);  
        return con;
    }
    
    
    ArrayList<RouteInfo> getRouteInfo(String source, String destination) throws SQLException{
        
     try {
        con = connectMysql("brs", "root", "test");
     } catch (ClassNotFoundException ex) {
        Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, ex);
     }
        String sql = "select * from route where origin = ? and destination = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, source);
        ps.setString(2, destination);
        ResultSet rs = ps.executeQuery();
        ArrayList<RouteInfo> route_id_list = new ArrayList<>();
        
        while(rs.next()){
            RouteInfo routeInfo = new RouteInfo();
            routeInfo.setRouteID(rs.getInt("id"));
            routeInfo.setOrigin(rs.getString("origin"));
            routeInfo.setDestination(rs.getString("destination"));
            route_id_list.add(routeInfo);
        }            
        setRouteFlag(true);
        return route_id_list;
    }
    
    
    
    
    ArrayList<BusInfo> getBusInfo(int routeID) throws SQLException{        
        ArrayList<BusInfo> result = new ArrayList();        
        if(getRouteFlag()){
            try {
               con = connectMysql("brs", "root", "test");        
               String sql = "select * from bus where routeid = ?";
               PreparedStatement ps = con.prepareStatement(sql);
               ps.setInt(1, routeID);           
               ResultSet rs = ps.executeQuery();
               while(rs.next()){
                   BusInfo busInfo = new BusInfo();
                   busInfo.setBusID(rs.getInt("id"));
                   busInfo.setRouteID(rs.getInt("routeid"));
                   busInfo.setAc(rs.getBoolean("ac"));
                   busInfo.setFare(rs.getInt("fare"));
                   busInfo.setAvailableSeat(rs.getInt("availableseats"));
                   busInfo.setDepartureTime(rs.getString("departuretime"));
                   busInfo.setArrivalTime(rs.getString("arrivaltime"));                
                   result.add(busInfo);
                   setBusFlag(true);
               }   
           } catch (ClassNotFoundException ex) {
            Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else{
            BusInfo busInfo = new BusInfo();
            busInfo.setErrorMsg("Select Route before selecting bus option");
            result.add(busInfo);
        }
        
        return result;
    }
    
    
    ArrayList<ReserveInfo> InsertReserveInfo(String name, String email, int phone, int qty,int busid) throws SQLException{
        ArrayList<ReserveInfo> result = new ArrayList();        
        try{
            int passengerId = -1;
            if(getBusFlag()){
                con = connectMysql("brs", "root", "test");        
                
                // Check how many seats are available
                Statement stmt=con.createStatement();
                ResultSet rs1 = stmt.executeQuery("select availableseats - "+ qty +"  from bus where id = "+busid);
                rs1.next();
                int available_count = rs1.getInt(1);
                
                if (available_count < 0){
                    int diff = qty + available_count;
                    ReserveInfo reserveInfo = new ReserveInfo();
                    reserveInfo.setErrorMsg("Available seats are only " + diff);
                    result.add(reserveInfo);
                }else{
                    String sql = "select id from passenger where email = ?";
                    PreparedStatement ps = con.prepareStatement(sql);
                    ps.setString(1, email);           
                    rs1 = ps.executeQuery();
                    if (!rs1.isBeforeFirst()){
                        // Insert passenger info in database, if it does not exist
                        System.out.println("1st passenger");
                        sql = "select max(id) from passenger";
                        ps = con.prepareStatement(sql);
                        ResultSet rs2 = ps.executeQuery();
                        rs2.next();
                        passengerId = rs2.getInt(1) + 1;

                        sql = "insert into passenger (id, name, email, mobile, tickets) values(?,?,?,?,?)";
                        ps = con.prepareStatement(sql);
                        ps.setInt(1, passengerId);
                        ps.setString(2, name);
                        ps.setString(3, email);
                        ps.setInt(4, phone);
                        ps.setInt(5,qty);
                        int response1 = ps.executeUpdate();    

                    }else{
                        rs1.next();
                        passengerId = rs1.getInt(1);
                    }

                    // get last id in reserve table
                    sql = "select count(id) from reserve";
                    ps = con.prepareStatement(sql);
                    rs1 = ps.executeQuery();
                    rs1.next();
                    int reserveid = rs1.getInt(1) + 1;

                    // Insert new entry in reserve table
                    stmt=con.createStatement();
                    rs1 = stmt.executeQuery("select max(seat) from reserve where busid="+busid);  
                    rs1.next();
                    int seat = rs1.getInt(1) + qty;

                    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    DateFormat time = new SimpleDateFormat("HH:mm:ss");
                    Date date = new Date();
                    String current_date = dateFormat.format(date);            
                    String current_time = time.format(date);

                    stmt=con.createStatement();
                    stmt.executeUpdate("insert into reserve values("+reserveid+",(select id from passenger where id ="+
                    passengerId+"),(select id from bus where id = "+busid+"),\""+ current_date+"\",\""+current_time+"\","+ 
                    seat+")");
                    
                    ReserveInfo reserveInfo = new ReserveInfo();
                    reserveInfo.setId(reserveid);
                    reserveInfo.setPassengerId(passengerId);
                    reserveInfo.setBusID(busid);
                    reserveInfo.setDate(date);
                    reserveInfo.setTime(current_time);
                    reserveInfo.setSeat(seat);
                    
                    result.add(reserveInfo);
                    
                    // Update available seats in Bus table
                    stmt = con.createStatement();                    
                    stmt.executeUpdate("update bus set availableseats = availableseats - "+qty+" where id = "+busid);                                        
                }
            }else{
                ReserveInfo reserveInfo = new ReserveInfo();
                reserveInfo.setErrorMsg("Select Bus before selecting Reserve option");
                result.add(reserveInfo);
            }
            
        }catch (Exception e){
            e.printStackTrace();
        }   
        return result;
    }
}
