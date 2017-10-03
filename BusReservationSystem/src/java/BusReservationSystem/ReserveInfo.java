/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BusReservationSystem;
import java.sql.Time;
import java.util.Date;
/**
 *
 * @author JINESH
 */
public class ReserveInfo {
    
    String errormsg;
    int id;
    int passengerid;
    int busid;
    Date date;
    String time;
    int seat; 
    
    public void setErrorMsg(String errormsg){
        this.errormsg = errormsg;
    }
    
    public String getErrorMsg(){
        return errormsg;
    }
    
    public void setId(int id){
        this.id = id;
    }
  
    public int getId(){
        return id;
    }
    
    public void setPassengerId(int passengerid){
        this.passengerid = passengerid;
    }
    
    public int getPassengerId(){
        return passengerid;
    }   
    
    public void setBusID(int busid){
        this.busid = busid;
    }
    
    public int getBusID(){
        return busid;
    }   
    
    public void setDate(Date date){
        this.date = date;
    }
    
    public Date getDate(){
        return date;
    }
    
    public void setTime(String time){
        this.time = time;
    }
    
    public String getTime(){
        return time;
    }
    
    public void setSeat(int seat){
        this.seat = seat;
    }
  
    public int getSeat(){
        return seat;
    }
    
}
