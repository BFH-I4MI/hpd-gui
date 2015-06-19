
package oasis.names.tc.dsml._2._0.core;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementRefs;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java-Klasse für FilterSet complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="FilterSet">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;group ref="{urn:oasis:names:tc:DSML:2:0:core}FilterGroup" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FilterSet", propOrder = {
    "filterGroup"
})
public class FilterSet {

    @XmlElementRefs({
        @XmlElementRef(name = "and", namespace = "urn:oasis:names:tc:DSML:2:0:core", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "or", namespace = "urn:oasis:names:tc:DSML:2:0:core", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "not", namespace = "urn:oasis:names:tc:DSML:2:0:core", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "present", namespace = "urn:oasis:names:tc:DSML:2:0:core", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "substrings", namespace = "urn:oasis:names:tc:DSML:2:0:core", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "greaterOrEqual", namespace = "urn:oasis:names:tc:DSML:2:0:core", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "lessOrEqual", namespace = "urn:oasis:names:tc:DSML:2:0:core", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "extensibleMatch", namespace = "urn:oasis:names:tc:DSML:2:0:core", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "approxMatch", namespace = "urn:oasis:names:tc:DSML:2:0:core", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "equalityMatch", namespace = "urn:oasis:names:tc:DSML:2:0:core", type = JAXBElement.class, required = false)
    })
    protected List<JAXBElement<?>> filterGroup;

    /**
     * Gets the value of the filterGroup property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the filterGroup property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFilterGroup().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link JAXBElement }{@code <}{@link FilterSet }{@code >}
     * {@link JAXBElement }{@code <}{@link FilterSet }{@code >}
     * {@link JAXBElement }{@code <}{@link Filter }{@code >}
     * {@link JAXBElement }{@code <}{@link AttributeDescription }{@code >}
     * {@link JAXBElement }{@code <}{@link SubstringFilter }{@code >}
     * {@link JAXBElement }{@code <}{@link AttributeValueAssertion }{@code >}
     * {@link JAXBElement }{@code <}{@link AttributeValueAssertion }{@code >}
     * {@link JAXBElement }{@code <}{@link MatchingRuleAssertion }{@code >}
     * {@link JAXBElement }{@code <}{@link AttributeValueAssertion }{@code >}
     * {@link JAXBElement }{@code <}{@link AttributeValueAssertion }{@code >}
     * 
     * 
     */
    public List<JAXBElement<?>> getFilterGroup() {
        if (filterGroup == null) {
            filterGroup = new ArrayList<JAXBElement<?>>();
        }
        return this.filterGroup;
    }

}
