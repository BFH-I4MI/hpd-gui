
package oasis.names.tc.dsml._2._0.core;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java-Klasse für ExtendedResponse complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="ExtendedResponse">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:oasis:names:tc:DSML:2:0:core}LDAPResult">
 *       &lt;sequence>
 *         &lt;element name="responseName" type="{urn:oasis:names:tc:DSML:2:0:core}NumericOID" minOccurs="0"/>
 *         &lt;element name="response" type="{http://www.w3.org/2001/XMLSchema}anyType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ExtendedResponse", propOrder = {
    "responseName",
    "response"
})
public class ExtendedResponse
    extends LDAPResult
{

    protected String responseName;
    protected Object response;

    /**
     * Ruft den Wert der responseName-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getResponseName() {
        return responseName;
    }

    /**
     * Legt den Wert der responseName-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setResponseName(String value) {
        this.responseName = value;
    }

    /**
     * Ruft den Wert der response-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Object }
     *     
     */
    public Object getResponse() {
        return response;
    }

    /**
     * Legt den Wert der response-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Object }
     *     
     */
    public void setResponse(Object value) {
        this.response = value;
    }

}
