package superhero.configs.auth;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;

import java.io.Serializable;

public class User implements Serializable {
    private static final long serialVersionUID = 10952930399594131L;
    private final String user;
    private final String token;
    private final boolean authenticated;

    public User(String user, String token, boolean authenticated) {
        Preconditions.checkState(!Strings.isNullOrEmpty(user));
        Preconditions.checkState(!Strings.isNullOrEmpty(token));

        this.user = user;
        this.token = token;
        this.authenticated = authenticated;
    }

    public String getUser() {
        return user;
    }

    public String getToken() {
        return token;
    }

    public boolean isAuthenticated() {
        return authenticated;
    }

}
