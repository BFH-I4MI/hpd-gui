
package oasis.names.tc.dsml._2._0.core;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java-Klasse für LDAPResultCode.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * <p>
 * <pre>
 * &lt;simpleType name="LDAPResultCode">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="success"/>
 *     &lt;enumeration value="operationsError"/>
 *     &lt;enumeration value="protocolError"/>
 *     &lt;enumeration value="timeLimitExceeded"/>
 *     &lt;enumeration value="sizeLimitExceeded"/>
 *     &lt;enumeration value="compareFalse"/>
 *     &lt;enumeration value="compareTrue"/>
 *     &lt;enumeration value="authMethodNotSupported"/>
 *     &lt;enumeration value="strongAuthRequired"/>
 *     &lt;enumeration value="referral"/>
 *     &lt;enumeration value="adminLimitExceeded"/>
 *     &lt;enumeration value="unavailableCriticalExtension"/>
 *     &lt;enumeration value="confidentialityRequired"/>
 *     &lt;enumeration value="saslBindInProgress"/>
 *     &lt;enumeration value="noSuchAttribute"/>
 *     &lt;enumeration value="undefinedAttributeType"/>
 *     &lt;enumeration value="inappropriateMatching"/>
 *     &lt;enumeration value="constraintViolation"/>
 *     &lt;enumeration value="attributeOrValueExists"/>
 *     &lt;enumeration value="invalidAttributeSyntax"/>
 *     &lt;enumeration value="noSuchObject"/>
 *     &lt;enumeration value="aliasProblem"/>
 *     &lt;enumeration value="invalidDNSyntax"/>
 *     &lt;enumeration value="aliasDerefencingProblem"/>
 *     &lt;enumeration value="inappropriateAuthentication"/>
 *     &lt;enumeration value="invalidCredentials"/>
 *     &lt;enumeration value="insufficientAccessRights"/>
 *     &lt;enumeration value="busy"/>
 *     &lt;enumeration value="unavailable"/>
 *     &lt;enumeration value="unwillingToPerform"/>
 *     &lt;enumeration value="loopDetect"/>
 *     &lt;enumeration value="namingViolation"/>
 *     &lt;enumeration value="objectClassViolation"/>
 *     &lt;enumeration value="notAllowedOnNonLeaf"/>
 *     &lt;enumeration value="notAllowedOnRDN"/>
 *     &lt;enumeration value="entryAlreadyExists"/>
 *     &lt;enumeration value="objectClassModsProhibited"/>
 *     &lt;enumeration value="affectMultipleDSAs"/>
 *     &lt;enumeration value="other"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "LDAPResultCode")
@XmlEnum
public enum LDAPResultCode {

    @XmlEnumValue("success")
    SUCCESS("success"),
    @XmlEnumValue("operationsError")
    OPERATIONS_ERROR("operationsError"),
    @XmlEnumValue("protocolError")
    PROTOCOL_ERROR("protocolError"),
    @XmlEnumValue("timeLimitExceeded")
    TIME_LIMIT_EXCEEDED("timeLimitExceeded"),
    @XmlEnumValue("sizeLimitExceeded")
    SIZE_LIMIT_EXCEEDED("sizeLimitExceeded"),
    @XmlEnumValue("compareFalse")
    COMPARE_FALSE("compareFalse"),
    @XmlEnumValue("compareTrue")
    COMPARE_TRUE("compareTrue"),
    @XmlEnumValue("authMethodNotSupported")
    AUTH_METHOD_NOT_SUPPORTED("authMethodNotSupported"),
    @XmlEnumValue("strongAuthRequired")
    STRONG_AUTH_REQUIRED("strongAuthRequired"),
    @XmlEnumValue("referral")
    REFERRAL("referral"),
    @XmlEnumValue("adminLimitExceeded")
    ADMIN_LIMIT_EXCEEDED("adminLimitExceeded"),
    @XmlEnumValue("unavailableCriticalExtension")
    UNAVAILABLE_CRITICAL_EXTENSION("unavailableCriticalExtension"),
    @XmlEnumValue("confidentialityRequired")
    CONFIDENTIALITY_REQUIRED("confidentialityRequired"),
    @XmlEnumValue("saslBindInProgress")
    SASL_BIND_IN_PROGRESS("saslBindInProgress"),
    @XmlEnumValue("noSuchAttribute")
    NO_SUCH_ATTRIBUTE("noSuchAttribute"),
    @XmlEnumValue("undefinedAttributeType")
    UNDEFINED_ATTRIBUTE_TYPE("undefinedAttributeType"),
    @XmlEnumValue("inappropriateMatching")
    INAPPROPRIATE_MATCHING("inappropriateMatching"),
    @XmlEnumValue("constraintViolation")
    CONSTRAINT_VIOLATION("constraintViolation"),
    @XmlEnumValue("attributeOrValueExists")
    ATTRIBUTE_OR_VALUE_EXISTS("attributeOrValueExists"),
    @XmlEnumValue("invalidAttributeSyntax")
    INVALID_ATTRIBUTE_SYNTAX("invalidAttributeSyntax"),
    @XmlEnumValue("noSuchObject")
    NO_SUCH_OBJECT("noSuchObject"),
    @XmlEnumValue("aliasProblem")
    ALIAS_PROBLEM("aliasProblem"),
    @XmlEnumValue("invalidDNSyntax")
    INVALID_DN_SYNTAX("invalidDNSyntax"),
    @XmlEnumValue("aliasDerefencingProblem")
    ALIAS_DEREFENCING_PROBLEM("aliasDerefencingProblem"),
    @XmlEnumValue("inappropriateAuthentication")
    INAPPROPRIATE_AUTHENTICATION("inappropriateAuthentication"),
    @XmlEnumValue("invalidCredentials")
    INVALID_CREDENTIALS("invalidCredentials"),
    @XmlEnumValue("insufficientAccessRights")
    INSUFFICIENT_ACCESS_RIGHTS("insufficientAccessRights"),
    @XmlEnumValue("busy")
    BUSY("busy"),
    @XmlEnumValue("unavailable")
    UNAVAILABLE("unavailable"),
    @XmlEnumValue("unwillingToPerform")
    UNWILLING_TO_PERFORM("unwillingToPerform"),
    @XmlEnumValue("loopDetect")
    LOOP_DETECT("loopDetect"),
    @XmlEnumValue("namingViolation")
    NAMING_VIOLATION("namingViolation"),
    @XmlEnumValue("objectClassViolation")
    OBJECT_CLASS_VIOLATION("objectClassViolation"),
    @XmlEnumValue("notAllowedOnNonLeaf")
    NOT_ALLOWED_ON_NON_LEAF("notAllowedOnNonLeaf"),
    @XmlEnumValue("notAllowedOnRDN")
    NOT_ALLOWED_ON_RDN("notAllowedOnRDN"),
    @XmlEnumValue("entryAlreadyExists")
    ENTRY_ALREADY_EXISTS("entryAlreadyExists"),
    @XmlEnumValue("objectClassModsProhibited")
    OBJECT_CLASS_MODS_PROHIBITED("objectClassModsProhibited"),
    @XmlEnumValue("affectMultipleDSAs")
    AFFECT_MULTIPLE_DS_AS("affectMultipleDSAs"),
    @XmlEnumValue("other")
    OTHER("other");
    private final String value;

    LDAPResultCode(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static LDAPResultCode fromValue(String v) {
        for (LDAPResultCode c: LDAPResultCode.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
