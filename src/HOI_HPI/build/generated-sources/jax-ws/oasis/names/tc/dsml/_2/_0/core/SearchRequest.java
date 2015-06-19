
package oasis.names.tc.dsml._2._0.core;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java-Klasse für SearchRequest complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="SearchRequest">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:oasis:names:tc:DSML:2:0:core}DsmlMessage">
 *       &lt;sequence>
 *         &lt;element name="filter" type="{urn:oasis:names:tc:DSML:2:0:core}Filter"/>
 *         &lt;element name="attributes" type="{urn:oasis:names:tc:DSML:2:0:core}AttributeDescriptions" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="dn" use="required" type="{urn:oasis:names:tc:DSML:2:0:core}DsmlDN" />
 *       &lt;attribute name="scope" use="required">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *             &lt;enumeration value="baseObject"/>
 *             &lt;enumeration value="singleLevel"/>
 *             &lt;enumeration value="wholeSubtree"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="derefAliases" use="required">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *             &lt;enumeration value="neverDerefAliases"/>
 *             &lt;enumeration value="derefInSearching"/>
 *             &lt;enumeration value="derefFindingBaseObj"/>
 *             &lt;enumeration value="derefAlways"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="sizeLimit" type="{urn:oasis:names:tc:DSML:2:0:core}MAXINT" default="0" />
 *       &lt;attribute name="timeLimit" type="{urn:oasis:names:tc:DSML:2:0:core}MAXINT" default="0" />
 *       &lt;attribute name="typesOnly" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" />
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SearchRequest", propOrder = {
    "filter",
    "attributes"
})
public class SearchRequest
    extends DsmlMessage
{

    @XmlElement(required = true)
    protected Filter filter;
    protected AttributeDescriptions attributes;
    @XmlAttribute(name = "dn", required = true)
    protected String dn;
    @XmlAttribute(name = "scope", required = true)
    protected String scope;
    @XmlAttribute(name = "derefAliases", required = true)
    protected String derefAliases;
    @XmlAttribute(name = "sizeLimit")
    protected Long sizeLimit;
    @XmlAttribute(name = "timeLimit")
    protected Long timeLimit;
    @XmlAttribute(name = "typesOnly")
    protected Boolean typesOnly;

    /**
     * Ruft den Wert der filter-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Filter }
     *     
     */
    public Filter getFilter() {
        return filter;
    }

    /**
     * Legt den Wert der filter-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Filter }
     *     
     */
    public void setFilter(Filter value) {
        this.filter = value;
    }

    /**
     * Ruft den Wert der attributes-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link AttributeDescriptions }
     *     
     */
    public AttributeDescriptions getAttributes() {
        return attributes;
    }

    /**
     * Legt den Wert der attributes-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link AttributeDescriptions }
     *     
     */
    public void setAttributes(AttributeDescriptions value) {
        this.attributes = value;
    }

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
     * Ruft den Wert der scope-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getScope() {
        return scope;
    }

    /**
     * Legt den Wert der scope-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setScope(String value) {
        this.scope = value;
    }

    /**
     * Ruft den Wert der derefAliases-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDerefAliases() {
        return derefAliases;
    }

    /**
     * Legt den Wert der derefAliases-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDerefAliases(String value) {
        this.derefAliases = value;
    }

    /**
     * Ruft den Wert der sizeLimit-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public long getSizeLimit() {
        if (sizeLimit == null) {
            return  0L;
        } else {
            return sizeLimit;
        }
    }

    /**
     * Legt den Wert der sizeLimit-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setSizeLimit(Long value) {
        this.sizeLimit = value;
    }

    /**
     * Ruft den Wert der timeLimit-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public long getTimeLimit() {
        if (timeLimit == null) {
            return  0L;
        } else {
            return timeLimit;
        }
    }

    /**
     * Legt den Wert der timeLimit-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setTimeLimit(Long value) {
        this.timeLimit = value;
    }

    /**
     * Ruft den Wert der typesOnly-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public boolean isTypesOnly() {
        if (typesOnly == null) {
            return false;
        } else {
            return typesOnly;
        }
    }

    /**
     * Legt den Wert der typesOnly-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setTypesOnly(Boolean value) {
        this.typesOnly = value;
    }

}
