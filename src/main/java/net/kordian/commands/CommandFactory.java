package net.kordian.commands;

import net.kordian.commands.impl.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Factory class for creating commands.
 */
public class CommandFactory {

    /**
     * Creates and returns a map of commands.
     * @return a map of commands with their corresponding names
     */
    public static Map<String, Command> createCommands() {
        Map<String, Command> commands = new HashMap<>();
        commands.put("USER", new USERCommand());
        commands.put("PASS", new PASSCommand());
        commands.put("LIST", new LISTCommand());
        commands.put("XPWD", new PWDCommand());
        commands.put("PWD", new PWDCommand());
        commands.put("TYPE", new TYPECommand());
        commands.put("EPSV", new EPSVCommand());
        commands.put("EPRT", new EPRTCommand());
        commands.put("CWD", new CWDCommand());
        commands.put("QUIT", new QUITCommand());
        commands.put("PASV", new PASVCommand());
        commands.put("SYST", new SYSTCommand());
        commands.put("FEAT", new FEATCommand());
        commands.put("PORT", new PORTCommand());
        commands.put("RETR", new RETRCommand());
        commands.put("XMKD", new RETRCommand());
        commands.put("MKD", new MKDCommand());
        commands.put("RMD", new RMDCommand());
        commands.put("XRMD", new RMDCommand());
        commands.put("STOR", new STORCommand());
        commands.put("NLST", new NLSTCommand());
        commands.put("DELE", new DELECommand());
        // TODO: Add more commands here...

        return commands;
    }
}