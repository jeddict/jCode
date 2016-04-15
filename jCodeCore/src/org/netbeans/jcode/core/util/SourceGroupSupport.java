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

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.EnumSet;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import org.netbeans.api.java.classpath.ClassPath;
import org.netbeans.api.java.project.JavaProjectConstants;
import org.netbeans.api.java.queries.UnitTestForSourceQuery;
import org.netbeans.api.java.source.ClassIndex;
import org.netbeans.api.java.source.ClasspathInfo;
import org.netbeans.api.java.source.CompilationController;
import org.netbeans.api.java.source.ElementHandle;
import org.netbeans.api.java.source.ElementUtilities;
import org.netbeans.api.java.source.JavaSource;
import org.netbeans.api.java.source.SourceUtils;
import org.netbeans.api.java.source.Task;
import org.netbeans.api.java.source.ui.TypeElementFinder;
import org.netbeans.api.project.FileOwnerQuery;
import org.netbeans.api.project.Project;
import org.netbeans.api.project.ProjectUtils;
import org.netbeans.api.project.SourceGroup;
import org.netbeans.api.project.Sources;
import org.netbeans.jcode.core.util.JavaUtil;
import org.netbeans.modules.websvc.rest.spi.MiscUtilities;
import org.netbeans.spi.java.classpath.ClassPathProvider;
import org.netbeans.spi.java.classpath.support.ClassPathSupport;
import org.openide.ErrorManager;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;
import org.openide.filesystems.URLMapper;

public class SourceGroupSupport {

    private SourceGroupSupport() {
    }

    public static SourceGroup[] getJavaSourceGroups(Project project) {
        SourceGroup[] sourceGroups = ProjectUtils.getSources(project).getSourceGroups(
                JavaProjectConstants.SOURCES_TYPE_JAVA);
        Set testGroups = getTestSourceGroups(sourceGroups);
        List result = new ArrayList();
        for (int i = 0; i < sourceGroups.length; i++) {
            if (!testGroups.contains(sourceGroups[i])) {
                result.add(sourceGroups[i]);
            }
        }
        return (SourceGroup[]) result.toArray(new SourceGroup[result.size()]);
    }

  

    // returns true if the folder is writable or is in a writable parent dir 
    // but does not yet exist
    public static boolean isFolderWritable(SourceGroup sourceGroup, String packageName) {
        try {
            FileObject fo = getFolderForPackage(sourceGroup, packageName, false);

            while ((fo == null) && (packageName.lastIndexOf('.') != -1)) {
                packageName = packageName.substring(0, packageName.lastIndexOf('.'));
                fo = getFolderForPackage(sourceGroup, packageName, false);
            }
            return ((fo == null) || fo.canWrite());
        } catch (IOException ex) {
            return false;
        }
    }

    public static SourceGroup findSourceGroupForFile(Project project, FileObject folder) {
        return findSourceGroupForFile(getJavaSourceGroups(project), folder);
    }

    public static SourceGroup findSourceGroupForFile(SourceGroup[] sourceGroups, FileObject folder) {
        for (int i = 0; i < sourceGroups.length; i++) {
            if (FileUtil.isParentOf(sourceGroups[i].getRootFolder(), folder)
                    || sourceGroups[i].getRootFolder().equals(folder)) {
                return sourceGroups[i];
            }
        }
        return null;
    }

    public static String getPackageForFolder(SourceGroup sourceGroup, FileObject folder) {
        String relative = FileUtil.getRelativePath(sourceGroup.getRootFolder(), folder);
        if (relative != null) {
            return relative.replace('/', '.'); // NOI18N
        } else {
            return ""; // NOI18N
        }
    }

    public static String getPackageForFolder(FileObject folder) {
        Project project = FileOwnerQuery.getOwner(folder);
        SourceGroup[] sources = ProjectUtils.getSources(project).getSourceGroups(
                JavaProjectConstants.SOURCES_TYPE_JAVA);
        SourceGroup sg = findSourceGroupForFile(sources, folder);
        if (sg != null) {
            return getPackageForFolder(sg, folder);
        } else {
            return "";          //NOI18N
        }
    }

    public static FileObject getFolderForPackage(SourceGroup sourceGroup, String pgkName) throws IOException {
        return getFolderForPackage(sourceGroup, pgkName, false);
    }

    public static FileObject getFolderForPackage(SourceGroup sourceGroup, String pgkName, boolean create) throws IOException {
        if (sourceGroup == null || pgkName == null) {
            return null;
        }
        String relativePkgName = pgkName.replace('.', '/');
        FileObject folder = sourceGroup.getRootFolder().getFileObject(relativePkgName);
        if (folder != null) {
            return folder;
        } else if (create) {
            return FileUtil.createFolder(sourceGroup.getRootFolder(), relativePkgName);
        }
        return null;
    }
    
    public static FileObject getFolderForPackage(FileObject rootFile, String pgkName, boolean create) throws IOException {
        if (rootFile == null || pgkName == null) {
            return null;
        }
        String relativePkgName = pgkName.replace('.', '/');
        FileObject folder = rootFile.getFileObject(relativePkgName);
        if (folder != null) {
            return folder;
        } else if (create) {
            return FileUtil.createFolder(rootFile, relativePkgName);
        }
        return null;
    }

    private static Map createFoldersToSourceGroupsMap(final SourceGroup[] sourceGroups) {
        Map result;
        if (sourceGroups.length == 0) {
            result = Collections.EMPTY_MAP;
        } else {
            result = new HashMap(2 * sourceGroups.length, .5f);
            for (int i = 0; i < sourceGroups.length; i++) {
                SourceGroup sourceGroup = sourceGroups[i];
                result.put(sourceGroup.getRootFolder(), sourceGroup);
            }
        }
        return result;
    }

    private static Set/*<SourceGroup>*/ getTestSourceGroups(SourceGroup[] sourceGroups) {
        Map foldersToSourceGroupsMap = createFoldersToSourceGroupsMap(sourceGroups);
        Set testGroups = new HashSet();
        for (int i = 0; i < sourceGroups.length; i++) {
            testGroups.addAll(getTestTargets(sourceGroups[i], foldersToSourceGroupsMap));
        }
        return testGroups;
    }

    private static List/*<SourceGroup>*/ getTestTargets(SourceGroup sourceGroup, Map foldersToSourceGroupsMap) {
        final URL[] rootURLs = UnitTestForSourceQuery.findUnitTests(sourceGroup.getRootFolder());
        if (rootURLs.length == 0) {
            return new ArrayList();
        }
        List result = new ArrayList();
        List sourceRoots = getFileObjects(rootURLs, true);
        for (int i = 0; i < sourceRoots.size(); i++) {
            FileObject sourceRoot = (FileObject) sourceRoots.get(i);
            SourceGroup srcGroup = (SourceGroup) foldersToSourceGroupsMap.get(sourceRoot);
            if (srcGroup != null) {
                result.add(srcGroup);
            }
        }
        return result;
    }

    private static List/*<FileObject>*/ getFileObjects(URL[] urls, boolean quiet) {
        List result = new ArrayList();
        for (int i = 0; i < urls.length; i++) {
            FileObject sourceRoot = URLMapper.findFileObject(urls[i]);
            if (sourceRoot != null) {
                result.add(sourceRoot);
            } else if (!quiet) {
                ErrorManager.getDefault().notify(
                        ErrorManager.INFORMATIONAL,
                        new IllegalStateException("No FileObject found for the following URL: " + urls[i])); //NOI18N
            }
        }
        return result;
    }

    public static String getPackageName(String qualifiedClassName) {
        int i = qualifiedClassName.lastIndexOf('.');
        return i > 0 ? qualifiedClassName.substring(0, i) : null;
    }

    public static String getClassName(String qualifiedClassName) {
        return qualifiedClassName.substring(qualifiedClassName.lastIndexOf('.') + 1);
    }

    public static JavaSource getJavaSourceFromClassName(String qualifiedClassName, Project project) throws IOException {
        FileObject fo = getFileObjectFromClassName(qualifiedClassName, project);
        if (fo != null) {
            return JavaSource.forFileObject(fo);
        } else {
            return null;
        }
    }

    public static ElementHandle<TypeElement> getHandleClassName(String qualifiedClassName,
            Project project) throws IOException {
        FileObject root = MiscUtilities.findSourceRoot(project);
        ClassPathProvider provider = project.getLookup().lookup(
                ClassPathProvider.class);
        ClassPath sourceCp = provider.findClassPath(root, ClassPath.SOURCE);
        ClassPath compileCp = provider.findClassPath(root, ClassPath.COMPILE);
        ClassPath bootCp = provider.findClassPath(root, ClassPath.BOOT);
        ClasspathInfo cpInfo = ClasspathInfo.create(bootCp, compileCp, sourceCp);
        ClassIndex ci = cpInfo.getClassIndex();
        int beginIndex = qualifiedClassName.lastIndexOf('.') + 1;
        String simple = qualifiedClassName.substring(beginIndex);
        Set<ElementHandle<TypeElement>> handles = ci.getDeclaredTypes(
                simple, ClassIndex.NameKind.SIMPLE_NAME,
                EnumSet.of(ClassIndex.SearchScope.SOURCE,
                        ClassIndex.SearchScope.DEPENDENCIES));
        for (final ElementHandle<TypeElement> handle : handles) {
            if (qualifiedClassName.equals(handle.getQualifiedName())) {
                return handle;
            }
        }
        return null;
    }
    
    public static ElementHandle<TypeElement> getHandleClassName(String qualifiedClassName,
            SourceGroup sourceGroup) throws IOException {
        ClassPath rcp = ClassPathSupport.createClassPath(sourceGroup.getRootFolder());
        ClasspathInfo cpInfo = ClasspathInfo.create(ClassPath.EMPTY, ClassPath.EMPTY, rcp);
        ClassIndex ci = cpInfo.getClassIndex();
        int beginIndex = qualifiedClassName.lastIndexOf('.') + 1;
        String simple = qualifiedClassName.substring(beginIndex);
   
        Set<ElementHandle<TypeElement>> handles = ci.getDeclaredTypes(
                simple, ClassIndex.NameKind.SIMPLE_NAME,
                EnumSet.of(ClassIndex.SearchScope.SOURCE));
        for (final ElementHandle<TypeElement> handle : handles) {
            if (qualifiedClassName.equals(handle.getQualifiedName())) {
                return handle;
            }
        }
       
        return null;
    }

    public static FileObject getFileObjectFromClassName(String qualifiedClassName,
            Project project) throws IOException {
        final ElementHandle<TypeElement> handle = getHandleClassName(qualifiedClassName,
                project);
        if (handle == null) {
            return null;
        }
        ClassPathProvider provider = project.getLookup().lookup(
                ClassPathProvider.class);
        FileObject root = MiscUtilities.findSourceRoot(project);
        ClassPath sourceCp = provider.findClassPath(root, ClassPath.SOURCE);
        final ClassPath compileCp = provider.findClassPath(root, ClassPath.COMPILE);
        ClassPath bootCp = provider.findClassPath(root, ClassPath.BOOT);
        ClasspathInfo cpInfo = ClasspathInfo.create(bootCp, compileCp, sourceCp);
        if (qualifiedClassName.equals(handle.getQualifiedName())) {
            FileObject fo = SourceUtils.getFile(handle, cpInfo);
            if (fo != null) {
                return fo;
            }
            JavaSource javaSource = JavaSource.create(cpInfo);
            final FileObject classFo[] = new FileObject[1];
            javaSource.runUserActionTask(new Task<CompilationController>() {

                @Override
                public void run(CompilationController controller)
                        throws Exception {
                    TypeElement element = handle.resolve(controller);
                    if (element == null) {
                        return;
                    }
                    PackageElement pack = controller.getElements()
                            .getPackageOf(element);
                    if (pack == null) {
                        return;
                    }
                    String packageName = pack.getQualifiedName().toString();
                    String fqn = ElementUtilities.getBinaryName(element);
                    String className = fqn.substring(packageName.length());
                    if (className.length() > 0 && className.charAt(0) == '.') {
                        className = className.substring(1);
                    } else {
                        return;
                    }
                    int dotIndex = className.indexOf('.');
                    if (dotIndex != -1) {
                        className = className.substring(0, dotIndex);
                    }

                    String path = packageName.replace('.', '/') + '/'
                            + className + ".class"; // NOI18N
                    classFo[0] = compileCp.findResource(path);
                }

            }, true);
            return classFo[0];
        }
        return null;
    }

    public static FileObject findJavaSourceFile(Project project, String name) {
        for (SourceGroup group : getJavaSourceGroups(project)) {
            Enumeration<? extends FileObject> files = group.getRootFolder().getChildren(true);
            while (files.hasMoreElements()) {
                FileObject fo = files.nextElement();
                if ("java".equals(fo.getExt())) { //NOI18N
                    if (name.equals(fo.getName())) {
                        return fo;
                    }
                }
            }
        }
        return null;
    }

    public static List<ClassPath> gerClassPath(Project project) {
        List<ClassPath> paths = new ArrayList<ClassPath>();
        List<SourceGroup> groups = new ArrayList<SourceGroup>();
        groups.addAll(Arrays.asList(ProjectUtils.getSources(project).getSourceGroups(JavaProjectConstants.SOURCES_TYPE_JAVA)));
        ClassPathProvider cpp = project.getLookup().lookup(ClassPathProvider.class);
        for (SourceGroup group : groups) {
            ClassPath cp = cpp.findClassPath(group.getRootFolder(), ClassPath.COMPILE);
            if (cp != null) {
                paths.add(cp);
            }
            cp = cpp.findClassPath(group.getRootFolder(), ClassPath.SOURCE);
            if (cp != null) {
                paths.add(cp);
            }
        }
        return paths;
    }
    public static final String TYPE_DOC_ROOT = "doc_root"; //NOI18N

    public static SourceGroup[] getSourceGroups(Project project) {
        SourceGroup[] sourceGroups = null;

        Sources sources = ProjectUtils.getSources(project);
        SourceGroup[] docRoot = sources.getSourceGroups(TYPE_DOC_ROOT);
        SourceGroup[] srcRoots = SourceGroupSupport.getJavaSourceGroups(project);

        if (docRoot != null && srcRoots != null) {
            sourceGroups = new SourceGroup[docRoot.length + srcRoots.length];
            System.arraycopy(docRoot, 0, sourceGroups, 0, docRoot.length);
            System.arraycopy(srcRoots, 0, sourceGroups, docRoot.length, srcRoots.length);
        }

        if (sourceGroups == null || sourceGroups.length == 0) {
            sourceGroups = sources.getSourceGroups(Sources.TYPE_GENERIC);
        }
        return sourceGroups;
    }

    public static Class getType(Project project, String typeName) {
        List<ClassPath> classPaths = SourceGroupSupport.gerClassPath(project);

        for (ClassPath cp : classPaths) {
            try {
                Class ret = JavaUtil.getPrimitiveType(typeName);
                if (ret != null) {
                    return ret;
                }
                ClassLoader cl = cp.getClassLoader(true);
                ret = getGenericRawType(typeName, cl);
                if (ret != null) {
                    return ret;
                }
                if (cl != null) {
                    return cl.loadClass(typeName);
                }
            } catch (ClassNotFoundException ex) {
                //Logger.global.log(Level.INFO, ex.getLocalizedMessage(), ex);
            }
        }
        return null;
    }

    public static Class getGenericRawType(String typeName, ClassLoader loader) {
        int i = typeName.indexOf('<');
        if (i < 1) {
            return null;
        }
        String raw = typeName.substring(0, i);
        try {
            return loader.loadClass(raw);
        } catch (ClassNotFoundException ex) {
            Logger.global.log(Level.INFO, "", ex);
            return null;
        }
    }

}
