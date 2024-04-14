import cnj.annotation.WebServlet;
import org.junit.Test;

import java.io.File;
import java.lang.annotation.Annotation;
import java.nio.file.Path;
import java.util.Stack;

public class Test1 {
    @Test
    public void testGetUrlMapping() {
        String path = System.getProperty("user.dir") + File.separator + "src" + File.separator + "main" + File.separator + "java";
        System.out.println("工作区路径：" + path);
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
                            System.out.println(className + " " + annotation.value());
                        }
                    } catch (ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }
}
