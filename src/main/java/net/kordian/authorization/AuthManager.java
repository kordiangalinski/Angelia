package net.kordian.authorization;

/**
 * The type Auth manager.
 */
// TODO: SQLite to be implemented
public class AuthManager {
    private final String username = "user";
    private final String password = "pass";

    /**
     * Is authorized boolean.
     *
     * @param username the username
     * @param password the password
     * @return the boolean
     */
    public boolean isAuthorized(String username, String password) {
        return this.username.equals(username) && this.password.equals(password);
    }
}
