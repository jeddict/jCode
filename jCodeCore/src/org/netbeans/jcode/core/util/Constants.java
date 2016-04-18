/**
 * Copyright [2016] Gaurav Gupta
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
package org.netbeans.jcode.core.util;

import com.sun.source.tree.ExpressionTree;
import javax.lang.model.element.Modifier;
import org.netbeans.api.java.source.TreeMaker;
import org.netbeans.modules.websvc.rest.model.api.RestConstants;

public class Constants {

    public static final String WEB_INF = "WEB-INF";        // NOI18N
    public static final String META_INF = "META-INF";        // NOI18N
    public static final String LOGGER = "java.util.logging.Logger";
    public static final String POST_CONSTRUCT = "javax.annotation.PostConstruct";

    public static final String NAMED = "javax.inject.Named"; //NOI18N
    public static final String RESOURCE_SUFFIX = "Resource"; // NOI18N

    public static final String XML_TRANSIENT_ANNOTATION = "XmlTransient"; //NOI18N

    public static final String XML_ROOT_ELEMENT_ANNOTATION = "XmlRootElement";  //NOI18N

    public static final String XML_ELEMENT_ANNOTATION = "XmlElement";  //NOI18N

    public static final String XML_ATTRIBUTE_ANNOTATION = "XmlAttribute";  //NOI18N

    public static final String PERSISTENCE_CONTEXT_ANNOTATION = "PersistenceContext";       //NOI18N

    public static final String URI_TYPE = "java.net.URI";       //NOI18N

    public static final String PERSISTENCE_PACKAGE = "javax.persistence.";      //NOI18N

    public static final String QUERY_TYPE = PERSISTENCE_PACKAGE + "Query";       //NOI18N

    public static final String ENTITY_MANAGER_TYPE = PERSISTENCE_PACKAGE + "EntityManager";       //NOI18N

    public static final String ENTITY_MANAGER_FACTORY = PERSISTENCE_PACKAGE + "EntityManagerFactory";       //NOI18N

    public static final String ENTITY_TRANSACTION = PERSISTENCE_PACKAGE + "EntityTransaction";      //NOI18N

    public static final String PERSISTENCE = PERSISTENCE_PACKAGE + "Persistence";       //NOI18N

    public static final String PERSISTENCE_CONTEXT = PERSISTENCE_PACKAGE + PERSISTENCE_CONTEXT_ANNOTATION;

    public static final String PERSISTENCE_ENTITY = PERSISTENCE_PACKAGE + "Entity";     //NOI18N

    public static final String PERSISTENCE_TABLE = PERSISTENCE_PACKAGE + "Table";       //NOI18M

    public static final String NO_RESULT_EXCEPTION = PERSISTENCE_PACKAGE + "NoResultException";        //NOI18N

    public static final String XML_ANNOTATION_PACKAGE = "javax.xml.bind.annotation.";       //NOI18N

    public static final String XML_ROOT_ELEMENT = XML_ANNOTATION_PACKAGE + XML_ROOT_ELEMENT_ANNOTATION;

    public static final String XML_ELEMENT = XML_ANNOTATION_PACKAGE + XML_ELEMENT_ANNOTATION;

    public static final String XML_ATTRIBUTE = XML_ANNOTATION_PACKAGE + XML_ATTRIBUTE_ANNOTATION;

    public static final String XML_TRANSIENT = XML_ANNOTATION_PACKAGE + XML_TRANSIENT_ANNOTATION;

    public static final String VOID = "void";           //NOI18N

    public static final String COLLECTION = "Collection"; //NOI18N

    public static final String COLLECTION_TYPE = "java.util.Collection"; //NOI18N

    public static final String COLLECTIONS_TYPE = "java.util.Collections";  //NOI18N

    public static final String LIST_TYPE = "java.util.List";        //NOI18N

    public static final String SET_TYPE = "java.util.Set";      //NOI18N

    public static final String ARRAY_LIST_TYPE = "java.util.ArrayList"; //NOI18N

    public static final String HASH_SET_TYPE = "java.util.HashSet"; //NOI18N

    public static final String REQUEST_SCOPE = "javax.enterprise.context.RequestScoped"; //NOI18N

    static final Modifier[] PUBLIC = new Modifier[]{Modifier.PUBLIC};

    public static final Modifier[] PRIVATE = new Modifier[]{Modifier.PRIVATE};

    public static final Modifier[] PUBLIC_STATIC_FINAL = new Modifier[]{
        Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL
    };

    public static final String JAVA_EXT = "java"; //NI18N

    public enum MimeType {

        XML("application/xml", "Xml", "APPLICATION_XML"), // NOI18N
        JSON("application/json", "Json", "APPLICATION_JSON"), // NOI18N
        TEXT("text/plain", "Text", "TEXT_PLAIN"), // NOI18N
        HTML("text/html", "Html", "TEXT_HTML"), // NOI18N
        IMAGE("image/png", "Image", null); // NOI18N

        private final String value;
        private final String suffix;
        private final String mediaTypeField;

        MimeType(String value, String suffix, String mediaTypeField) {
            this.value = value;
            this.suffix = suffix;
            this.mediaTypeField = mediaTypeField;
        }

        public String value() {
            return value;
        }

        public String suffix() {
            return suffix;
        }

        public ExpressionTree expressionTree(TreeMaker maker) {
            ExpressionTree tree;
            if (mediaTypeField == null) {
                tree = maker.Literal(value());
            } else {
                // Use a field of MediaType class if possible
                ExpressionTree typeTree = maker.QualIdent("javax.ws.rs.core.MediaType"); // NOI18N
                tree = maker.MemberSelect(typeTree, mediaTypeField);
            }
            return tree;
        }

        public static MimeType find(String value) {
            for (MimeType m : values()) {
                if (m.value().equals(value)) {
                    return m;
                }
            }
            return null;
        }

        @Override
        public String toString() {
            return value;
        }
    }

    public enum HttpMethodType {

        GET("get", RestConstants.GET), //NOI18N
        PUT("put", RestConstants.PUT), //NOI18N
        POST("post", RestConstants.POST), //NOI18N
        DELETE("delete", RestConstants.DELETE); //NOI18N

        private final String prefix;
        private final String annotationType;

        HttpMethodType(String prefix, String annotationType) {
            this.prefix = prefix;
            this.annotationType = annotationType;
        }

        public String value() {
            return name();
        }

        public String prefix() {
            return prefix;
        }

        public String getAnnotationType() {
            return annotationType;
        }
    }

    public static final String REST_STUBS_DIR = "rest"; //NOI18N

    public static final String PASSWORD = "password"; //NOI18N
}
