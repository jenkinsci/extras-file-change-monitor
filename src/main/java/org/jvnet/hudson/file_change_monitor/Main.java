package org.jvnet.hudson.file_change_monitor;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;

/**
 * @author Kohsuke Kawaguchi
 */
public class Main {
    public static void main(String[] args) throws Exception {
        File timestamp = new File(args[0]);
        URL triggerURL = new URL(args[1]);

        long r = -1;
        for (int i=2; i < args.length; i++)
            r = Math.max(r,Directory.lastModified(new File(args[i])));

        System.out.println("Last time we checked, the timestamp was "+timestamp.lastModified());
        System.out.println("This time it is                         "+r);
        boolean updated = timestamp.lastModified() < r;
        new FileOutputStream(timestamp).close();
        timestamp.setLastModified(r);
        if(updated) {
            System.out.println("Triggering");
            triggerURL.openStream().close();
        }
    }
}
