package net.kordian.commands.impl;

import net.kordian.commands.Command;
import net.kordian.connections.ConnectionState;
import net.kordian.connections.ControlConnection;
import net.kordian.connections.DataConnection;
import net.kordian.connections.DataTransferMode;

/**
 * The type Port command.
 */
public class PORTCommand extends Command {
    private static final String COMMAND_NAME = "PORT";
    private static final String COMMAND_SUCCESS_RESPONSE = "200 Command OK";

    /**
     * Instantiates a new Port command.
     */
    public PORTCommand() {
        super(COMMAND_NAME);
    }

    @Override
    public ConnectionState handler(String args,
                                   ConnectionState state,
                                   ControlConnection controlConnection,
                                   DataConnection dataConnection) {
        try {
            String[] stringSplit = args.split(",");
            if (stringSplit.length < 6) {
                controlConnection.sendToClient("500 Syntax error: insufficient arguments");
                return state;
            }

            String hostName;
            int port;

            if (isIPv4Address(stringSplit)) {
                hostName = buildIPv4HostName(stringSplit[0], stringSplit[1], stringSplit[2], stringSplit[3]);
                port = calculatePort(stringSplit[4], stringSplit[5]);
            } else if (isIPv6Address(stringSplit)) {
                hostName = buildIPv6HostName(stringSplit);
                port = calculatePort(stringSplit[6], stringSplit[7]);
            } else {
                controlConnection.sendToClient("500 Syntax error: unsupported address format");
                return state;
            }

            dataConnection.openConnection(DataTransferMode.ACTIVE, port, hostName);
            controlConnection.sendToClient(COMMAND_SUCCESS_RESPONSE);
        } catch (Exception e) {
            controlConnection.sendToClient("500 Syntax error in parameters or arguments");
        }

        return state;
    }

    private String buildIPv4HostName(String... parts) {
        StringBuilder builder = new StringBuilder();
        for (String part : parts) {
            builder.append(part).append(".");
        }
        return builder.substring(0, builder.length() - 1);
    }

    private String buildIPv6HostName(String... parts) {
        StringBuilder builder = new StringBuilder();
        for (String part : parts) {
            builder.append(part).append(":");
        }
        return builder.substring(0, builder.length() - 1);
    }

    private boolean isIPv4Address(String[] parts) {
        if (parts.length != 4) {
            return false;
        }
        for (String part : parts) {
            try {
                int value = Integer.parseInt(part);
                if (value < 0 || value > 255) {
                    return false;
                }
            } catch (NumberFormatException e) {
                return false;
            }
        }
        return true;
    }

    private boolean isIPv6Address(String[] parts) {
        if (parts.length != 8) {
            return false;
        }
        for (String part : parts) {
            if (!isValidIPv6Part(part)) {
                return false;
            }
        }
        return true;
    }

    private boolean isValidIPv6Part(String part) {
        try {
            if (part.isEmpty() || part.length() > 4) {
                return false;
            }
            int value = Integer.parseInt(part, 16);
            return value >= 0 && value <= 0xFFFF;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private int calculatePort(String highByte, String lowByte) {
        int high = Integer.parseInt(highByte);
        int low = Integer.parseInt(lowByte);
        return high * 256 + low;
    }
}
