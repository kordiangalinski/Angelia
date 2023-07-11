package net.kordian.connections;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.kordian.authorization.AuthManager;
import net.kordian.authorization.UserStatus;

/**
 * The type Connection state.
 */
@NoArgsConstructor
@Getter
@Setter
public class ConnectionState {
    private String user;
    private UserStatus userStatus = UserStatus.NOT_LOGGED_IN;

    private AuthManager authManager = new AuthManager();

    private final String rootDir = "/FTPDir";
    private String currDir = rootDir;

    private boolean established = true;
}
