package superhero.configs.auth;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;


public class Credential {
    @NotNull
    @NotEmpty
    private String user;
    @NotNull
    @NotEmpty
    private String password;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
