package burp;

import java.io.*;

public class BurpExtender implements burp.IBurpExtender {

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
        stdout.println("###############################################################");
        stdout.println("#");
        stdout.println("#        _______  __        ______     ______   .______      \n" +
                "#       |   ____||  |      /  __  \\   /  __  \\  |   _  \\     \n" +
                "#       |  |__   |  |     |  |  |  | |  |  |  | |  |_)  |    \n" +
                "#       |   __|  |  |     |  |  |  | |  |  |  | |      /     \n" +
                "#       |  |     |  `----.|  `--'  | |  `--'  | |  |\\  \\----.\n" +
                "#       |__|     |_______| \\______/   \\______/  | _| `._____|");
        stdout.println("#");
        stdout.println("###############################################################");

        //注册右键菜单
        callbacks.registerContextMenuFactory(new Menu());


    }


}
