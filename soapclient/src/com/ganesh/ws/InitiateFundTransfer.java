
package com.ganesh.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for initiateFundTransfer complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="initiateFundTransfer">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="initiateFundTransferRequest" type="{http://ws.ganesh.com/}InitiateFundTransferRequest" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "initiateFundTransfer", propOrder = {
    "initiateFundTransferRequest"
})
public class InitiateFundTransfer {

    protected InitiateFundTransferRequest initiateFundTransferRequest;

    /**
     * Gets the value of the initiateFundTransferRequest property.
     * 
     * @return
     *     possible object is
     *     {@link InitiateFundTransferRequest }
     *     
     */
    public InitiateFundTransferRequest getInitiateFundTransferRequest() {
        return initiateFundTransferRequest;
    }

    /**
     * Sets the value of the initiateFundTransferRequest property.
     * 
     * @param value
     *     allowed object is
     *     {@link InitiateFundTransferRequest }
     *     
     */
    public void setInitiateFundTransferRequest(InitiateFundTransferRequest value) {
        this.initiateFundTransferRequest = value;
    }

}
