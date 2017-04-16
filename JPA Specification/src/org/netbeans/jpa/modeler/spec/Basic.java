//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a>
// Any modifications to this file will be lost upon recompilation of the source schema.
// Generated on: 2014.01.21 at 01:52:19 PM IST
//
package org.netbeans.jpa.modeler.spec;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.VariableElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import static org.netbeans.jcode.jpa.JPAConstants.BASIC_FQN;
import org.netbeans.jpa.modeler.spec.extend.AccessTypeHandler;
import org.netbeans.jpa.modeler.spec.extend.ConvertHandler;
import org.netbeans.jpa.modeler.spec.extend.EnumTypeHandler;
import org.netbeans.jpa.modeler.spec.extend.FetchTypeHandler;
import org.netbeans.jpa.modeler.spec.extend.PersistenceBaseAttribute;
import org.netbeans.jpa.source.JavaSourceParserUtil;

/**
 *
 *
 * @Target({METHOD, FIELD}) @Retention(RUNTIME) public @interface Basic {
 * FetchType fetch() default EAGER; boolean optional() default true; }
 *
 *
 *
 * <p>
 * Java class for basic complex type.
 *
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 *
 * <pre>
 * &lt;complexType name="basic">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="column" type="{http://xmlns.jcp.org/xml/ns/persistence/orm}column" minOccurs="0"/>
 *         &lt;choice>
 *           &lt;element name="lob" type="{http://xmlns.jcp.org/xml/ns/persistence/orm}lob" minOccurs="0"/>
 *           &lt;element name="temporal" type="{http://xmlns.jcp.org/xml/ns/persistence/orm}temporal" minOccurs="0"/>
 *           &lt;element name="enumerated" type="{http://xmlns.jcp.org/xml/ns/persistence/orm}enumerated" minOccurs="0"/>
 *           &lt;element name="convert" type="{http://xmlns.jcp.org/xml/ns/persistence/orm}convert" minOccurs="0"/>
 *         &lt;/choice>
 *       &lt;/sequence>
 *       &lt;attribute name="name" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="fetch" type="{http://xmlns.jcp.org/xml/ns/persistence/orm}fetch-type" />
 *       &lt;attribute name="optional" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *       &lt;attribute name="access" type="{http://xmlns.jcp.org/xml/ns/persistence/orm}access-type" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "basic", propOrder = {
    "lob",
    "enumerated",
    "convert"
})
@XmlRootElement
public class Basic extends PersistenceBaseAttribute implements AccessTypeHandler, FetchTypeHandler, EnumTypeHandler, ConvertHandler {

    protected Lob lob;
    protected EnumType enumerated;
    protected Convert convert;//RENENF PENDING
    @XmlAttribute(name = "fetch")
    protected FetchType fetch;
    @XmlAttribute(name = "optional")
    protected Boolean optional;

    public static Basic load(Element element, VariableElement variableElement, ExecutableElement getterElement) {
        AnnotationMirror annotationMirror = JavaSourceParserUtil.getAnnotation(element, BASIC_FQN);
        Basic basic = new Basic();
        basic.loadAttribute(element, variableElement, getterElement);
        basic.lob = Lob.load(element, variableElement);
        basic.enumerated = EnumType.load(element, null);
        basic.fetch = FetchType.load(element, annotationMirror);
        if (annotationMirror != null) {
            basic.optional = (Boolean) JavaSourceParserUtil.findAnnotationValue(annotationMirror, "optional");
        }
        return basic;
    }

    /**
     * Gets the value of the lob property.
     *
     * @return possible object is {@link Lob }
     *
     */
    public Lob getLob() {
        return lob;
    }

    /**
     * Sets the value of the lob property.
     *
     * @param value allowed object is {@link Lob }
     *
     */
    public void setLob(Lob value) {
        this.lob = value;
    }

    /**
     * Gets the value of the enumerated property.
     *
     * @return possible object is {@link EnumType }
     *
     */
    public EnumType getEnumerated() {
        return enumerated;
    }

    /**
     * Sets the value of the enumerated property.
     *
     * @param value allowed object is {@link EnumType }
     *
     */
    public void setEnumerated(EnumType value) {
        this.enumerated = value;
    }

    /**
     * Gets the value of the convert property.
     *
     * @return possible object is {@link Convert }
     *
     */
    @Override
    public Convert getConvert() {
        if(convert==null){
            convert = new Convert();
        }
        return convert;
    }

    /**
     * Sets the value of the convert property.
     *
     * @param value allowed object is {@link Convert }
     *
     */
    public void setConvert(Convert value) {
        this.convert = value;
    }

    /**
     * Sets the value of the name property.
     *
     * @param value allowed object is {@link String }
     *
     */
    @Override
    public void setName(String value) {
        notifyListeners("name", this.name, this.name = value);
    }

    /**
     * Gets the value of the fetch property.
     *
     * @return possible object is {@link FetchType }
     *
     */
    @Override
    public FetchType getFetch() {
        return fetch;
    }

    /**
     * Sets the value of the fetch property.
     *
     * @param value allowed object is {@link FetchType }
     *
     */
    @Override
    public void setFetch(FetchType value) {
        this.fetch = value;
    }

    /**
     * Gets the value of the optional property.
     *
     * @return possible object is {@link Boolean }
     *
     */
    public Boolean getOptional() {//isOptional
        if(optional == null){
            return true;
        }
        return optional;
    }

    /**
     * Sets the value of the optional property.
     *
     * @param value allowed object is {@link Boolean }
     *
     */
    public void setOptional(Boolean value) {
        this.optional = value;
    }

}
