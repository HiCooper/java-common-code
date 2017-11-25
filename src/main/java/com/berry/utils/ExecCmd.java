package com.berry.utils;

public class ExecCmd {

    public static void main(String args[]) {
        Runtime run = Runtime.getRuntime();
        Process process = null;
        try {
            process = run.exec("notepad"); // 执行cmd命令

            process.waitFor();
        } catch (Exception e) {
            System.out.println("Error executing notepad.");
        }
        System.out.println("Notepad returned " + process.exitValue());
    }

}
