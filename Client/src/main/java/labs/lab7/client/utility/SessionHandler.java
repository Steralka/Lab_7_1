package labs.lab7.client.utility;

import labs.lab7.common.models.User;

public class SessionHandler {

    private User user = null;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
