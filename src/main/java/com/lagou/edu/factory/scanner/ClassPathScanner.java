package com.lagou.edu.factory.scanner;


import java.io.File;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;

public class ClassPathScanner implements Scanner {

    private static final String RESOURCE_SUFFIX = ".class";

    private static final String FILE_SEPARATOR = "/";

    private static final String SEPARATOR_DOT = ".";

    @Override
    public List<String> scan(String path) {
        List<String> result = new LinkedList<>();
        URL resource = ClassPathScanner.class.getClassLoader()
                .getResource(path.replace(SEPARATOR_DOT, FILE_SEPARATOR));
        if (null != resource) {
            File file = new File(resource.getPath());
            int n = path.lastIndexOf(".");
            if (n > 0) {
                path = path.substring(0, n);
            }
            scan(path, file, result);
        }
        return result;
    }

    /**
     * 递归扫描所有.class文件
     */
    private void scan(String path, File file, List<String> result) {
        String fileName = file.getName();
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (null != files && files.length > 0) {
                for (File subFile : files) {
                    scan(path + SEPARATOR_DOT + fileName, subFile, result);
                }
            }
        } else {
            if (fileName.endsWith(RESOURCE_SUFFIX)) {
                result.add(path + SEPARATOR_DOT +
                        fileName.substring(0, fileName.length() - RESOURCE_SUFFIX.length()));
            }
        }
    }

}
