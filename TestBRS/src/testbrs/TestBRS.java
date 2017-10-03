/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testbrs;

import busreservationsystem.BusInfo;
import busreservationsystem.ReserveInfo;
import busreservationsystem.RouteInfo;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Scanner;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPMessage;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPMessageContext;

/**
 *
 * @author JINESH
 */
public class TestBRS {

    
    /**
     * @param args the command line arguments
     */
    
    
    static String operation_sequence = "";
    public static void main(String[] args) {
        
        TestBRS testbrs = new TestBRS();
        // TODO code application logic here
        Scanner sc = new Scanner(System.in);
        System.out.println("Welcome to Bus Reservation System:\n" + "Enter Your Desired Username:");
        operation_sequence = sc.nextLine();
        
        
        while(true){
            System.out.println("\nEnter Number Based On Your Choice:\n1. Get Route Info    2. Get Bus Info    3. Get Reservation    4. Exit");
            System.out.print("Choice: ");
            int n = sc.nextInt();
            sc.nextLine();                        
            System.out.println("");

            if (n == 1){

                operation_sequence += "->1";
                System.out.println("Enter Source(Eg: rochester, chicago, dallas, miami, etc.)");
                String source = sc.nextLine().toLowerCase();

                System.out.println("Enter Destination (Eg: new york city, rochester, austin, orlando, etc.)");
                String destination = sc.nextLine().toLowerCase();
                System.out.println("");
                try{
                    
                    ArrayList<RouteInfo> route_info = (ArrayList<RouteInfo>) getRouteInfo(source, destination);  
                    testbrs.printRouteInfo(route_info);
                    if(route_info.isEmpty()){
                        System.out.println("Sorry No Buses For This Route");
                    }
                    
                }catch(javax.xml.ws.soap.SOAPFaultException soapFaultException){
                    
                    operation_sequence = "";
                    System.out.println(soapFaultException.getFault().getFaultString());
                    System.out.println("Restart again from Operation 1");
                }
            } else if (n == 2){
                
                try{
                    operation_sequence += "->2";
                    System.out.println("\nEnter Route Id:");
                    int route_id = sc.nextInt();sc.nextLine();
                    ArrayList<BusInfo> bus_info = (ArrayList<BusInfo>) getBusInfo(route_id);
                    testbrs.printBusInfo(bus_info);

                }catch(javax.xml.ws.soap.SOAPFaultException soapFaultException){
                    
                    operation_sequence = "";
                    System.out.println(soapFaultException.getFault().getFaultString());
                    System.out.println("Restart again from Operation 1");
                }
            
            } else if (n == 3){
                String prev_seq = operation_sequence;
                try{
                    
                    
                    operation_sequence += "->3";
                    System.out.println("");
                    System.out.println("Enter Bus-ID for Reservation:");
                    int busid = sc.nextInt();sc.nextLine();
                    System.out.println("\nEnter Passenger Name:");
                    String name = sc.nextLine();
                    System.out.println("\nEnter Number of Tickets:");
                    int ticket_qty = sc.nextInt();sc.nextLine();
                    System.out.println("\nEnter Passenger Email:");
                    String email = sc.nextLine();
                    System.out.println("\nEnter Passenger Phone Number:");
                    int phone = sc.nextInt();sc.nextLine();
                    ArrayList<ReserveInfo> reserveinfo =  (ArrayList<ReserveInfo>) insertReserveInfo(name, email, phone, ticket_qty, busid);
                    String msg = reserveinfo.get(0).getErrorMsg();
                    if (msg != null ){
                        operation_sequence = prev_seq;
                        System.out.println(msg);
                        System.out.println("Repeat Only Last Operation");
                    }else{
                    
                        testbrs.printReserveInfo(reserveinfo); 
                        operation_sequence = "";
                        
                        System.out.println("");
                        System.out.println("You Can AGAIN Book More Tickets!!!!!!");
                              
                    }
                    

                }catch(javax.xml.ws.soap.SOAPFaultException soapFaultException){
                
                    operation_sequence = "";
                    System.out.println(soapFaultException.getFault().getFaultString());
                    System.out.println("Restart again from Operation 1\n");
                    
                }
                
            } else if (n == 4){
            
                break;
            
            }
        }
    }
            
   
    void printReserveInfo(ArrayList<ReserveInfo> reserveinfo){
        
        System.out.println("");
        System.out.println("Reservation Detail:");        
        for (ReserveInfo reserveinfo1 : reserveinfo) {         
            System.out.println("Reservation ID = "+reserveinfo1.getId());
            System.out.println("Passenger Id = "+ reserveinfo1.getPassengerId());
            System.out.println("Bus Id = "+reserveinfo1.getBusID());
            System.out.println("Seat No = "+reserveinfo1.getSeat());
            System.out.println("Date = "+reserveinfo1.getDate());
            System.out.println("Time = "+reserveinfo1.getTime());
        }
    }
    
    void printBusInfo(ArrayList<BusInfo> businfo){
        
        System.out.format("%1s%5s%8s%20s%20s", "Bus-Id", "AC", "Fare", "Departure-Time", "Arrival-Time");
        System.out.println(" ");
//        System.out.println(businfo.get(0).getFare());
        for (BusInfo businfo1 : businfo) {           
            System.out.format("%1s%10s%6s%10s%25s", businfo1.getBusID(), 
                    businfo1.isAc(), businfo1.getFare()+"$", 
                    businfo1.getDepartureTime(),businfo1.getArrivalTime());
            System.out.println("");
        }
    }
    
    
    void printRouteInfo(ArrayList<RouteInfo> routeinfo){
        
        for (RouteInfo routeinfo1 : routeinfo){
            System.out.println("Route Id : " + routeinfo1.getRouteID() + " for " 
                    + routeinfo1.getOrigin() + " to " + routeinfo1.getDestination());
        }
    
    }
    

    private static java.util.List<busreservationsystem.RouteInfo> getRouteInfo(java.lang.String origin, java.lang.String destination) {
        busreservationsystem.BusReservation_Service service = new busreservationsystem.BusReservation_Service();
        busreservationsystem.BusReservation port = service.getBusReservationPort();
        return port.getRouteInfo(origin, destination);
    }

    private static java.util.List<busreservationsystem.BusInfo> getBusInfo(int routeid) {
        busreservationsystem.BusReservation_Service service = new busreservationsystem.BusReservation_Service();
        busreservationsystem.BusReservation port = service.getBusReservationPort();
        return port.getBusInfo(routeid);
    }

    private static java.util.List<busreservationsystem.ReserveInfo> insertReserveInfo(java.lang.String name, java.lang.String email, int phone, int qty, int busid) {
        busreservationsystem.BusReservation_Service service = new busreservationsystem.BusReservation_Service();
        busreservationsystem.BusReservation port = service.getBusReservationPort();
        return port.insertReserveInfo(name, email, phone, qty, busid);
    }
    
}
