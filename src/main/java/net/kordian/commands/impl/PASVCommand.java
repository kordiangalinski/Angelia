package net.kordian.commands.impl;

import java.net.*;
import java.util.Enumeration;
import java.util.regex.Pattern;

import net.kordian.commands.Command;
import net.kordian.connections.ConnectionState;
import net.kordian.connections.ControlConnection;
import net.kordian.connections.DataConnection;
import net.kordian.connections.DataTransferMode;

/**
 * The type Pasv command.
 */
public class PASVCommand extends Command {

    private static final String COMMAND_NAME = "PASV";
    private static final String COMMAND_SUCCESS_RESPONSE = "227 Entering Passive Mode";

    /**
     * Instantiates a new Pasv command.
     */
    public PASVCommand() {
        super(COMMAND_NAME);
    }

    @Override
    public ConnectionState handler(String args,
                                   ConnectionState state,
                                   ControlConnection controlConnection,
                                   DataConnection dataConnection) {
        String ipAddress = getIpAddress();
        int dataPort = dataConnection.getDataPort();

        String response = buildPassiveModeResponse(ipAddress, dataPort);
        controlConnection.sendToClient(response);

        dataConnection.openConnection(DataTransferMode.PASSIVE, dataPort);

        return state;
    }

    private String getIpAddress() {
        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            while (interfaces.hasMoreElements()) {
                NetworkInterface networkInterface = interfaces.nextElement();
                Enumeration<InetAddress> addresses = networkInterface.getInetAddresses();
                while (addresses.hasMoreElements()) {
                    InetAddress address = addresses.nextElement();
                    if (!address.isLoopbackAddress() && isSupportedAddressType(address)) {
                        return address.getHostAddress();
                    }
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return "";
    }

    private boolean isSupportedAddressType(InetAddress address) {
        return address instanceof Inet4Address || address instanceof Inet6Address;
    }

    private String buildPassiveModeResponse(String ipAddress, int dataPort) {
        if (ipAddress.contains(":")) {
            // IPv6
            String[] ipParts = splitIpv6Address(ipAddress);
            String ipString = String.join(",", ipParts);
            return String.format("%s (%s,%d,%d)", COMMAND_SUCCESS_RESPONSE, ipString, dataPort / 256, dataPort % 256);
        } else {
            // IPv4
            String[] ipParts = ipAddress.split("\\.");
            return String.format("%s (%s,%s,%s,%s,%d,%d)", COMMAND_SUCCESS_RESPONSE, ipParts[0], ipParts[1], ipParts[2], ipParts[3], dataPort / 256, dataPort % 256);
        }
    }

    private String[] splitIpv6Address(String ipAddress) {
        String sanitizedIpAddress = ipAddress.replace("[", "").replace("]", "");
        return sanitizedIpAddress.split(Pattern.quote(":"));
    }
}
