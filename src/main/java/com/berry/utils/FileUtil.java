package com.berry.utils;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {
    /**
     * 删除指定文件或文件夹
     *
     * @param file 文件或文件夹
     */
    public static void deleteFile(File file) {
        if (file != null && file.exists()) {
            if (file.isDirectory()) {
                File files[] = file.listFiles();
                for (int i = 0; i < files.length; i++) {
                    deleteFile(files[i]);
                }
            }
            file.delete();
        }
    }

    /**
     * 删除目录下的所有文件和文件夹
     *
     * @param file 目录
     */
    public static void deleteSubFiles(File file) {
        if (file != null && file.exists()) {
            if (file.isDirectory()) {
                File files[] = file.listFiles();
                for (int i = 0; i < files.length; i++) {
                    deleteFile(files[i]);
                }
            }
        }
    }

    /**
     * 往文件末尾添加一行，使用默认字符集
     *
     * @param file 文件
     * @param content 添加的内容
     */
    public static void appendTextLine(File file, String content) {
        appendText(file, content + "\n");
    }

    /**
     * 往文件末尾添加一行，可以指定字符集
     *
     * @param file 文件
     * @param charset 字符集
     * @param content 添加的内容
     */
    public static void appendTextLine(File file, String charset, String content) {
        appendText(file, charset, content + "\n");
    }

    public static void appendText(File file, String content) {
        appendText(file, null, content);
    }

    public static void appendText(File file, String charset, String content) {
        try {
            if (charset == null) {
                FileWriter fw = new FileWriter(file, true);
                fw.append(content);
                fw.close();
            } else {
                BufferedWriter bw = new BufferedWriter(
                        new OutputStreamWriter(new FileOutputStream(file, true), charset));
                bw.append(content);
                bw.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 按行读取文件，返回字符串数组。使用默认字符集
     *
     * @param file 文件
     * @return 返回字符串数组，每行一个字符串
     */
    public static String[] readFileAsStringArrayIgnoreBlankLine(File file) {
        return readFileAsStringArrayIgnoreBlankLine(file, null);
    }

    /**
     * 按行读取文件，返回字符串数组。可以指定字符集
     *
     * @param file 文件
     * @param charset 字符集
     * @return 返回字符串数组，每行一个字符串
     */
    public static String[] readFileAsStringArrayIgnoreBlankLine(File file, String charset) {
        List<String> list = new ArrayList<String>();
        try {
            BufferedReader br = null;
            if (charset == null) {
                br = new BufferedReader(new FileReader(file));
            } else {
                br = new BufferedReader(new InputStreamReader(new FileInputStream(file), charset));
            }

            String line = null;
            while ((line = br.readLine()) != null) {
                if (line.trim().length() > 0) {
                    list.add(line);
                }
            }
            br.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list.toArray(new String[list.size()]);
    }

    public static File getDesFile(File srcFile) {
        File desFile = null;
        if (srcFile.exists() && srcFile != null) {
            if (srcFile.getName().indexOf(".") != -1) {
                String srcFilePath = srcFile.getAbsolutePath();
                String desFilePath = srcFilePath.substring(0, srcFilePath.lastIndexOf("."))
                        + "_after" + srcFilePath.substring(srcFilePath.lastIndexOf("."));
                desFile = new File(desFilePath);
            } else {
                desFile = new File(srcFile.getAbsoluteFile() + ".after");
            }
        } else {
            System.out.println("Please check the source file...");
        }
        return desFile;
    }

    /**
     * 列出目录中包含指定字符串的所有文件
     *
     * @param file 目录
     * @param subString 包含的字符串
     * @return 返回文件数组
     */
    public static File[] getFilesContains(File file, final String subString) {
        File[] files = file.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                if (name.contains(subString)) {
                    return true;
                }
                return false;
            }
        });
        return files;
    }
}
