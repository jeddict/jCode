<#if package??>package ${package};</#if>

import ${Authority_FQN};
import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
@Named("authority")
public class ${AuthorityFacade} extends AbstractFacade<Authority, String> {

    @PersistenceContext(unitName = "${PU}")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ${AuthorityFacade}() {
        super(Authority.class);
    }
}