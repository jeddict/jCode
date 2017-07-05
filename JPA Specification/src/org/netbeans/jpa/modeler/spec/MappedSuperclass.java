//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a>
// Any modifications to this file will be lost upon recompilation of the source schema.
// Generated on: 2014.01.21 at 01:52:19 PM IST
//
package org.netbeans.jpa.modeler.spec;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.lang.model.element.TypeElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 *
 *
 * Defines the settings and mappings for a mapped superclass. Is allowed to be
 * sparsely populated and used in conjunction with the annotations.
 * Alternatively, the metadata-complete attribute can be used to indicate that
 * no annotations are to be processed If this is the case then the defaulting
 * rules will be recursively applied.
 *
 * @Target(TYPE) @Retention(RUNTIME) public @interface MappedSuperclass{}
 *
 *
 *
 * <p>
 * Java class for mapped-superclass complex type.
 *
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 *
 * <pre>
 * &lt;complexType name="mapped-superclass">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="id-class" type="{http://java.sun.com/xml/ns/persistence/orm}id-class" minOccurs="0"/>
 *         &lt;element name="exclude-default-listeners" type="{http://java.sun.com/xml/ns/persistence/orm}emptyType" minOccurs="0"/>
 *         &lt;element name="exclude-superclass-listeners" type="{http://java.sun.com/xml/ns/persistence/orm}emptyType" minOccurs="0"/>
 *         &lt;element name="entity-listeners" type="{http://java.sun.com/xml/ns/persistence/orm}entity-listeners" minOccurs="0"/>
 *         &lt;element name="pre-persist" type="{http://java.sun.com/xml/ns/persistence/orm}pre-persist" minOccurs="0"/>
 *         &lt;element name="post-persist" type="{http://java.sun.com/xml/ns/persistence/orm}post-persist" minOccurs="0"/>
 *         &lt;element name="pre-remove" type="{http://java.sun.com/xml/ns/persistence/orm}pre-remove" minOccurs="0"/>
 *         &lt;element name="post-remove" type="{http://java.sun.com/xml/ns/persistence/orm}post-remove" minOccurs="0"/>
 *         &lt;element name="pre-update" type="{http://java.sun.com/xml/ns/persistence/orm}pre-update" minOccurs="0"/>
 *         &lt;element name="post-update" type="{http://java.sun.com/xml/ns/persistence/orm}post-update" minOccurs="0"/>
 *         &lt;element name="post-load" type="{http://java.sun.com/xml/ns/persistence/orm}post-load" minOccurs="0"/>
 *         &lt;element name="attributes" type="{http://java.sun.com/xml/ns/persistence/orm}attributes" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="class" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="access" type="{http://java.sun.com/xml/ns/persistence/orm}access-type" />
 *       &lt;attribute name="metadata-complete" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "mapped-superclass", propOrder = { //    "description",
//    "excludeDefaultListeners",
//    "excludeSuperclassListeners",
//    "entityListeners",
//    "prePersist",
//    "postPersist",
//    "preRemove",
//    "postRemove",
//    "preUpdate",
//    "postUpdate",
//    "postLoad",
//    "attributes"
})
@XmlRootElement
public class MappedSuperclass extends IdentifiableClass {

    @Override
    public void load(EntityMappings entityMappings, TypeElement element, boolean fieldAccess) {
        if (!entityMappings.findMappedSuperclass(element.getSimpleName().toString()).isPresent()) {
            super.load(entityMappings, element, fieldAccess);
        }
    }

    public List<NamedQuery> getNamedQuery() {
        return Collections.EMPTY_LIST;
    }

    public List<NamedNativeQuery> getNamedNativeQuery() {
        return Collections.EMPTY_LIST;
    }

    public List<NamedStoredProcedureQuery> getNamedStoredProcedureQuery() {
        return Collections.EMPTY_LIST;
    }

}
