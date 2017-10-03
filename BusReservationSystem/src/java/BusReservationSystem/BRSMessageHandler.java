/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BusReservationSystem;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Set;
import javax.xml.namespace.QName;
import javax.xml.soap.Node;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPConstants;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPFault;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPMessage;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;
import javax.xml.ws.soap.SOAPFaultException;

/**
 *
 * @author JINESH
 */
public class BRSMessageHandler implements SOAPHandler<SOAPMessageContext> {
    
    static String operation_sequence[] = {"1", "2", "3"};
    static HashMap user_info = new LinkedHashMap();     
    
    public boolean handleMessage(SOAPMessageContext context) {
        
       
        Boolean outBoundProperty = (Boolean) context
                        .get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);
        
        // if this is an incoming message from the client
        if (!outBoundProperty) {

            try {

                    // Get the SOAP Message and grab the headers
                    SOAPMessage soapMsg = context.getMessage();

                    SOAPEnvelope soapEnv = soapMsg.getSOAPPart().getEnvelope();
                    SOAPHeader soapHeader = soapEnv.getHeader();

                    // Grab an iterator to go through the headers
                    Iterator<?> headerIterator = soapHeader
                                    .extractHeaderElements(SOAPConstants.URI_SOAP_ACTOR_NEXT);

                    // if there is no additional header
                    if (headerIterator != null && headerIterator.hasNext()) {

                        // Extract the property node of the header
                        Node propertyNode = (Node) headerIterator.next();

                        String property = null;

                        if (propertyNode != null)
                                property = propertyNode.getValue();

                        if(!check_property(property)){

                            // Attach an error message as a response
                            SOAPBody soapBody = soapMsg.getSOAPPart().getEnvelope().getBody();
                            SOAPFault soapFault = soapBody.addFault();
                            soapFault.setFaultString("Invalid Operation Sequence(Expected: 1->2->3)");
                            throw new SOAPFaultException(soapFault);  
                        }
                    }
            } catch (SOAPException e) {
                    System.err.println(e);
            } 
        }
        return true;
    }
    
    public Set<QName> getHeaders() {
        return Collections.EMPTY_SET;
    }
    
    public boolean handleFault(SOAPMessageContext messageContext) {
        return true;
    }
    
    public void close(MessageContext context) {
    }
    
    
    public boolean check_property(String property){
    
        String temp_seq_operation[] = property.split("->");
        
        for (int i = 1; i < temp_seq_operation.length; i++) {
            
            if(!temp_seq_operation[i].equals(operation_sequence[i - 1])){
                
                return false;
            }
        }
        
        user_info. put(temp_seq_operation[0], property);
        return true;
    }
    
    
    
}
