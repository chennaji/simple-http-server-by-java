package cnj;

import cnj.Exception.UrlNotMatchException;
import cnj.annotation.WebServlet;

import java.io.File;
import java.util.Map;
import java.util.Stack;

public class ServetMapping {
    static Map<String, Class<?>> servletMap = new java.util.HashMap<>();
    enum URL_TYPE{
        HTML,
        SERVLET
    }
    static{
        String path = System.getProperty("user.dir") + File.separator + "src" + File.separator + "main" + File.separator + "java";
        //创建一个栈,递归遍历类
        File rootDir = new File(path);
        Stack<File> stack = new Stack<>();
        stack.push(rootDir);
        while (!stack.isEmpty()) {
            File file = stack.pop();
            if (file.isDirectory()) {
                File[] subFiles = file.listFiles();
                for (File subFile : subFiles) {
                    stack.push(subFile);
                }
            } else {
                String fileName = file.getName();
                if (fileName.endsWith(".java")) {
                    //将路径名转换为全限定名
                    String filePath = file.getPath();
                    String className = filePath.replace(File.separator, ".")
                            .replaceFirst(".*src.main.java.", "")
                            .replace(".java", "");
                    //加载类
                    try {
                        Class<?> clazz = Thread.currentThread().getContextClassLoader().loadClass(className);
                        if (clazz.isAnnotationPresent(WebServlet.class)) {
                            WebServlet annotation = clazz.getAnnotation(WebServlet.class);
                            servletMap.put(annotation.value(), clazz);
                        }
                    } catch (ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }
    public static Class<?> getServletClass(String url) throws UrlNotMatchException {
        //url无效则抛出错误
        if(!servletMap.containsKey(url)){
            throw new UrlNotMatchException("无效的url");
        }
        System.out.println("即将匹配的url为：" + url);
        System.out.println("匹配的servlet为：" + servletMap.get(url).getName());
        return servletMap.get(url);
    }
}
