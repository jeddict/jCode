package ${package};

import static ${appPackage}${AuthoritiesConstants_FQN}.ADMIN;
import static ${appPackage}${AuthoritiesConstants_FQN}.USER;
import java.util.HashSet;
import java.util.Set;
import javax.annotation.security.DeclareRoles;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import org.eclipse.microprofile.auth.LoginConfig;
<#if microservices && registryType == "SNOOPEE">
import eu.agilejava.snoop.annotation.EnableSnoopClient;

@EnableSnoopClient(serviceName = "${contextPath}")</#if>
@LoginConfig(
    authMethod = "MP-JWT",
    realmName = "MP-JWT"
)
@DeclareRoles({ADMIN, USER})
@ApplicationPath("${applicationPath}")
public class ${applicationConfig} extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new HashSet<>();<#if docs>
        resources.add(com.wordnik.swagger.jaxrs.listing.ApiListingResource.class);
        resources.add(com.wordnik.swagger.jaxrs.listing.ApiDeclarationProvider.class);
        resources.add(com.wordnik.swagger.jaxrs.listing.ApiListingResourceJSON.class);
        resources.add(com.wordnik.swagger.jaxrs.listing.ResourceListingProvider.class);</#if>
        <#if metrics>resources.add(${appPackage}${DiagnosticFilter_FQN}.class);</#if>
        resources.add(${appPackage}${SecurityHelper_FQN}.class);
        resources.add(${appPackage}${CORSFilter_FQN}.class);
        <#if microservices>resources.add(${appPackage}${HealthController_FQN}.class);</#if>
        <#if gateway || monolith>
        resources.add(${appPackage}${AccountController_FQN}.class);
        resources.add(${appPackage}${AuthenticationController_FQN}.class);
        resources.add(${appPackage}${UserController_FQN}.class);</#if><#if gateway>
        resources.add(${appPackage}${GatewayController_FQN}.class);</#if>
        <#if log>resources.add(${appPackage}${LogsController_FQN}.class);</#if>
        <#if microservices || monolith><#list entityControllerList as entityController>
        resources.add(${entityController.package}.${entityController.name}.class);
        </#list></#if>
        //addRestResourceClasses(resources);
        return resources;
    }

    /**
     * Do not modify addRestResourceClasses() method.
     * It is automatically populated with
     * all resources defined in the project.
     * If required, comment out calling this method in getClasses().
     */
    private void addRestResourceClasses(Set<Class<?>> resources) {
    }

}
