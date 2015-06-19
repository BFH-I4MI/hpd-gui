
package oasis.names.tc.dsml._2._0.core;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java-Klasse für ResultCode complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="ResultCode">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attribute name="code" use="required" type="{http://www.w3.org/2001/XMLSchema}int" />
 *       &lt;attribute name="descr" type="{urn:oasis:names:tc:DSML:2:0:core}LDAPResultCode" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ResultCode")
public class ResultCode {

    @XmlAttribute(name = "code", required = true)
    protected int code;
    @XmlAttribute(name = "descr")
    protected LDAPResultCode descr;

    /**
     * Ruft den Wert der code-Eigenschaft ab.
     * 
     */
    public int getCode() {
        return code;
    }

    /**
     * Legt den Wert der code-Eigenschaft fest.
     * 
     */
    public void setCode(int value) {
        this.code = value;
    }

    /**
     * Ruft den Wert der descr-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link LDAPResultCode }
     *     
     */
    public LDAPResultCode getDescr() {
        return descr;
    }

    /**
     * Legt den Wert der descr-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link LDAPResultCode }
     *     
     */
    public void setDescr(LDAPResultCode value) {
        this.descr = value;
    }

}
