package class_parser;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.Getter;
import utils.FormatUtil;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class ClassParser {
    private DataInputStream dis;
    @Getter
    @JSONField
    private int magic;
    @Getter
    @JSONField(ordinal = 1)
    private int minor_version;
    @Getter
    @JSONField(ordinal = 2)
    private int major_version;
    @Getter
    @JSONField(ordinal = 3)
    private int constant_pool_count;
    @Getter
    @JSONField(ordinal = 4)
    private Map<Integer, Map<String, Object>> constant_pool;
    @Getter
    @JSONField(ordinal = 5)
    private int access_flags;
    @Getter
    @JSONField(ordinal = 6)
    private String this_class;
    @Getter
    @JSONField(ordinal = 7)
    private String super_class;
    @Getter
    @JSONField(ordinal = 8)
    private int interfaces_count;
    @Getter
    @JSONField(ordinal = 9)
    private String[] interfaces_info_array;
    @Getter
    @JSONField(ordinal = 10)
    private int fields_count;
    @Getter
    @JSONField(ordinal = 11)
    private Map<Integer, Map<String, Object>> fields;
    @Getter
    @JSONField(ordinal = 12)
    private int methods_count;
    @Getter
    @JSONField(ordinal = 13)
    private Map<Integer, Map<String, Object>> methods;
    @Getter
    @JSONField(ordinal = 14)
    private int attribute_count;
    @Getter
    @JSONField(ordinal = 15)
    private Map<Integer, Map<String, Object>> attributes;

    // fis -> dis
    public void parse(FileInputStream fis) throws IOException {
        this.dis = new DataInputStream(fis);
        // 解析魔数和class文件版本
        int temp_magic = dis.readInt();
        if (temp_magic == 0xCAFEBABE) {
            this.magic = temp_magic;
            // 解析副版本号
            this.minor_version = dis.readUnsignedShort();
            // 解析主版本号
            this.major_version = dis.readUnsignedShort();
        }
        // 解析常量池
        this.constant_pool = new LinkedHashMap<>();
        this.constant_pool_count = dis.readUnsignedShort();
        // TODO：long 和 double 类型占两个常量位
        for (int i = 1; i <= constant_pool_count - 1; i++) {
            // 用于存储元素
            HashMap<String, Object> constant_data = new LinkedHashMap<>();
            // 解析tag
            int tag = dis.readUnsignedByte();
            switch (tag) {
                /*
                CONSTANT_Utf8_info {
                    u1 tag;
                    u2 length;
                    u1 bytes[length];
                }
                 */
                case 1: {
                    int length = dis.readUnsignedShort();
                    byte[] bytes = new byte[length];
                    dis.read(bytes);
                    constant_data.put("tag", getTagNameByNumber(tag));
                    constant_data.put("length", length);
                    constant_data.put("bytes", new String(bytes, StandardCharsets.UTF_8));
                    break;
                }
                /*
                CONSTANT_Integer_info {
                    u1 tag;
                    u4 bytes;
                }
                 */
                case 3: {
                    int bytes = dis.readInt();
                    constant_data.put("tag", getTagNameByNumber(tag));
                    constant_data.put("bytes", bytes);
                    break;
                }
                /*
                CONSTANT_Float_info {
                    u1 tag;
                    u4 bytes;
                }
                 */
                case 4: {
                    float bytes = dis.readFloat();
                    constant_data.put("tag", getTagNameByNumber(tag));
                    constant_data.put("bytes", bytes);
                    break;
                }
                /*
                CONSTANT_Long_info {
                    u1 tag;
                    u8 bytes;
                }
                 */
                case 5: {
                    long bytes = dis.readLong();
                    constant_data.put("tag", getTagNameByNumber(tag));
                    constant_data.put("bytes", bytes);
                    break;
                }
                /*
                CONSTANT_Double_info {
                    u1 tag;
                    u8 bytes;
                }
                 */
                case 6: {
                    double bytes = dis.readDouble();
                    constant_data.put("tag", getTagNameByNumber(tag));
                    constant_data.put("bytes", bytes);
                    break;
                }
                /*
                CONSTANT_Class_info {
                    u1 tag;
                    u2 index;
                }
                 */
                case 7: {
                    int index = dis.readUnsignedShort();
                    constant_data.put("tag", getTagNameByNumber(tag));
                    constant_data.put("constant_class_index", index);
                    break;
                }
                /*
                CONSTANT_String_info {
                    u1 tag;
                    u2 index;
                }
                 */
                case 8: {
                    int index = dis.readUnsignedShort();
                    constant_data.put("tag", getTagNameByNumber(tag));
                    constant_data.put("constant_string_index", index);
                    break;
                }
                /*
                CONSTANT_Fieldref_info {
                    u1 tag;
                    u2 index;
                    u2 index;
                }
                 */
                case 9:
                /*
                CONSTANT_Methodref_info {
                    u1 tag;
                    u2 index;
                    u2 index;
                }
                 */
                case 10:
                /*
                CONSTANT_InterfaceMethodref_info {
                    u1 tag;
                    u2 index;
                    u2 index;
                }
                 */
                case 11: {
                    int class_index = dis.readUnsignedShort();
                    int nameAndType_index = dis.readUnsignedShort();
                    constant_data.put("tag", getTagNameByNumber(tag));
                    constant_data.put("class_index", class_index);
                    constant_data.put("nameAndType_index", nameAndType_index);
                    break;
                }
                /*
                CONSTANT_NameAndType_info {
                    u1 tag;
                    u2 index;
                    u2 index;
                }
                 */
                case 12: {
                    int constant_name_index = dis.readUnsignedShort();
                    int constant_type_index = dis.readUnsignedShort();
                    constant_data.put("tag", getTagNameByNumber(tag));
                    constant_data.put("constant_name_index", constant_name_index);
                    constant_data.put("constant_type_index", constant_type_index);
                    break;
                }
                /*
                CONSTANT_Method_Handle_info {
                    u1 tag;
                    u1 reference_kind (1~9);
                    u2 reference_index;
                }
                 */
                case 15: {
                    int reference_kind = dis.readUnsignedByte();
                    int reference_index = dis.readUnsignedShort();
                    constant_data.put("tag", getTagNameByNumber(tag));
                    constant_data.put("reference_kind", reference_kind);
                    constant_data.put("reference_index", reference_index);
                    break;
                }
                /*
                CONSTANT_Method_Type_info {
                    u1 tag;
                    u2 desc_index;
                }
                 */
                case 16: {
                    int desc_index = dis.readUnsignedShort();
                    constant_data.put("tag", getTagNameByNumber(tag));
                    constant_data.put("descriptor_index", desc_index);
                    break;
                }
                /*
                CONSTANT_Invoke_Dynamic_info {
                    u1 tag;
                    u2 bootstrap_method_attr_index;
                    u2 nameAndType_index;
                }
                 */
                case 18: {
                    int bootstrap_method_attr_index = dis.readUnsignedShort();
                    int nameAndType_index = dis.readUnsignedShort();
                    constant_data.put("tag", getTagNameByNumber(tag));
                    constant_data.put("bootstrap_method_attr_index", bootstrap_method_attr_index);
                    constant_data.put("nameAndType_index", nameAndType_index);
                    break;
                }
            }
            // i表示元素在常量池中的索引位置
            constant_pool.put(i, constant_data);
        }
        // TODO：链接常量池对象，测试更多的数据
        for (Integer id : constant_pool.keySet()) {
            Map<String, Object> constant_data = constant_pool.get(id);
            for (String key : constant_data.keySet()) {
                // 以 index 结尾，表示索引，则可能存在常量池中的对象链接
                if (key.endsWith("index")) {
                    Integer index_in_pool = (Integer) constant_data.get(key);
                    if (index_in_pool > 0) {
                        Map<String, Object> index_map = constant_pool.get(index_in_pool);
                        if (index_map.containsKey("bytes")) {
                            // 链接字面量
                            constant_data.put(key, "#" + index_in_pool + " " + index_map.get("bytes"));
                        } else {
                            constant_data.put(key, "#" + index_in_pool + " " + index_map.get("tag"));
                        }
                    }
                }
            }
        }

        // 解析访问标志 u2 access_flags
        access_flags = dis.readUnsignedShort();

        // 解析当前类名在常量池中的索引 u2 this_class
        int this_class_index = dis.readUnsignedShort();
        this.this_class = getCpInfoByIndex(this_class_index);

        // 解析父类在常量池中的索引位置 u2 super_class
        int super_class_index = dis.readUnsignedShort();
        this.super_class = getCpInfoByIndex(super_class_index);

        // 解析接口数量 u2 interfaces_count
        interfaces_count = dis.readUnsignedShort();

        // 解析接口 interfaces[interfaces_count]
        this.interfaces_info_array = new String[interfaces_count];
        for (int i = 0; i < interfaces_count; i++) {
            int index_in_pool = dis.readUnsignedShort();
            this.interfaces_info_array[i] = getCpInfoByIndex(index_in_pool);
        }

        // 解析字段数量 u2 fields_counts
        this.fields_count = dis.readUnsignedShort();

        /*
        字段和属性具有相同的结构，可以一起解析
        field_info {
            u2 access_flags; //成员变量访问标志
            u2 name_index; //成员变量名称在常量池中的索引
            u2 descriptor_index; //成员变量的描述符在常量池中的索引
            u2 attributes_count; //成员变量属性数量
            attribute_info attributes[attributes_count]; //成员变量的属性信息
         }
         */
        fields = new LinkedHashMap<>();
        getFieldsOrMethodInfo(fields, fields_count);

        /*
        解析方法
        u2 methods_counts
         */
        this.methods_count = dis.readUnsignedShort();
        methods = new LinkedHashMap<>();
        getFieldsOrMethodInfo(methods, methods_count);

        /*
        解析属性
        u2 attribute_count
         */
        this.attribute_count = dis.readUnsignedShort();
        attributes = new LinkedHashMap<>();
        getAttributeInfo(attributes, this.attribute_count);
    }

    private void getFieldsOrMethodInfo(Map<Integer, Map<String, Object>> fieldsOrMethods, int count) throws IOException {
        for (int i = 0; i < count; i++) {
            LinkedHashMap<String, Object> info_map = new LinkedHashMap<>();
            // u2 access_flags
            int access_flags = dis.readUnsignedShort();
            info_map.put("access_flags", access_flags);
            // u2 name_index
            int name_index = dis.readUnsignedShort();
            info_map.put("name_index", getCpInfoByIndex(name_index));
            // u2 descriptor_index
            int descriptor_index = dis.readUnsignedShort();
            info_map.put("descriptor_index", getCpInfoByIndex(descriptor_index));

            // u2 attribute_count;
            int attribute_count = dis.readUnsignedShort();
            info_map.put("attributes_count", attribute_count);
            /*
            attribute_info {
                u2 attribute_name_index; //属性名称在常量池中的索引
                u4 attribute_length; //属性长度
                u1 info[attribute_length]; //属性信息，不同属性的结构不同
            }
             */
            LinkedHashMap<Integer, Map<String, Object>> attr_info_index = new LinkedHashMap<>();
            getAttributeInfo(attr_info_index, attribute_count);
            info_map.put("attribute_info", attr_info_index);
            fieldsOrMethods.put(i + 1, info_map);
        }
    }

    private void getAttributeInfo(Map<Integer, Map<String, Object>> attr_info_index, int attribute_count) throws IOException {
        if (attribute_count > 0) {
            for (int i = 0; i < attribute_count; i++) {
                LinkedHashMap<String, Object> attr_info = new LinkedHashMap<>();
                int attr_name_index = dis.readUnsignedShort();
                attr_info.put("attribute_name_index", getCpInfoByIndex(attr_name_index));
                int attribute_length = dis.readInt();
                // TODO：具体解析各类属性信息
                byte[] info = new byte[attribute_length];
                dis.read(info);

                attr_info.put("attr_info", info);
                attr_info_index.put(i + 1, attr_info);
            }
        }
    }

    private String getCpInfoByIndex(int index) throws IOException {
        String cp_info, bytes_value;
        if (index > 0) {
            if (constant_pool.get(index).containsKey("constant_class_index")) {
                cp_info = (String) constant_pool.get(index).get("constant_class_index");
                if (cp_info == null) {
                    throw new IOException("[cp_info parse error!]");
                }
                return "#" + index + cp_info.substring(cp_info.indexOf(" "));
            } else if (constant_pool.get(index).containsKey("bytes")) {
                bytes_value = (String) constant_pool.get(index).get("bytes");
                if (bytes_value == null) {
                    throw new IOException("[bytes_value parse error!]");
                }
                return "#" + index + " " + bytes_value;
            } else {
                throw new IOException("[key parse error!]");
            }
        }
        return null;
    }

    private static String getTagNameByNumber(int tag) {
        switch (tag) {
            case 1:
                return "CONSTANT_Utf8_info";
            case 3:
                return "CONSTANT_Integer_info";
            case 4:
                return "CONSTANT_Float_info";
            case 5:
                return "CONSTANT_Long_info";
            case 6:
                return "CONSTANT_Double_info";
            case 7:
                return "CONSTANT_Class_info";
            case 8:
                return "CONSTANT_String_info";
            case 9:
                return "CONSTANT_Fieldref_info";
            case 10:
                return "CONSTANT_Methodref_info";
            case 11:
                return "CONSTANT_InterfaceMethodref_info";
            case 12:
                return "CONSTANT_NameAndType_info";
            case 15:
                return "CONSTANT_Method_Handle_info";
            case 16:
                return "CONSTANT_Method_Type_info";
            case 18:
                return "CONSTANT_Invoke_Dynamic_info";
        }
        return null;
    }

    public void showByteCodeInfo() throws IOException {
        if (magic != 0xCAFEBABE) {
            throw new IOException("ByteCode file doesn't be parsed");
        }
        System.out.println("   _____ _                 _____                            __   ___  \n" +
                "  / ____| |               |  __ \\                          /_ | / _ \\ \n" +
                " | |    | | __ _ ___ ___  | |__) |_ _ _ __ ___  ___ _ __    | || | | |\n" +
                " | |    | |/ _` / __/ __| |  ___/ _` | '__/ __|/ _ \\ '__|   | || | | |\n" +
                " | |____| | (_| \\__ \\__ \\ | |  | (_| | |  \\__ \\  __/ |      | || |_| |\n" +
                "  \\_____|_|\\__,_|___/___/ |_|   \\__,_|_|  |___/\\___|_|      |_(_)___/ \n" +
                "                                                                      \n" +
                "                                                                      ");
        // print ByteCode information
        FormatUtil.printJson(JSON.toJSONString(this));
    }

}
