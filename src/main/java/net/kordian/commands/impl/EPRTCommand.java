package net.kordian.commands.impl;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.kordian.commands.Command;
import net.kordian.connections.ConnectionState;
import net.kordian.connections.ControlConnection;
import net.kordian.connections.DataConnection;
import net.kordian.connections.DataTransferMode;

public class EPRTCommand extends Command {

    private static final String COMMAND_NAME = "EPRT";
    private static final String COMMAND_SUCCESS_RESPONSE = "200 Command OK";
    private static final Pattern IPV4_PATTERN = Pattern.compile("\\|1\\|(\\d+\\.\\d+\\.\\d+\\.\\d+)\\|(\\d+)\\|");
//    private static final Pattern IPV6_PATTERN = Pattern.compile("\\|2\\|\\[(.+)]\\|(\\d+)\\|");
    private static final Pattern IPV6_PATTERN = Pattern.compile("\\|2\\|([^\\|]+)\\|(\\d+)\\|");

    public EPRTCommand() {
        super(COMMAND_NAME);
    }

    @Override
    public ConnectionState handler(String args,
                                   ConnectionState state,
                                   ControlConnection controlConnection,
                                   DataConnection dataConnection) {
        Matcher ipv4Matcher = IPV4_PATTERN.matcher(args);
        Matcher ipv6Matcher = IPV6_PATTERN.matcher(args);

        if (ipv4Matcher.matches()) {
            String ipAddress = ipv4Matcher.group(1);
            int port = Integer.parseInt(ipv4Matcher.group(2));
            dataConnection.openConnection(DataTransferMode.ACTIVE, port, ipAddress);
        } else if (ipv6Matcher.matches()) {
            String ipAddress = ipv6Matcher.group(1);
            int port = Integer.parseInt(ipv6Matcher.group(2));
            dataConnection.openConnection(DataTransferMode.ACTIVE, port, ipAddress);
        } else {
            throw new IllegalArgumentException("Invalid EPRT command format");
        }

        controlConnection.sendToClient(COMMAND_SUCCESS_RESPONSE);
        return state;
    }
}
