
package com.nvl.utils;

import java.io.File;
import javax.swing.*;
import javax.swing.filechooser.*;

public class QuickSilverFilter extends FileFilter {
    //Accept all directories and all source code files
    public boolean accept(File f) {
        if (f.isDirectory()) {
            return true;
        }

        String extension = Utils.getExtension(f);
        if (extension != null) {
            if (extension.equals(Utils.asp) ||
                extension.equals(Utils.aspx) ||
                extension.equals(Utils.bat) ||
                extension.equals(Utils.cs) ||
                extension.equals(Utils.doc) ||
                extension.equals(Utils.htm) ||
                extension.equals(Utils.html) ||
                extension.equals(Utils.ini) ||
                extension.equals(Utils.java) ||
                extension.equals(Utils.jsp) ||
                extension.equals(Utils.xml) ||
                extension.equals(Utils.rtf) ||
                extension.equals(Utils.txt)) {
                    return true;
            } else {
                return false;
            }
        }

        return false;
    }

    //The description of this filter
    public String getDescription() {
        return "Just Source codes";
    }
}
