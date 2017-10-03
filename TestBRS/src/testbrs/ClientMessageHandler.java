/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testbrs;

import java.util.Collections;
import java.util.Set;
import javax.xml.namespace.QName;
import javax.xml.soap.SOAPConstants;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPHeaderElement;
import javax.xml.soap.SOAPMessage;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;

/**
 *
 * @author JINESH
 */
public class ClientMessageHandler implements SOAPHandler<SOAPMessageContext> {
    
    @Override
    public boolean handleMessage(SOAPMessageContext context) {
                        
        Boolean outboundProperty = (Boolean) context.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);
        
        // If it is an outgoing message
        if (outboundProperty) {

            try {
                    SOAPMessage soapMessagg = context.getMessage();
                    SOAPEnvelope soapEnvelope = soapMessagg.getSOAPPart().getEnvelope();

                    // Grab the header of the SOAP envelop
                    SOAPHeader soapHeader = soapEnvelope.getHeader();

                    // Attach a new header if there is none...
                    if (soapHeader == null) {
                            soapHeader = soapEnvelope.addHeader();
                    }

                    // Add the property to the header
                    QName qname = new QName("http://busReservationSystem/", "PROPERTY");

                    // Create a new HeaderElement in order to place the new property
                    SOAPHeaderElement soapHeaderElement = soapHeader.addHeaderElement(qname);

                    soapHeaderElement.setActor(SOAPConstants.URI_SOAP_ACTOR_NEXT);
                    soapHeaderElement.addTextNode(TestBRS.operation_sequence);
                    soapMessagg.saveChanges();

            } catch (SOAPException e) {
                    System.err.println(e);
            }
        }
        
        return true;
        
        
    }
    
    @Override
    public Set<QName> getHeaders() {
        return Collections.EMPTY_SET;
    }
    
    @Override
    public boolean handleFault(SOAPMessageContext messageContext) {
        return true;
    }
    
    @Override
    public void close(MessageContext context) {
    }
    
    
}
