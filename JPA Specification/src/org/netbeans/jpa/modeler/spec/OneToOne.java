//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a>
// Any modifications to this file will be lost upon recompilation of the source schema.
// Generated on: 2014.01.21 at 01:52:19 PM IST
//
package org.netbeans.jpa.modeler.spec;

import java.util.ArrayList;
import java.util.List;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.VariableElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import org.apache.commons.lang.StringUtils;
import static org.netbeans.jcode.jpa.JPAConstants.ONE_TO_ONE_FQN;
import org.netbeans.jpa.modeler.spec.extend.SingleRelationAttribute;
import org.netbeans.jpa.source.JavaSourceParserUtil;

/**
 *
 *
 * @Target({METHOD, FIELD}) @Retention(RUNTIME) public @interface OneToOne {
 * Class targetEntity() default void.class; CascadeType[] cascade() default {};
 * FetchType fetch() default EAGER; boolean optional() default true; String
 * mappedBy() default ""; boolean orphanRemoval() default false; }
 *
 *
 *
 * <p>
 * Java class for one-to-one complex type.
 *
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 *
 * <pre>
 * &lt;complexType name="one-to-one">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;choice>
 *           &lt;sequence>
 *             &lt;element name="primary-key-join-column" type="{http://xmlns.jcp.org/xml/ns/persistence/orm}primary-key-join-column" maxOccurs="unbounded" minOccurs="0"/>
 *             &lt;element name="primary-key-foreign-key" type="{http://xmlns.jcp.org/xml/ns/persistence/orm}foreign-key" minOccurs="0"/>
 *           &lt;/sequence>
 *           &lt;sequence>
 *             &lt;element name="join-column" type="{http://xmlns.jcp.org/xml/ns/persistence/orm}join-column" maxOccurs="unbounded" minOccurs="0"/>
 *             &lt;element name="foreign-key" type="{http://xmlns.jcp.org/xml/ns/persistence/orm}foreign-key" minOccurs="0"/>
 *           &lt;/sequence>
 *           &lt;element name="join-table" type="{http://xmlns.jcp.org/xml/ns/persistence/orm}join-table" minOccurs="0"/>
 *         &lt;/choice>
 *         &lt;element name="cascade" type="{http://xmlns.jcp.org/xml/ns/persistence/orm}cascade-type" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="name" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="target-entity" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="fetch" type="{http://xmlns.jcp.org/xml/ns/persistence/orm}fetch-type" />
 *       &lt;attribute name="optional" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *       &lt;attribute name="access" type="{http://xmlns.jcp.org/xml/ns/persistence/orm}access-type" />
 *       &lt;attribute name="mapped-by" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="orphan-removal" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *       &lt;attribute name="maps-id" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="id" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "one-to-one", propOrder = {
    "primaryKeyJoinColumn",
    "primaryKeyForeignKey"
})
@XmlRootElement
public class OneToOne extends SingleRelationAttribute {

    @XmlElement(name = "pk-jc")
    protected List<PrimaryKeyJoinColumn> primaryKeyJoinColumn;//REVENG PENDING
    @XmlElement(name = "pk-fk")
    protected ForeignKey primaryKeyForeignKey;//REVENG PENDING

    @XmlAttribute(name = "own")
    private Boolean owner;//default true/null
    @XmlTransient//(name = "mapped-by")
    protected String mappedBy;
    @XmlAttribute(name = "orp")
    protected Boolean orphanRemoval;

    @Override
    public OneToOne load(EntityMappings entityMappings, Element element, VariableElement variableElement, ExecutableElement getterElement, AnnotationMirror annotationMirror) {
        if(annotationMirror==null){
          annotationMirror = JavaSourceParserUtil.findAnnotation(element, ONE_TO_ONE_FQN);
        }
        super.loadAttribute(entityMappings, element, variableElement, getterElement, annotationMirror);
        this.mappedBy = (String) JavaSourceParserUtil.findAnnotationValue(annotationMirror, "mappedBy");
        this.orphanRemoval = (Boolean) JavaSourceParserUtil.findAnnotationValue(annotationMirror, "orphanRemoval");
        return this;
    }

    /**
     * Gets the value of the primaryKeyJoinColumn property.
     *
     * <p>
     * This accessor method returns a reference to the live list, not a
     * snapshot. Therefore any modification you make to the returned list will
     * be present inside the JAXB object. This is why there is not a
     * <CODE>set</CODE> method for the primaryKeyJoinColumn property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPrimaryKeyJoinColumn().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PrimaryKeyJoinColumn }
     *
     *
     */
    public List<PrimaryKeyJoinColumn> getPrimaryKeyJoinColumn() {
        if (primaryKeyJoinColumn == null) {
            primaryKeyJoinColumn = new ArrayList<PrimaryKeyJoinColumn>();
        }
        return this.primaryKeyJoinColumn;
    }

    /**
     * Gets the value of the primaryKeyForeignKey property.
     *
     * @return possible object is {@link ForeignKey }
     *
     */
    public ForeignKey getPrimaryKeyForeignKey() {
        return primaryKeyForeignKey;
    }

    /**
     * Sets the value of the primaryKeyForeignKey property.
     *
     * @param value allowed object is {@link ForeignKey }
     *
     */
    public void setPrimaryKeyForeignKey(ForeignKey value) {
        this.primaryKeyForeignKey = value;
    }

    /**
     * Gets the value of the mappedBy property.
     *
     * @return possible object is {@link String }
     *
     */
    public String getMappedBy() {
        if (Boolean.FALSE.equals(isOwner())) {
            if (mappedBy != null) {
                return mappedBy;
            }
            if (getConnectedAttribute() != null) {
                return getConnectedAttribute().getName();
            }
        }
        return null;
    }

    /**
     * Sets the value of the mappedBy property.
     *
     * @param value allowed object is {@link String }
     *
     */
      public void setMappedBy(String value) {
        this.mappedBy = value;
        this.owner =  StringUtils.isBlank(mappedBy);
    }

    /**
     * Gets the value of the orphanRemoval property.
     *
     * @return possible object is {@link Boolean }
     *
     */
    public Boolean getOrphanRemoval() {
        if(orphanRemoval == null){
            orphanRemoval = false;
        }
        return orphanRemoval;
    }

    /**
     * Sets the value of the orphanRemoval property.
     *
     * @param value allowed object is {@link Boolean }
     *
     */
    public void setOrphanRemoval(Boolean value) {
        this.orphanRemoval = value;
    }

//    @Override
//    public List<JaxbVariableType> getJaxbVariableList() {
//        if (mappedBy != null && !mappedBy.trim().isEmpty()) {
//            return null;
//        } else {
//            return super.getJaxbVariableList();
//        }
//    }

    /**
     * @return the owner
     */
    public boolean isOwner() {
        if (owner == null) {
            return Boolean.FALSE;
        }
        return owner;
    }

    /**
     * @param owner the owner to set
     */
    public void setOwner(boolean owner) {
        this.owner = owner;
        if(owner){
            mappedBy = null;
        } 
        
    }

}