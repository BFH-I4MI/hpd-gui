
package oasis.names.tc.dsml._2._0.core;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java-Klasse für MatchingRuleAssertion complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="MatchingRuleAssertion">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="value" type="{urn:oasis:names:tc:DSML:2:0:core}DsmlValue"/>
 *       &lt;/sequence>
 *       &lt;attribute name="dnAttributes" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" />
 *       &lt;attribute name="matchingRule" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="name" type="{urn:oasis:names:tc:DSML:2:0:core}AttributeDescriptionValue" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MatchingRuleAssertion", propOrder = {
    "value"
})
public class MatchingRuleAssertion {

    @XmlElement(required = true)
    protected String value;
    @XmlAttribute(name = "dnAttributes")
    protected Boolean dnAttributes;
    @XmlAttribute(name = "matchingRule")
    protected String matchingRule;
    @XmlAttribute(name = "name")
    protected String name;

    /**
     * Ruft den Wert der value-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getValue() {
        return value;
    }

    /**
     * Legt den Wert der value-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * Ruft den Wert der dnAttributes-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public boolean isDnAttributes() {
        if (dnAttributes == null) {
            return false;
        } else {
            return dnAttributes;
        }
    }

    /**
     * Legt den Wert der dnAttributes-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setDnAttributes(Boolean value) {
        this.dnAttributes = value;
    }

    /**
     * Ruft den Wert der matchingRule-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMatchingRule() {
        return matchingRule;
    }

    /**
     * Legt den Wert der matchingRule-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMatchingRule(String value) {
        this.matchingRule = value;
    }

    /**
     * Ruft den Wert der name-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Legt den Wert der name-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

}
