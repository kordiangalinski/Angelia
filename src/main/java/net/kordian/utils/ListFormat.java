package net.kordian.utils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * The type List format.
 */
// TODO:
public class ListFormat {

    /**
     * Generates a long listing format for the given file and its contents.
     *
     * @param file The file for which the long listing format is generated.
     * @return An array of strings representing the long listing format.
     */
    public static String[] longListingFormat(File file) {
        File[] files = file.listFiles();

        List<String> list = new ArrayList<>();

        assert files != null;
        for (File f : files) {
            String type = "-";
            if (f.isDirectory()) {
                type = "d";
            } else if (f.isFile()) {
                type = "-";
            }

            String permissions = "";
            permissions += f.canRead() ? "r" : "-";
            permissions += f.canWrite() ? "w" : "-";
            permissions += f.canExecute() ? "x" : "-";

            long fileSize = f.length();

            SimpleDateFormat sdf = new SimpleDateFormat("MMM dd HH:mm");
            Date lastModified = new Date(f.lastModified());

            String owner = "ftp";

            list.add(String.format("%s%s %d %s %s %d %s %s\n",
                    type, permissions, 1, owner, owner, fileSize,
                    sdf.format(lastModified), f.getName()));
        }

        return list.toArray(new String[0]);
    }
}