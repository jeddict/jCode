    <security-constraint>
        <web-resource-collection>
            <web-resource-name>${AccountController}.isAuthenticated</web-resource-name>
            <url-pattern>/resources/api/authenticate</url-pattern>
            <http-method>GET</http-method>
        </web-resource-collection>
        <web-resource-collection>
            <web-resource-name>${AccountController}.getAccount</web-resource-name>
            <url-pattern>/resources/api/account</url-pattern>
            <http-method>GET</http-method>
        </web-resource-collection>
        <web-resource-collection>
            <web-resource-name>${AccountController}.saveAccount</web-resource-name>
            <url-pattern>/resources/api/account</url-pattern>
            <http-method>POST</http-method>
        </web-resource-collection>
        <web-resource-collection>
            <web-resource-name>${AccountController}.changePassword</web-resource-name>
            <url-pattern>/resources/api/account/change_password</url-pattern>
            <http-method>POST</http-method>
        </web-resource-collection>
        <web-resource-collection>
            <web-resource-name>${UserController}.getAllUsers</web-resource-name>
            <url-pattern>/resources/api/users</url-pattern>
            <http-method>GET</http-method>
        </web-resource-collection>
        <web-resource-collection>
            <web-resource-name>${UserController}.getUser</web-resource-name>
            <url-pattern>/resources/api/users/*</url-pattern>
            <http-method>GET</http-method>
        </web-resource-collection>
        <auth-constraint>
            <role-name>ROLE_USER</role-name>
        </auth-constraint>
    </security-constraint>
    <security-constraint>
        <web-resource-collection>
            <web-resource-name>${UserController}.createUser</web-resource-name>
            <url-pattern>/resources/api/users</url-pattern>
            <http-method>POST</http-method>
        </web-resource-collection>
        <web-resource-collection>
            <web-resource-name>${UserController}.updateUser</web-resource-name>
            <url-pattern>/resources/api/users</url-pattern>
            <http-method>PUT</http-method>
        </web-resource-collection>
        <web-resource-collection>
            <web-resource-name>${UserController}.getAuthorities</web-resource-name>
            <url-pattern>/resources/api/users/authorities</url-pattern>
            <http-method>GET</http-method>
        </web-resource-collection>
        <web-resource-collection>
            <web-resource-name>${UserController}.deleteUser</web-resource-name>
            <url-pattern>/resources/api/users/*</url-pattern>
            <http-method>DELETE</http-method>
        </web-resource-collection>
        <auth-constraint>
            <role-name>ROLE_ADMIN</role-name>
        </auth-constraint>
    </security-constraint>