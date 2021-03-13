package class_parser;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.Getter;
import org.objectweb.asm.Opcodes;
import utils.FormatUtil;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class ClassParser implements Opcodes {
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
    private Map<String, Map<String, Object>> fields;
    @Getter
    @JSONField(ordinal = 12)
    private int methods_count;
    @Getter
    @JSONField(ordinal = 13)
    private Map<String, Map<String, Object>> methods;
    @Getter
    @JSONField(ordinal = 14)
    private int attribute_count;
    @Getter
    @JSONField(ordinal = 15)
    private Map<String, Map<String, Object>> attributes;

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
        getFieldsOrMethodInfo(fields, fields_count, false);

        /*
        解析方法
        u2 methods_counts
         */
        this.methods_count = dis.readUnsignedShort();
        methods = new LinkedHashMap<>();
        getFieldsOrMethodInfo(methods, methods_count, true);

        /*
        解析属性
        u2 attribute_count
         */
        this.attribute_count = dis.readUnsignedShort();
        attributes = new LinkedHashMap<>();
        getAttributeInfo(attributes, this.attribute_count);
    }

    /**
     * 具体解析各类属性信息
     *
     * @param attr_info_index 包含属性信息的Map (带索引值)
     * @param attribute_count 属性数量
     * @throws IOException
     */
    private void getAttributeInfo(Map<String, Map<String, Object>> attr_info_index, int attribute_count) throws IOException {
        if (attribute_count > 0) {
            for (int i = 0; i < attribute_count; i++) {
                /*
                Map<String, Object>来存储具体属性信息
                 */
                LinkedHashMap<String, Object> attr_info = new LinkedHashMap<>();
                int attr_name_index = dis.readUnsignedShort();
                attr_info.put("attribute_name_index", getCpInfoByIndex(attr_name_index));
                int attribute_length = dis.readInt();
                attr_info.put("attribute_length", attribute_length);

                // TODO：具体解析各类属性信息
                switch (Objects.requireNonNull(getCpInfoByIndex(attr_name_index)).substring(Objects.requireNonNull(getCpInfoByIndex(attr_name_index)).indexOf(" ") + 1)) {
                     /*
                     ConstantValue_attribute {
                         u2 attribute_name_index;
                         u4 attribute_length;
                         u2 constantValue_index;
                     }
                     */
                    case "ConstantValue": {
                        attr_info.put("constant_value_index", getCpInfoByIndex(dis.readUnsignedShort()));
                        break;
                    }
                    /*
                     Code_attribute {
                         u2 attribute_name_index;
                         u4 attribute_length;
                         u2 max_stack;
                         u2 max_locals;
                         u4 code_length;
                         u1 code[code_length];
                         u2 exception_table_length;
                         { u2 start_pc;
                           u2 end_pc;
                           u2 handler_pc;
                           u2 catch_type;
                         } exception_table[exception_table_length];
                         u2 attributes_count;
                         attribute_info attributes[attributes_count];
                    }
                     */
                    case "Code": {
                        attr_info.put("max_stack", dis.readUnsignedShort());
                        attr_info.put("max_locals", dis.readUnsignedShort());
                        int code_length = dis.readInt();
                        attr_info.put("code_length", code_length);
                        byte[] code = new byte[code_length];

                        // TODO:解析虚拟机指令
                        dis.read(code);
                        attr_info.put("code", Arrays.toString(code));

                        int exception_table_length = dis.readUnsignedShort();
                        attr_info.put("exception_table_length", exception_table_length);
                        // TODO:解析异常表
                        for (int j = 0; j < exception_table_length; j++) {
                            LinkedHashMap<String, Object> exception_table = new LinkedHashMap<>();
                            int start_pc = dis.readUnsignedShort();
                            exception_table.put("start_pc", start_pc);
                            int end_pc = dis.readUnsignedShort();
                            exception_table.put("end_pc", end_pc);
                            int handler_pc = dis.readUnsignedShort();
                            exception_table.put("handler_pc", handler_pc);
                            int catch_type = dis.readUnsignedShort();
                            exception_table.put("catch_type", getCpInfoByIndex(catch_type));

                            attr_info.put("exception_table #" + j, exception_table);
                        }

                        int attributes_count = dis.readUnsignedShort();
                        attr_info.put("attributes_count", attributes_count);

                        Map<String, Map<String, Object>> code_attr = new LinkedHashMap<>();
                        // 递归调用，继续解析 Code 属性中的属性
                        getAttributeInfo(code_attr, attributes_count);
                        attr_info.put("Code_attribute_info", code_attr);
                        break;
                    }

                    case "LineNumberTable": {
                        int line_number_table_length = dis.readUnsignedShort();
                        attr_info.put("line_number_table_length", line_number_table_length);
                        for (int j = 0; j < line_number_table_length; j++) {
                            LinkedHashMap<String, Object> line_number_table = new LinkedHashMap<>();
                            int start_pc = dis.readUnsignedShort();
                            int line_number = dis.readUnsignedShort();
                            line_number_table.put("start_pc", start_pc);
                            line_number_table.put("line_number", line_number);
                            attr_info.put("line_number_table #" + j, line_number_table);
                        }
                        break;
                    }
                    /*
                    LocalVariableTable_attribute {
                        u2 attribute_name_index;
                        u4 attribute_length;
                        u2 local_variable_table_length;
                        {   u2 start_pc;
                            u2 length;
                            u2 name_index;
                            u2 descriptor_index;
                            u2 index;
                        } local_variable_table[local_variable_table_length];
                    }
                     */
                    case "LocalVariableTable": {
                        int local_variable_table_length = dis.readUnsignedShort();
                        attr_info.put("local_variable_table_length", local_variable_table_length);

                        for (int j = 0; j < local_variable_table_length; j++) {
                            LinkedHashMap<String, Object> local_variable_table = new LinkedHashMap<>();
                            int start_pc = dis.readUnsignedShort();
                            int length = dis.readUnsignedShort();
                            int name_index = dis.readUnsignedShort();
                            int descriptor_index = dis.readUnsignedShort();
                            int index = dis.readUnsignedShort();

                            local_variable_table.put("start_pc", start_pc);
                            local_variable_table.put("length", length);
                            local_variable_table.put("name_index", getCpInfoByIndex(name_index));
                            local_variable_table.put("descriptor_index", getCpInfoByIndex(descriptor_index));
                            local_variable_table.put("index", index);

                            attr_info.put("local_variable_table #" + j, local_variable_table);
                        }
                        break;
                    }
                    /*
                    SourceFile_attribute {
                        u2 attribute_name_index;
                        u4 attribute_length;
                        u2 sourcefile_index;
                    }
                     */
                    case "SourceFile": {
                        int source_file_index = dis.readUnsignedShort();
                        attr_info.put("source_file_index", getCpInfoByIndex(source_file_index));
                        break;
                    }
                    /*
                    InnerClasses_attribute {
                        u2 attribute_name_index;
                        u4 attribute_length;
                        u2 number_of_classes;
                        {   u2 inner_class_info_index;
                            u2 outer_class_info_index;
                            u2 inner_name_index;
                            u2 inner_class_access_flags;
                        } classes[number_of_classes];
                    }
                     */
                    case "InnerClasses": {
                        int number_of_classes = dis.readUnsignedShort();
                        attr_info.put("number_of_classes", number_of_classes);
                        for (int j = 0; j < number_of_classes; j++) {
                            LinkedHashMap<String, Object> classes = new LinkedHashMap<>();
                            // u2 inner_class_info_index;
                            int inner_class_info_index = dis.readUnsignedShort();
                            classes.put("inner_class_info_index", getCpInfoByIndex(inner_class_info_index));
                            // u2 outer_class_info_index;
                            int outer_class_info_index = dis.readUnsignedShort();
                            classes.put("outer_class_info_index", getCpInfoByIndex(outer_class_info_index));
                            // u2 inner_name_index;
                            int inner_name_index = dis.readUnsignedShort();
                            classes.put("inner_name_index", getCpInfoByIndex(inner_name_index));
                            // u2 inner_class_access_flags;
                            int inner_class_access = dis.readUnsignedShort();
                            classes.put("inner_class_access", inner_class_access);

                            attr_info.put("classes #" + j, classes);
                        }
                        break;
                    }
                    /*
                    BootstrapMethods_attribute {
                        u2 attribute_name_index;
                        u4 attribute_length;
                        u2 num_bootstrap_methods;
                        {   u2 bootstrap_method_ref;
                            u2 num_bootstrap_arguments;
                            u2 bootstrap_arguments[num_bootstrap_arguments];
                        } bootstrap_methods[num_bootstrap_methods];
                    }
                     */
                    case "BootstrapMethods": {
                        // u2 num_bootstrap_methods;
                        int num_bootstrap_methods = dis.readUnsignedShort();
                        attr_info.put("num_bootstrap_methods", num_bootstrap_methods);
                        for (int j = 0; j < num_bootstrap_methods; j++) {
                            LinkedHashMap<String, Object> bootstrap_methods = new LinkedHashMap<>();
                            // u2 bootstrap_method_ref;
                            int bootstrap_method_ref = dis.readUnsignedShort();
                            bootstrap_methods.put("bootstrap_method_ref", getCpInfoByIndex(bootstrap_method_ref));
                            // u2 num_bootstrap_arguments;
                            int num_bootstrap_arguments = dis.readUnsignedShort();
                            bootstrap_methods.put("num_bootstrap_arguments", num_bootstrap_arguments);
                            // u2 bootstrap_arguments[num_bootstrap_arguments];
                            String[] bootstrap_arguments = new String[num_bootstrap_arguments];
                            for (int k = 0; k < num_bootstrap_arguments; k++) {
                                bootstrap_arguments[k] = getCpInfoByIndex(dis.readUnsignedShort());
                            }
                            bootstrap_methods.put("bootstrap_arguments", bootstrap_arguments);
                            attr_info.put("bootstrap_methods #" + j, bootstrap_methods);
                        }
                        break;
                    }
                    /*
                    Exceptions_attribute {
                        u2 attribute_name_index;
                        u4 attribute_length;
                        u2 number_of_exceptions;
                        u2 exception_index_table[number_of_exceptions];
                    }
                     */
                    case "Exceptions": {
                        // u2 number_of_exceptions;
                        int number_of_exceptions = dis.readUnsignedShort();
                        attr_info.put("number_of_exceptions", number_of_exceptions);
                        // u2 exception_index_table[number_of_exceptions];
                        String[] exception_index_table = new String[number_of_exceptions];
                        for (int j = 0; j < number_of_exceptions; j++) {
                            int exception_index = dis.readUnsignedShort();
                            exception_index_table[j] = getCpInfoByIndex(exception_index);
                        }
                        attr_info.put("exception_index_table", exception_index_table);
                        break;
                    }
                    /*
                    attribute_length must be 0x00000000
                     */
                    case "Deprecated":
                    case "Synthetic": {
                        if (attribute_length != 0x00) {
                            throw new IOException("[Deprecated or Synthetic attribute parse error!]");
                        }
                        break;
                    }
                    /*
                    Signature_attribute {
                        u2 attribute_name_index;
                        u4 attribute_length;
                        u2 signature_index;
                    }
                     */
                    case "Signature": {
                        int signature_index = dis.readUnsignedShort();
                        attr_info.put("signature_index", getCpInfoByIndex(signature_index));
                        break;
                    }
                    /*
                    MethodParameters_attribute {
                        u2 attribute_name_index;
                        u4 attribute_length;
                        u1 parameters_count;
                        {   u2 name_index;
                            u2 access_flags;
                        } parameters[parameters_count];
                    }
                     */
                    case "MethodParameters": {
                        int parameters_count = dis.readUnsignedByte();
                        attr_info.put("parameter_count", parameters_count);
                        for (int j = 0; j < parameters_count; j++) {
                            LinkedHashMap<String, Object> parameters = new LinkedHashMap<>();
                            int name_index = dis.readUnsignedShort();
                            parameters.put("name_index", getCpInfoByIndex(name_index));
                            int access_flags = dis.readUnsignedShort();
                            parameters.put("access_flags", access_flags);
                            attr_info.put("parameters #" + j, parameters);
                        }
                    }

                    default: {
                        byte[] info = new byte[attribute_length];
                        dis.read(info);
                        attr_info.put("info", info);
                    }
                }

                /*
                最后放入到带索引信息的总Map中
                 */
                attr_info_index.put("attribute #" + i, attr_info);
            }
        }
    }


    /**
     * 解析字段信息和方法信息
     *
     * @param fieldsOrMethods 包含字段或者方法信息的Map
     * @param count           字段或者方法的数量
     * @throws IOException
     */
    private void getFieldsOrMethodInfo(Map<String, Map<String, Object>> fieldsOrMethods, int count, boolean isMethod) throws IOException {
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
            LinkedHashMap<String, Map<String, Object>> attr_info_index = new LinkedHashMap<>();
            getAttributeInfo(attr_info_index, attribute_count);
            info_map.put("attribute_info", attr_info_index);

            if (isMethod) {
                fieldsOrMethods.put("method #" + i, info_map);
            } else {
                fieldsOrMethods.put("field #" + i, info_map);
            }
        }
    }

    /**
     * 根据索引值获取在常量池中的信息
     *
     * @param index 常量池中的索引
     * @return 该索引在常量池中对应的元素
     * @throws IOException
     */
    private String getCpInfoByIndex(int index) throws IOException {
        String result = null;
        if (index > 0) {
            Set<String> keys = constant_pool.get(index).keySet();
            for (String key : keys) {
                if (key.endsWith("_index")) {
                    result = (String) constant_pool.get(index).get(key);
                    return "#" + index + " " + result.substring(result.indexOf(" "));
                } else if (key.endsWith("bytes")) {
                    result = (String) constant_pool.get(index).get("bytes");
                    return "#" + index + " " + result;
                }
            }
            throw new IOException("[Key Error!]");
        } else {
            throw new IOException("[Index Error!]: " + index);
        }
//        if (index > 0) {
//            if (constant_pool.get(index).containsKey("constant_class_index")) {
//                cp_info = (String) constant_pool.get(index).get("constant_class_index");
//                if (cp_info == null) {
//                    throw new IOException("[cp_info parse error!]");
//                }
//                return "#" + index + cp_info.substring(cp_info.indexOf(" "));
//            } else if (constant_pool.get(index).containsKey("bytes")) {
//                bytes_value = (String) constant_pool.get(index).get("bytes");
//                if (bytes_value == null) {
//                    throw new IOException("[bytes_value parse error!]");
//                }
//                return "#" + index + " " + bytes_value;
//            } else if (constant_pool.get(index).containsKey("constant_string_index")) {
//                constant_string_value = (String) constant_pool.get(index).get("constant_string_index");
//                if (constant_string_value == null) {
//                    throw new IOException("[string_info parse error!]");
//                }
//                return "#" + index + constant_string_value.substring(constant_string_value.indexOf(" "));
//            } else if (constant_pool.get(index).containsKey("reference_index")) {
//                reference_index = (String) constant_pool.get(index).get("reference_index");
//                if (reference_index == null) {
//                    throw new IOException("[reference_index parse error!]");
//                }
//                return "#" + index + reference_index.substring(reference_index.indexOf(" "));
//            } else {
//                throw new IOException("[key parse error!]");
//            }
//        }
    }

    /**
     * 根据tag来获取对应的tag_name
     *
     * @param tag 标签 (int)
     * @return tag_name (String)
     */
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

    /**
     * 以JSON字符串的形式输出字节码的信息
     *
     * @throws IOException
     */
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
//        System.out.println(JSON.toJSONString(this));
    }
}
