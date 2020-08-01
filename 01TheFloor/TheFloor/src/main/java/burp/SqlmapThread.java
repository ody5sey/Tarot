package burp;

import java.io.*;
import java.util.ArrayList;

public class SqlmapThread implements Runnable {

    private static String osName;
    private static String cmd;

    public SqlmapThread(String os, String command) {
        osName = os;
        cmd = command;
    }


    @Override
    public void run() {

        String cmdBase;

        // 此处不要用switch，会锁死主线程
        if (osName.equals("macOS")) {
            cmdBase = "tell application \"Terminal\" \n\tactivate\n\tdo script \" " + String.format(cmd, "sqlmap") + "\"\nend tell";

            try {
                final ProcessBuilder processBuilder = new ProcessBuilder(
                        "/usr/bin/osascript", "-e");
                processBuilder.command().add(cmdBase);
                final Process process = processBuilder.start();

            } catch (Exception e) {
                BurpExtender.stdout.println(e.getMessage());

            }
        } else if (osName.equals("windows")) {

            try {
                String runBat = "sqlmap\\run.bat";

                String command = String.format(cmd.replace("/", "\\"), "sqlmap.py");

                BufferedWriter out = new BufferedWriter(new FileWriter(runBat));
                out.write(command);
                out.close();

                ArrayList<String> cmds = new ArrayList<>();
                cmds.add("cmd.exe");
                cmds.add("/c");
                cmds.add("start");
                cmds.add(runBat);

                ProcessBuilder processBuilder = new ProcessBuilder(cmds);
                Process process = processBuilder.start();
                InputStreamReader ir = new InputStreamReader(process.getInputStream());
                BufferedReader input = new BufferedReader(ir);
                String line;
                while ((line = input.readLine()) != null) {
                    BurpExtender.stdout.println(line);
                }

                File file = new File(runBat);
                if (file.isFile()) { // 判断是否是文件
                    boolean err = file.delete();// 删除
                    if (!err) {
                        BurpExtender.stdout.println("删除bat文件失败");
                    }
                }


            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("shadow");
                BurpExtender.stdout.println(e.getMessage());
                BurpExtender.stdout.println("shadow");

            }

        } else if (osName.equals("linux")) {
            try {
                String sqlmapCmd = String.format(cmd, "sqlmap");
                String[] cmdDict = sqlmapCmd.split(" ");

                String[] command = {"xterm", "-hold", "-e", cmdDict[0], cmdDict[1], cmdDict[2]};
                Process proc = new ProcessBuilder(command).start();

            } catch (Exception e) {
                BurpExtender.stdout.println(e.getMessage());
            }

        }


    }
}
