
package com.ganesh.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for initiateFundTransferResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="initiateFundTransferResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="InitiateFundTransferReply" type="{http://ws.ganesh.com/}InitiateFundTransferReply" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "initiateFundTransferResponse", propOrder = {
    "initiateFundTransferReply"
})
public class InitiateFundTransferResponse {

    @XmlElement(name = "InitiateFundTransferReply")
    protected InitiateFundTransferReply initiateFundTransferReply;

    /**
     * Gets the value of the initiateFundTransferReply property.
     * 
     * @return
     *     possible object is
     *     {@link InitiateFundTransferReply }
     *     
     */
    public InitiateFundTransferReply getInitiateFundTransferReply() {
        return initiateFundTransferReply;
    }

    /**
     * Sets the value of the initiateFundTransferReply property.
     * 
     * @param value
     *     allowed object is
     *     {@link InitiateFundTransferReply }
     *     
     */
    public void setInitiateFundTransferReply(InitiateFundTransferReply value) {
        this.initiateFundTransferReply = value;
    }

}
