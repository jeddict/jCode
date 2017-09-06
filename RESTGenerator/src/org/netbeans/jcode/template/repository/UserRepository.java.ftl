<#if package??>package ${package};</#if>

import ${User_FQN};
import static java.util.Collections.singletonMap;
import java.util.Optional;
import java.util.List;
<#if !cdi>import javax.ejb.Stateless;</#if>
<#if named>import javax.inject.Named;</#if>
import javax.inject.Inject;
import javax.persistence.EntityManager;

<#if !cdi>@Stateless</#if>
<#if named>@Named("user")</#if>
public class ${UserRepository} extends ${AbstractRepository}<User, Long> {

    @Inject
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ${UserRepository}() {
        super(User.class);
    }

    public Optional<User> findOneByLogin(String login) {
        return findSingleByNamedQuery("findUserByLogin", singletonMap("login", login));
    }

    public Optional<User> findOneByEmail(String email) {
        return findSingleByNamedQuery("findUserByEmail", singletonMap("email", email));
    }

    public Optional<User> findOneByResetKey(String resetKey) {
        return findSingleByNamedQuery("findUserByResetKey", singletonMap("resetKey", resetKey));
    }

    public Optional<User> findOneByActivationKey(String activationKey) {
        return findSingleByNamedQuery("findUserByActivationKey", singletonMap("activationKey", activationKey));
    }

<#--public List<User> findAllByActivatedIsFalseAndCreatedDateBefore(ZonedDateTime dateTime){
       TypedQuery<User> query = em.createNamedQuery("findUserByActivationKey");
       query.setParameter("createdDate", false);
       return query.getResultList();
    }    
-->
    public Optional<User> findOneById(Long userId) {
        return findSingleByNamedQuery("findUserByUserId", singletonMap("id", userId));
    }

    public Optional<User> findOneWithAuthoritiesById(Long userId) {
        return findSingleByNamedQuery("findUserByUserId", "graph.user.authorities", singletonMap("id", userId));
    }

    public Optional<User> findOneWithAuthoritiesByLogin(String login) {
        return findSingleByNamedQuery("findUserByLogin", "graph.user.authorities", singletonMap("login", login));
    }
    
    public List<User> getUsersWithAuthorities(int startPosition, int size) {
        return findRange(startPosition, size, "graph.user.authorities");
    }

}