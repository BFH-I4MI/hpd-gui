
package oasis.names.tc.dsml._2._0.core;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java-Klasse für BatchRequest complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="BatchRequest">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="authRequest" type="{urn:oasis:names:tc:DSML:2:0:core}AuthRequest" minOccurs="0"/>
 *         &lt;group ref="{urn:oasis:names:tc:DSML:2:0:core}BatchRequests" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="requestID" type="{urn:oasis:names:tc:DSML:2:0:core}RequestID" />
 *       &lt;attribute name="processing" default="sequential">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *             &lt;enumeration value="sequential"/>
 *             &lt;enumeration value="parallel"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="responseOrder" default="sequential">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *             &lt;enumeration value="sequential"/>
 *             &lt;enumeration value="unordered"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="onError" default="exit">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *             &lt;enumeration value="resume"/>
 *             &lt;enumeration value="exit"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BatchRequest", propOrder = {
    "authRequest",
    "batchRequests"
})
public class BatchRequest {

    protected AuthRequest authRequest;
    @XmlElements({
        @XmlElement(name = "searchRequest", type = SearchRequest.class),
        @XmlElement(name = "modifyRequest", type = ModifyRequest.class),
        @XmlElement(name = "addRequest", type = AddRequest.class),
        @XmlElement(name = "delRequest", type = DelRequest.class),
        @XmlElement(name = "modDNRequest", type = ModifyDNRequest.class),
        @XmlElement(name = "compareRequest", type = CompareRequest.class),
        @XmlElement(name = "abandonRequest", type = AbandonRequest.class),
        @XmlElement(name = "extendedRequest", type = ExtendedRequest.class)
    })
    protected List<DsmlMessage> batchRequests;
    @XmlAttribute(name = "requestID")
    protected String requestID;
    @XmlAttribute(name = "processing")
    protected String processing;
    @XmlAttribute(name = "responseOrder")
    protected String responseOrder;
    @XmlAttribute(name = "onError")
    protected String onError;

    /**
     * Ruft den Wert der authRequest-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link AuthRequest }
     *     
     */
    public AuthRequest getAuthRequest() {
        return authRequest;
    }

    /**
     * Legt den Wert der authRequest-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link AuthRequest }
     *     
     */
    public void setAuthRequest(AuthRequest value) {
        this.authRequest = value;
    }

    /**
     * Gets the value of the batchRequests property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the batchRequests property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getBatchRequests().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link SearchRequest }
     * {@link ModifyRequest }
     * {@link AddRequest }
     * {@link DelRequest }
     * {@link ModifyDNRequest }
     * {@link CompareRequest }
     * {@link AbandonRequest }
     * {@link ExtendedRequest }
     * 
     * 
     */
    public List<DsmlMessage> getBatchRequests() {
        if (batchRequests == null) {
            batchRequests = new ArrayList<DsmlMessage>();
        }
        return this.batchRequests;
    }

    /**
     * Ruft den Wert der requestID-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRequestID() {
        return requestID;
    }

    /**
     * Legt den Wert der requestID-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRequestID(String value) {
        this.requestID = value;
    }

    /**
     * Ruft den Wert der processing-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProcessing() {
        if (processing == null) {
            return "sequential";
        } else {
            return processing;
        }
    }

    /**
     * Legt den Wert der processing-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProcessing(String value) {
        this.processing = value;
    }

    /**
     * Ruft den Wert der responseOrder-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getResponseOrder() {
        if (responseOrder == null) {
            return "sequential";
        } else {
            return responseOrder;
        }
    }

    /**
     * Legt den Wert der responseOrder-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setResponseOrder(String value) {
        this.responseOrder = value;
    }

    /**
     * Ruft den Wert der onError-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOnError() {
        if (onError == null) {
            return "exit";
        } else {
            return onError;
        }
    }

    /**
     * Legt den Wert der onError-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOnError(String value) {
        this.onError = value;
    }

}
