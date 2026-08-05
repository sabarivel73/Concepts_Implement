package sb.session.sessionimplement;

import java.util.ArrayList;
import java.util.List;

public class Roles {
    private final List<String> roles;

    public Roles() {
        roles = new ArrayList<>();
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(String context) {
        roles.add(context);
    }
}
