package class_parser;

import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException {
        File classFile = new File("target/classes/class_study/TestClass.class");
        FileInputStream fis = new FileInputStream(classFile);
        ClassParser classParser = new ClassParser();
        classParser.parse(fis);
        classParser.showByteCodeInfo();
    }
}
