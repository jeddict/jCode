/**
 * Copyright [2014] Gaurav Gupta
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.netbeans.jpa.modeler.spec.extend;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.eclipse.persistence.internal.jpa.metadata.accessors.classes.XMLAttributes;
import org.netbeans.jpa.modeler.spec.Basic;
import org.netbeans.jpa.modeler.spec.ElementCollection;
import org.netbeans.jpa.modeler.spec.Embedded;
import org.netbeans.jpa.modeler.spec.Entity;
import org.netbeans.jpa.modeler.spec.ManyToMany;
import org.netbeans.jpa.modeler.spec.ManyToOne;
import org.netbeans.jpa.modeler.spec.OneToMany;
import org.netbeans.jpa.modeler.spec.OneToOne;
import org.netbeans.jpa.modeler.spec.Transient;
import org.netbeans.jpa.modeler.spec.workspace.WorkSpace;

/**
 *
 * @author Gaurav_Gupta
 */
public interface IPersistenceAttributes extends IAttributes {

    List<Basic> getBasic();

    void setBasic(List<Basic> basic);

    void addBasic(Basic basic);

    void removeBasic(Basic basic);

    Optional<Basic> getBasic(String id);

    void addTransient(Transient _transient);

    void removeTransient(Transient _transient);

    List<Transient> getTransient();

    Optional<Transient> getTransient(String id);

    void setTransient(List<Transient> _transient);

    List<Embedded> getEmbedded();

    void setEmbedded(List<Embedded> embedded);

    void addEmbedded(Embedded embedded);

    void removeEmbedded(Embedded embedded);

    Optional<Embedded> getEmbedded(String id);

    List<ElementCollection> getElementCollection();

    void setElementCollection(List<ElementCollection> elementCollection);

    void addElementCollection(ElementCollection elementCollection);

    void removeElementCollection(ElementCollection elementCollection);

    Optional<ElementCollection> getElementCollection(String id);

    void removeRelationAttribute(RelationAttribute relationAttribute);

    void addRelationAttribute(RelationAttribute relationAttribute);

    List<Attribute> getNonRelationAttributes();

    List<RelationAttribute> getRelationAttributes();

    List<SingleRelationAttribute> getDerivedRelationAttributes();

    Optional<RelationAttribute> getRelationAttribute(String id);

    List<ManyToMany> getManyToMany();

    void setManyToMany(List<ManyToMany> manyToMany);

    Optional<ManyToMany> getManyToMany(String id);

    void addManyToMany(ManyToMany manyToMany);

    void removeManyToMany(ManyToMany manyToMany);

    List<ManyToOne> getManyToOne();

    void setManyToOne(List<ManyToOne> manyToOne);

    Optional<ManyToOne> getManyToOne(String id);

    void addManyToOne(ManyToOne manyToOne);

    void removeManyToOne(ManyToOne manyToOne);

    List<OneToMany> getOneToMany();

    void setOneToMany(List<OneToMany> oneToMany);

    Optional<OneToMany> getOneToMany(String id);

    void addOneToMany(OneToMany oneToMany);

    void removeOneToMany(OneToMany oneToMany);

    List<OneToOne> getOneToOne();

    void setOneToOne(List<OneToOne> oneToOne);

    Optional<OneToOne> getOneToOne(String id);

    void addOneToOne(OneToOne oneToOne);

    void removeOneToOne(OneToOne oneToOne);

    Set<Entity> getRelationConnectedClassRef();

    void removeNonOwnerAttribute(Set<JavaClass> filterJavaClasses);

    Set<String> getAllConvert();

    List<Basic> getSuperBasic();

    XMLAttributes getAccessor(WorkSpace workSpace);

    XMLAttributes getAccessor(WorkSpace workSpace, boolean inherit);

    XMLAttributes updateAccessor(WorkSpace workSpace, XMLAttributes attr, boolean inherit);

}
