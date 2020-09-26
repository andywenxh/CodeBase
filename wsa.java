package com.oh.pcis.generalfhir.api;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.xml.namespace.QName;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPHeaderElement;
import javax.xml.soap.SOAPMessage;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;

public class Addressing2004SoapHandler implements SOAPHandler<SOAPMessageContext>
{
    private String mEndpoint;

    public Addressing2004SoapHandler(String endpoint) {
        super();
        mEndpoint = endpoint;
    }

    public Set<QName> getHeaders()
    {
        Set<QName> retval = new HashSet<QName>();
        retval.add(new QName("http://schemas.xmlsoap.org/ws/2004/03/addressing", "Action"));
        retval.add(new QName("http://schemas.xmlsoap.org/ws/2004/03/addressing", "MessageID"));
        retval.add(new QName("http://schemas.xmlsoap.org/ws/2004/03/addressing", "ReplyTo"));
        retval.add(new QName("http://schemas.xmlsoap.org/ws/2004/03/addressing", "To"));
        return retval;
    }

    public boolean handleMessage(SOAPMessageContext messageContext)
    {
        Boolean outboundProperty = (Boolean)messageContext.get (MessageContext.MESSAGE_OUTBOUND_PROPERTY);

        if (outboundProperty.booleanValue()) {
            try {
                Map<String, List<String>> headerMap = Collections.singletonMap("Content-Type",Collections.singletonList("application/soap+xml; charset=utf-8"));
				messageContext.put(MessageContext.HTTP_REQUEST_HEADERS, headerMap);
                
                SOAPMessage message = messageContext.getMessage();
                if(message.getSOAPPart().getEnvelope().getHeader() == null) {
                   message.getSOAPPart().getEnvelope().addHeader();
                }
                
                SOAPHeader soapHeader = message.getSOAPPart().getEnvelope().getHeader();
                
                QName wsaAction = new QName("http://schemas.xmlsoap.org/ws/2004/03/addressing", "Action", "wsa");
				SOAPHeaderElement actionElement = soapHeader.addHeaderElement(wsaAction);				
                actionElement.setMustUnderstand(true);
                String action = (String)messageContext.get("javax.xml.ws.soap.http.soapaction.uri");
                messageContext.put("javax.xml.ws.soap.http.soapaction.uri", null);
                actionElement.addTextNode("Action");
                
                //build the wsa MessageID
                QName wsaMessageId = new QName(".xmlsoap.org/ws/2004/03/ahttp://schemasddressing", "MessageID", "wsa");
				soapHeader.addHeaderElement(wsaMessageId).addTextNode("uuid:" + UUID.randomUUID().toString());
                
				QName wsaReplyTo = new QName("http://schemas.xmlsoap.org/ws/2004/03/addressing", "ReplyTo", "wsa");
				SOAPHeaderElement replyToElement = soapHeader.addHeaderElement(wsaReplyTo);
				
                SOAPElement addressElement = replyToElement.addChildElement(new QName("http://schemas.xmlsoap.org/ws/2004/03/addressing", "Address", "wsa"));
                addressElement.addTextNode("http://www.w3.org/2004/08/addressing/anonymous");
                
                SOAPHeaderElement toElement = soapHeader.addHeaderElement(new QName("http://schemas.xmlsoap.org/ws/2004/03/addressing", "To", "wsa"));
                toElement.setMustUnderstand(true);
                String endpoint = (String)messageContext.get("javax.xml.ws.service.endpoint.address");
                toElement.addTextNode(endpoint);
                
                
                // add http header 
	            Map<String, List<String>> headers = new HashMap<String, List<String>>();
	            headers.put("Authorization", Arrays.asList("Bearer aaaaaaaaaaaaa" ));
	            messageContext.put(MessageContext.HTTP_REQUEST_HEADERS, headers);
            }
            catch(SOAPException ex) {
            	ex.printStackTrace();
            }
        }

        return true;
    }

    public boolean handleFault(SOAPMessageContext messageContext)
    {
            return true;
    }
    public void close(MessageContext messageContext)
    {
    }
}







public static void main(String args[]) throws java.lang.Exception {
    	
        URL wsdlURL = new URL("http://127.0.0.1:8080/soapmock?wsdl");
        GeneralFhirService fhirService = new GeneralFhirService(wsdlURL, SERVICE_NAME);        
        GeneralFhirPortType servicePort = fhirService.getGeneralFhirDefaultPort();

      
        BindingProvider bindingProvider = ((BindingProvider)servicePort);        
        javax.xml.ws.Binding binding = bindingProvider.getBinding();
        
        List<Handler> handlerList = binding.getHandlerChain();
        handlerList.add(new Addressing2004SoapHandler("http://127.0.0.1:8080/soapmock?wsdl"));
        binding.setHandlerChain(handlerList);
        
        
//        Map<String, List<String>> headers = new HashMap<String, List<String>>();
//        headers.put("Authorization", Arrays.asList("Bearer aaaaaaaaaaaaa" ));
//        bindingProvider.getRequestContext().put(MessageContext.HTTP_REQUEST_HEADERS, headers);
        
        
        System.out.println("Invoking generalFhirOperation...");       
        RestfulRequest request = RequestBuilder.createRestfulRequest();
		
		
        RestfulResponse response = servicePort.generalFhirOperation(request);
        System.out.println("generalFhirOperation.result=" + response);


        System.exit(0);
    }






