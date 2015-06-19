
package oasis.names.tc.dsml._2._0.core;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementRefs;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java-Klasse für BatchResponse complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="BatchResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;group ref="{urn:oasis:names:tc:DSML:2:0:core}BatchResponses" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="requestID" type="{urn:oasis:names:tc:DSML:2:0:core}RequestID" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BatchResponse", propOrder = {
    "batchResponses"
})
public class BatchResponse {

    @XmlElementRefs({
        @XmlElementRef(name = "authResponse", namespace = "urn:oasis:names:tc:DSML:2:0:core", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "delResponse", namespace = "urn:oasis:names:tc:DSML:2:0:core", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "errorResponse", namespace = "urn:oasis:names:tc:DSML:2:0:core", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "compareResponse", namespace = "urn:oasis:names:tc:DSML:2:0:core", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "searchResponse", namespace = "urn:oasis:names:tc:DSML:2:0:core", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "extendedResponse", namespace = "urn:oasis:names:tc:DSML:2:0:core", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "modifyResponse", namespace = "urn:oasis:names:tc:DSML:2:0:core", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "modDNResponse", namespace = "urn:oasis:names:tc:DSML:2:0:core", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "addResponse", namespace = "urn:oasis:names:tc:DSML:2:0:core", type = JAXBElement.class, required = false)
    })
    protected List<JAXBElement<?>> batchResponses;
    @XmlAttribute(name = "requestID")
    protected String requestID;

    /**
     * Gets the value of the batchResponses property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the batchResponses property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getBatchResponses().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link JAXBElement }{@code <}{@link LDAPResult }{@code >}
     * {@link JAXBElement }{@code <}{@link LDAPResult }{@code >}
     * {@link JAXBElement }{@code <}{@link ErrorResponse }{@code >}
     * {@link JAXBElement }{@code <}{@link LDAPResult }{@code >}
     * {@link JAXBElement }{@code <}{@link SearchResponse }{@code >}
     * {@link JAXBElement }{@code <}{@link ExtendedResponse }{@code >}
     * {@link JAXBElement }{@code <}{@link LDAPResult }{@code >}
     * {@link JAXBElement }{@code <}{@link LDAPResult }{@code >}
     * {@link JAXBElement }{@code <}{@link LDAPResult }{@code >}
     * 
     * 
     */
    public List<JAXBElement<?>> getBatchResponses() {
        if (batchResponses == null) {
            batchResponses = new ArrayList<JAXBElement<?>>();
        }
        return this.batchResponses;
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

}
