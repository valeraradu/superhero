package superhero.configs.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(path =  "/auth")
public class ApiAuthenticationController {
    private static final String ATTRIBUTE_USER_KEY = "user";
    @Autowired
    private final AuthenticationManager authenticationManager;

    @Autowired
    public ApiAuthenticationController(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<User> login(@Validated @RequestBody Credential credential, HttpSession httpSession) {
        Authentication authentication = new UsernamePasswordAuthenticationToken(credential.getUser(), credential.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        User user = new User(credential.getUser(), httpSession.getId(), true);
        httpSession.setAttribute(ATTRIBUTE_USER_KEY, user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET)
    public User session(HttpSession session) {
        return (User) session.getAttribute(ATTRIBUTE_USER_KEY);
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public Map<String, String> logout(HttpSession session) {
        session.invalidate();
        Map<String, String> ret = new HashMap<>();
        ret.put("success", String.valueOf(true));
        return ret;
    }
}
