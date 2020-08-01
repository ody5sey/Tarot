package burp;

import javax.swing.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Menu implements IContextMenuFactory {

    public static String pwd = System.getProperty("user.dir");

    @Override
    public List<JMenuItem> createMenuItems(IContextMenuInvocation invocation) {

        List<JMenuItem> items = new ArrayList<>();

        JMenuItem item = new JMenuItem("Send To Sqlmap");
        item.addActionListener(e -> {

            boolean successDir = false;
            String path = pwd + "/sqlmap";

            File dir = new File(path);

            if (!dir.exists()) {// 判断目录是否存在
                if (dir.mkdir()) {

                    successDir = true;
                }
            } else {
                successDir = true;

            }

            if (successDir) {
                // 获取主机头
                IHttpRequestResponse[] messages = invocation.getSelectedMessages();
                byte[] reqArray = messages[0].getRequest();

                IHttpService httpService = messages[0].getHttpService();
                String domain = httpService.getHost();
                int port = httpService.getPort();

                String domainName = domain.replace(".", "_") + "_" + port;


                String domainPath = path + "/" + domainName;

                File domainDir = new File(domainPath);

                boolean successDirSecond = false;

                if (!domainDir.exists()) {// 判断目录是否存在
                    if (domainDir.mkdir()) {
                        successDirSecond = true;

                    }
                } else {
                    successDirSecond = true;
                }


                // 获取时间
                Date currentTime = new Date();
                SimpleDateFormat format0 = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss_S");
                String timeStr = format0.format(currentTime.getTime());//这个就是把时间戳经过处理得到期望格式的时间

//                String fullName = domainName + "_" + timeStr;
                String fullPath = domainPath + "/" + timeStr + ".txt";


                try {
                    //使用这个构造函数时，如果存在则删除
                    BufferedWriter out = new BufferedWriter(new FileWriter(fullPath));

                    String requests = new String(reqArray);
                    out.write(requests);
                    out.close();

                } catch (IOException e1) {
                    e1.printStackTrace();
                    successDirSecond = false;
                }

                if (successDirSecond) {
                    String os = Utils.getVersion();
                    String txt = "";

                    if (os.equals("macOS")) {
                        txt = "sqlmap -r " + fullPath;

                    } else if (os.equals("windows")) {
                        txt = "sqlmap.py -r " + fullPath.replace("/", "\\");

                    } else if (os.equals("linux")) {
                        txt = "sqlmap.py -r " + fullPath;

                    }

                    Utils.setSysClipboardText(txt);

                    new Thread(new SqlmapThread(os, "%s -r " + fullPath)).start();


                }

            }


        });

        items.add(item);

        return items;
    }
}