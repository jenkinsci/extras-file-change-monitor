package org.jvnet.hudson.file_change_monitor;

import java.io.File;

/**
 * Represents a directory.
 *
 * @author Kohsuke Kawaguchi
 */
public class Directory {
    private final File dir;

    public Directory(File dir) {
        this.dir = dir;
    }

    /**
     * Recursively compute the biggest last modified timestamp of all files in this directory tree.
     */
    public long lastModified() {
        File[] files = dir.listFiles();
        if(files==null)     return -1;

        long r = -1;

        for (File f : files) {
            if(f.getName().startsWith("."))
                continue;
            r = Math.max(r,lastModified(f));
        }

        return r;
    }

    public static long lastModified(File f) {
        if(f.isDirectory())
            return new Directory(f).lastModified();
        else
            return f.lastModified();
    }
}
