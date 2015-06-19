
package oasis.names.tc.dsml._2._0.core;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java-Klasse für Filter complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="Filter">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;group ref="{urn:oasis:names:tc:DSML:2:0:core}FilterGroup"/>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Filter", propOrder = {
    "and",
    "or",
    "not",
    "equalityMatch",
    "substrings",
    "greaterOrEqual",
    "lessOrEqual",
    "present",
    "approxMatch",
    "extensibleMatch"
})
public class Filter {

    protected FilterSet and;
    protected FilterSet or;
    protected Filter not;
    protected AttributeValueAssertion equalityMatch;
    protected SubstringFilter substrings;
    protected AttributeValueAssertion greaterOrEqual;
    protected AttributeValueAssertion lessOrEqual;
    protected AttributeDescription present;
    protected AttributeValueAssertion approxMatch;
    protected MatchingRuleAssertion extensibleMatch;

    /**
     * Ruft den Wert der and-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link FilterSet }
     *     
     */
    public FilterSet getAnd() {
        return and;
    }

    /**
     * Legt den Wert der and-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link FilterSet }
     *     
     */
    public void setAnd(FilterSet value) {
        this.and = value;
    }

    /**
     * Ruft den Wert der or-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link FilterSet }
     *     
     */
    public FilterSet getOr() {
        return or;
    }

    /**
     * Legt den Wert der or-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link FilterSet }
     *     
     */
    public void setOr(FilterSet value) {
        this.or = value;
    }

    /**
     * Ruft den Wert der not-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Filter }
     *     
     */
    public Filter getNot() {
        return not;
    }

    /**
     * Legt den Wert der not-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Filter }
     *     
     */
    public void setNot(Filter value) {
        this.not = value;
    }

    /**
     * Ruft den Wert der equalityMatch-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link AttributeValueAssertion }
     *     
     */
    public AttributeValueAssertion getEqualityMatch() {
        return equalityMatch;
    }

    /**
     * Legt den Wert der equalityMatch-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link AttributeValueAssertion }
     *     
     */
    public void setEqualityMatch(AttributeValueAssertion value) {
        this.equalityMatch = value;
    }

    /**
     * Ruft den Wert der substrings-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link SubstringFilter }
     *     
     */
    public SubstringFilter getSubstrings() {
        return substrings;
    }

    /**
     * Legt den Wert der substrings-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link SubstringFilter }
     *     
     */
    public void setSubstrings(SubstringFilter value) {
        this.substrings = value;
    }

    /**
     * Ruft den Wert der greaterOrEqual-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link AttributeValueAssertion }
     *     
     */
    public AttributeValueAssertion getGreaterOrEqual() {
        return greaterOrEqual;
    }

    /**
     * Legt den Wert der greaterOrEqual-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link AttributeValueAssertion }
     *     
     */
    public void setGreaterOrEqual(AttributeValueAssertion value) {
        this.greaterOrEqual = value;
    }

    /**
     * Ruft den Wert der lessOrEqual-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link AttributeValueAssertion }
     *     
     */
    public AttributeValueAssertion getLessOrEqual() {
        return lessOrEqual;
    }

    /**
     * Legt den Wert der lessOrEqual-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link AttributeValueAssertion }
     *     
     */
    public void setLessOrEqual(AttributeValueAssertion value) {
        this.lessOrEqual = value;
    }

    /**
     * Ruft den Wert der present-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link AttributeDescription }
     *     
     */
    public AttributeDescription getPresent() {
        return present;
    }

    /**
     * Legt den Wert der present-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link AttributeDescription }
     *     
     */
    public void setPresent(AttributeDescription value) {
        this.present = value;
    }

    /**
     * Ruft den Wert der approxMatch-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link AttributeValueAssertion }
     *     
     */
    public AttributeValueAssertion getApproxMatch() {
        return approxMatch;
    }

    /**
     * Legt den Wert der approxMatch-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link AttributeValueAssertion }
     *     
     */
    public void setApproxMatch(AttributeValueAssertion value) {
        this.approxMatch = value;
    }

    /**
     * Ruft den Wert der extensibleMatch-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link MatchingRuleAssertion }
     *     
     */
    public MatchingRuleAssertion getExtensibleMatch() {
        return extensibleMatch;
    }

    /**
     * Legt den Wert der extensibleMatch-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link MatchingRuleAssertion }
     *     
     */
    public void setExtensibleMatch(MatchingRuleAssertion value) {
        this.extensibleMatch = value;
    }

}
