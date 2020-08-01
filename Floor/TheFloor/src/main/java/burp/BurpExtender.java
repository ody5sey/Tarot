package burp;

import java.io.*;

public class BurpExtender implements IBurpExtender {

    // static修饰的方法无需new对象使用，可以直接调用
    public static PrintWriter stdout;

    // implement IBurpExtender
    @Override
    public void registerExtenderCallbacks(IBurpExtenderCallbacks callbacks) {


        // 设置插件名称
        callbacks.setExtensionName("Send to Sqlmap");

        // 获得标准输出流
        stdout = new PrintWriter(callbacks.getStdout(), true);

        // write a message to our output stream
        stdout.println("##########################################################");
        stdout.println("#");
        stdout.println(" _______  _        _______  _______  _______ \n" +
                "(  ____ \\( \\      (  ___  )(  ___  )(  ____ )\n" +
                "| (    \\/| (      | (   ) || (   ) || (    )|\n" +
                "| (__    | |      | |   | || |   | || (____)|\n" +
                "|  __)   | |      | |   | || |   | ||     __)\n" +
                "| (      | |      | |   | || |   | || (\\ (   \n" +
                "| )      | (____/\\| (___) || (___) || ) \\ \\__\n" +
                "|/       (_______/(_______)(_______)|/   \\__/\n" +
                "                                             ");
        stdout.println("#");
        stdout.println("##########################################################");

        //注册右键菜单
        callbacks.registerContextMenuFactory(new Menu());


    }


}
