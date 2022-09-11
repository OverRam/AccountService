package account;

import org.springframework.security.core.GrantedAuthority;

public class Role implements GrantedAuthority {


    private String authority;

    /**
     * @return role without prefix
     * ex: ADMIN
     */
    @Override
    public String getAuthority() {
        return authority;
    }

    /**
     * @param authority without prefix ROLE_
     */
    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public Role() {
    }

    public Role(String authority) {
        this.authority = authority;
    }
}