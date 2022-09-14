package account.user;

import account.Role;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "USER")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column()
    private Long id;

    @Column(columnDefinition = "varchar_ignorecase(255) NOT NULL UNIQUE")
    private String username;

    @Column
    @NotBlank(message = "Name is empty")
    private String name;

    @Column
    @NotBlank(message = "Lastname is empty")
    private String lastname;

    @Column
    @Size(message = "The password length must be at least 12 chars!", min = 12)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @NotBlank
    @Email(regexp = "[a-zA-Z0-9._%+-]+@acme.com")
    @Column(unique = true)
    private String email;

    @Column
    private boolean accountNonExpired;

    @Column
    private boolean accountNonLocked;

    @Column
    private boolean credentialsNonExpired;

    @Column
    private boolean enabled;

    @ElementCollection(fetch = FetchType.EAGER)
    @Column
    private Set<Role> authorities = new HashSet<>();

    public User() {
        accountNonExpired = true;
        accountNonLocked = true;
        credentialsNonExpired = true;
        enabled = true;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<Role> authorities) {
        this.authorities = authorities;
    }

    public void addAuthority(Role role) {
        authorities.add(role);
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    public void setAccountNonExpired(boolean accountNonExpired) {
        this.accountNonExpired = accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    public void setAccountNonLocked(boolean accountNonLocked) {
        this.accountNonLocked = accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    public void setCredentialsNonExpired(boolean credentialsNonExpired) {
        this.credentialsNonExpired = credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
        username = email;
    }

    @Override
    public String toString() {
        return "User{" +
                "\nid=" + id +
                "\n, username='" + username + '\'' +
                "\n, lastname='" + lastname + '\'' +
                "\n, password='" + password + '\'' +
                "\n, email='" + email + '\'' +
                "\n, accountNonExpired=" + accountNonExpired +
                "\n, accountNonLocked=" + accountNonLocked +
                "\n, credentialsNonExpired=" + credentialsNonExpired +
                "\n, enabled=" + enabled +
                "\n, authorities=" + authorities +
                '}';
    }
}
