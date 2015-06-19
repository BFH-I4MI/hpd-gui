
package oasis.names.tc.dsml._2._0.core;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java-Klasse für ModifyDNRequest complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="ModifyDNRequest">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:oasis:names:tc:DSML:2:0:core}DsmlMessage">
 *       &lt;attribute name="dn" use="required" type="{urn:oasis:names:tc:DSML:2:0:core}DsmlDN" />
 *       &lt;attribute name="newrdn" use="required" type="{urn:oasis:names:tc:DSML:2:0:core}DsmlRDN" />
 *       &lt;attribute name="deleteoldrdn" type="{http://www.w3.org/2001/XMLSchema}boolean" default="true" />
 *       &lt;attribute name="newSuperior" type="{urn:oasis:names:tc:DSML:2:0:core}DsmlDN" />
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ModifyDNRequest")
public class ModifyDNRequest
    extends DsmlMessage
{

    @XmlAttribute(name = "dn", required = true)
    protected String dn;
    @XmlAttribute(name = "newrdn", required = true)
    protected String newrdn;
    @XmlAttribute(name = "deleteoldrdn")
    protected Boolean deleteoldrdn;
    @XmlAttribute(name = "newSuperior")
    protected String newSuperior;

    /**
     * Ruft den Wert der dn-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDn() {
        return dn;
    }

    /**
     * Legt den Wert der dn-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDn(String value) {
        this.dn = value;
    }

    /**
     * Ruft den Wert der newrdn-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNewrdn() {
        return newrdn;
    }

    /**
     * Legt den Wert der newrdn-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNewrdn(String value) {
        this.newrdn = value;
    }

    /**
     * Ruft den Wert der deleteoldrdn-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public boolean isDeleteoldrdn() {
        if (deleteoldrdn == null) {
            return true;
        } else {
            return deleteoldrdn;
        }
    }

    /**
     * Legt den Wert der deleteoldrdn-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setDeleteoldrdn(Boolean value) {
        this.deleteoldrdn = value;
    }

    /**
     * Ruft den Wert der newSuperior-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNewSuperior() {
        return newSuperior;
    }

    /**
     * Legt den Wert der newSuperior-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNewSuperior(String value) {
        this.newSuperior = value;
    }

}
