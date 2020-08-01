package burp;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;

public class Utils {


    public static String getVersion() {


        String osName = System.getProperties().getProperty("os.name").toUpperCase();
        // macOS:"Mac OS X" windows:"" linux:""
        if (osName.contains("WINDOW")) {
            return "windows";
        } else if (osName.contains("MAC")) {
            return "macOS";
        } else if (osName.contains("LINUX")) {
            return "linux";
        } else {
            return "unknown";
        }
    }

    //将传入的字符串内容复制到剪切板
    public static void setSysClipboardText(String writeMe) {
        Clipboard clip = Toolkit.getDefaultToolkit().getSystemClipboard();
        Transferable tText = new StringSelection(writeMe);
        clip.setContents(tText, null);
    }

}
