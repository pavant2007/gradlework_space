
package com.ganesh.ws;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.ganesh.ws package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _InitiateFundTransfer_QNAME = new QName("http://ws.ganesh.com/", "initiateFundTransfer");
    private final static QName _InitiateFundTransferResponse_QNAME = new QName("http://ws.ganesh.com/", "initiateFundTransferResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.ganesh.ws
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link InitiateFundTransferResponse }
     * 
     */
    public InitiateFundTransferResponse createInitiateFundTransferResponse() {
        return new InitiateFundTransferResponse();
    }

    /**
     * Create an instance of {@link InitiateFundTransfer }
     * 
     */
    public InitiateFundTransfer createInitiateFundTransfer() {
        return new InitiateFundTransfer();
    }

    /**
     * Create an instance of {@link InitiateFundTransferReply }
     * 
     */
    public InitiateFundTransferReply createInitiateFundTransferReply() {
        return new InitiateFundTransferReply();
    }

    /**
     * Create an instance of {@link InitiateFundTransferRequest }
     * 
     */
    public InitiateFundTransferRequest createInitiateFundTransferRequest() {
        return new InitiateFundTransferRequest();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link InitiateFundTransfer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.ganesh.com/", name = "initiateFundTransfer")
    public JAXBElement<InitiateFundTransfer> createInitiateFundTransfer(InitiateFundTransfer value) {
        return new JAXBElement<InitiateFundTransfer>(_InitiateFundTransfer_QNAME, InitiateFundTransfer.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link InitiateFundTransferResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.ganesh.com/", name = "initiateFundTransferResponse")
    public JAXBElement<InitiateFundTransferResponse> createInitiateFundTransferResponse(InitiateFundTransferResponse value) {
        return new JAXBElement<InitiateFundTransferResponse>(_InitiateFundTransferResponse_QNAME, InitiateFundTransferResponse.class, null, value);
    }

}
