package class_parser;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
//        File classFile = new File("ClassParser/target/test-classes/com/company/TestClass.class");
        File classFile = new File("ClassParser/target/classes/utils/FormatUtil.class");
        FileInputStream fis = new FileInputStream(classFile);
        ClassParser classParser = new ClassParser();
        classParser.parse(fis);
        classParser.showByteCodeInfo();
    }
}
