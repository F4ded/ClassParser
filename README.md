```
   _____ _                 _____                           
  / ____| |               |  __ \                           
 | |    | | __ _ ___ ___  | |__) |_ _ _ __ ___  ___ _ __    
 | |    | |/ _` / __/ __| |  ___/ _` | '__/ __|/ _ \ '__|   
 | |____| | (_| \__ \__ \ | |  | (_| | |  \__ \  __/ |      
  \_____|_|\__,_|___/___/ |_|   \__,_|_|  |___/\___|_|      
                                                                      
                                                                      
```

## Start

一个自己实现的Java字节码文件解析器，目前已经可以解析：

* 魔数
* 版本信息
* 常量池
* 访问标志
* 本类、父类信息
* 接口信息
* 字段、方法信息
* 属性

该解析器会把解析到的信息以json格式的字符串打印出来，修改`Main.java`中`.class`文件的路径，即可在控制台打印出解析出的信息。

由于属性的结构比较复杂，所以目前只可以解析部分属性：

* Code
* ConstantValue
* LineNumberTable
* LocalVariableTable
* SourceFile
* InnerClasses
* BootstrapMethods
* Exceptions

**Code 属性中的虚拟机指令（Opcodes）还不能解析，当前会把指令放到一个 byte 数组中，等我学会了虚拟机指令映射再来完善这一功能😐。**

## Dependence

* 使用的JDK版本为11
* Fastjson 1.2.50
* lombok 1.18.18

```xml
        <dependency>
            <groupId>com.alibaba</groupId>
            <artifactId>fastjson</artifactId>
            <version>1.2.50</version>
        </dependency>

        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <version>1.18.18</version>
        </dependency>
```

## *TODO*

待实现功能：

* **实现更多属性的解析**
* **解析Code属性中的虚拟机指令**
* **命令行使用工具**

## Example

```java
//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package class_study;

import java.io.IOException;

public class TestClass implements TestInterface {
    private int id = 3;
    private static final String str = "test";

    public TestClass() {
    }

    public void hello() throws IOException {
        System.out.println("Hello" + this.id);
    }
}

```

解析结果如下：

```json
{
	"magic":-889275714,
	"minor_version":0,
	"major_version":55,
	"constant_pool_count":62,
	"constant_pool":{
		1:{
			"tag":"CONSTANT_Methodref_info",
			"class_index":"#7 CONSTANT_Class_info",
			"nameAndType_index":"#27 CONSTANT_NameAndType_info"
		},
		2:{
			"tag":"CONSTANT_Fieldref_info",
			"class_index":"#6 CONSTANT_Class_info",
			"nameAndType_index":"#28 CONSTANT_NameAndType_info"
		},
		3:{
			"tag":"CONSTANT_Fieldref_info",
			"class_index":"#29 CONSTANT_Class_info",
			"nameAndType_index":"#30 CONSTANT_NameAndType_info"
		},
		4:{
			"tag":"CONSTANT_Invoke_Dynamic_info",
			"bootstrap_method_attr_index":0,
			"nameAndType_index":"#34 CONSTANT_NameAndType_info"
		},
		5:{
			"tag":"CONSTANT_Methodref_info",
			"class_index":"#35 CONSTANT_Class_info",
			"nameAndType_index":"#36 CONSTANT_NameAndType_info"
		},
		6:{
			"tag":"CONSTANT_Class_info",
			"constant_class_index":"#37 class_study/TestClass"
		},
		7:{
			"tag":"CONSTANT_Class_info",
			"constant_class_index":"#38 java/lang/Object"
		},
		8:{
			"tag":"CONSTANT_Class_info",
			"constant_class_index":"#39 class_study/TestInterface"
		},
		9:{
			"tag":"CONSTANT_Utf8_info",
			"length":2,
			"bytes":"id"
		},
		10:{
			"tag":"CONSTANT_Utf8_info",
			"length":1,
			"bytes":"I"
		},
		11:{
			"tag":"CONSTANT_Utf8_info",
			"length":3,
			"bytes":"str"
		},
		12:{
			"tag":"CONSTANT_Utf8_info",
			"length":18,
			"bytes":"Ljava/lang/String;"
		},
		13:{
			"tag":"CONSTANT_Utf8_info",
			"length":13,
			"bytes":"ConstantValue"
		},
		14:{
			"tag":"CONSTANT_String_info",
			"constant_string_index":"#40 test"
		},
		15:{
			"tag":"CONSTANT_Utf8_info",
			"length":6,
			"bytes":"<init>"
		},
		16:{
			"tag":"CONSTANT_Utf8_info",
			"length":3,
			"bytes":"()V"
		},
		17:{
			"tag":"CONSTANT_Utf8_info",
			"length":4,
			"bytes":"Code"
		},
		18:{
			"tag":"CONSTANT_Utf8_info",
			"length":15,
			"bytes":"LineNumberTable"
		},
		19:{
			"tag":"CONSTANT_Utf8_info",
			"length":18,
			"bytes":"LocalVariableTable"
		},
		20:{
			"tag":"CONSTANT_Utf8_info",
			"length":4,
			"bytes":"this"
		},
		21:{
			"tag":"CONSTANT_Utf8_info",
			"length":23,
			"bytes":"Lclass_study/TestClass;"
		},
		22:{
			"tag":"CONSTANT_Utf8_info",
			"length":5,
			"bytes":"hello"
		},
		23:{
			"tag":"CONSTANT_Utf8_info",
			"length":10,
			"bytes":"Exceptions"
		},
		24:{
			"tag":"CONSTANT_Class_info",
			"constant_class_index":"#41 java/io/IOException"
		},
		25:{
			"tag":"CONSTANT_Utf8_info",
			"length":10,
			"bytes":"SourceFile"
		},
		26:{
			"tag":"CONSTANT_Utf8_info",
			"length":14,
			"bytes":"TestClass.java"
		},
		27:{
			"tag":"CONSTANT_NameAndType_info",
			"constant_name_index":"#15 <init>",
			"constant_type_index":"#16 ()V"
		},
		28:{
			"tag":"CONSTANT_NameAndType_info",
			"constant_name_index":"#9 id",
			"constant_type_index":"#10 I"
		},
		29:{
			"tag":"CONSTANT_Class_info",
			"constant_class_index":"#42 java/lang/System"
		},
		30:{
			"tag":"CONSTANT_NameAndType_info",
			"constant_name_index":"#43 out",
			"constant_type_index":"#44 Ljava/io/PrintStream;"
		},
		31:{
			"tag":"CONSTANT_Utf8_info",
			"length":16,
			"bytes":"BootstrapMethods"
		},
		32:{
			"tag":"CONSTANT_Method_Handle_info",
			"reference_kind":6,
			"reference_index":"#45 CONSTANT_Methodref_info"
		},
		33:{
			"tag":"CONSTANT_String_info",
			"constant_string_index":"#46 Hello\u0001"
		},
		34:{
			"tag":"CONSTANT_NameAndType_info",
			"constant_name_index":"#47 makeConcatWithConstants",
			"constant_type_index":"#48 (I)Ljava/lang/String;"
		},
		35:{
			"tag":"CONSTANT_Class_info",
			"constant_class_index":"#49 java/io/PrintStream"
		},
		36:{
			"tag":"CONSTANT_NameAndType_info",
			"constant_name_index":"#50 println",
			"constant_type_index":"#51 (Ljava/lang/String;)V"
		},
		37:{
			"tag":"CONSTANT_Utf8_info",
			"length":21,
			"bytes":"class_study/TestClass"
		},
		38:{
			"tag":"CONSTANT_Utf8_info",
			"length":16,
			"bytes":"java/lang/Object"
		},
		39:{
			"tag":"CONSTANT_Utf8_info",
			"length":25,
			"bytes":"class_study/TestInterface"
		},
		40:{
			"tag":"CONSTANT_Utf8_info",
			"length":4,
			"bytes":"test"
		},
		41:{
			"tag":"CONSTANT_Utf8_info",
			"length":19,
			"bytes":"java/io/IOException"
		},
		42:{
			"tag":"CONSTANT_Utf8_info",
			"length":16,
			"bytes":"java/lang/System"
		},
		43:{
			"tag":"CONSTANT_Utf8_info",
			"length":3,
			"bytes":"out"
		},
		44:{
			"tag":"CONSTANT_Utf8_info",
			"length":21,
			"bytes":"Ljava/io/PrintStream;"
		},
		45:{
			"tag":"CONSTANT_Methodref_info",
			"class_index":"#52 CONSTANT_Class_info",
			"nameAndType_index":"#53 CONSTANT_NameAndType_info"
		},
		46:{
			"tag":"CONSTANT_Utf8_info",
			"length":6,
			"bytes":"Hello\u0001"
		},
		47:{
			"tag":"CONSTANT_Utf8_info",
			"length":23,
			"bytes":"makeConcatWithConstants"
		},
		48:{
			"tag":"CONSTANT_Utf8_info",
			"length":21,
			"bytes":"(I)Ljava/lang/String;"
		},
		49:{
			"tag":"CONSTANT_Utf8_info",
			"length":19,
			"bytes":"java/io/PrintStream"
		},
		50:{
			"tag":"CONSTANT_Utf8_info",
			"length":7,
			"bytes":"println"
		},
		51:{
			"tag":"CONSTANT_Utf8_info",
			"length":21,
			"bytes":"(Ljava/lang/String;)V"
		},
		52:{
			"tag":"CONSTANT_Class_info",
			"constant_class_index":"#54 java/lang/invoke/StringConcatFactory"
		},
		53:{
			"tag":"CONSTANT_NameAndType_info",
			"constant_name_index":"#47 makeConcatWithConstants",
			"constant_type_index":"#58 (Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/invoke/CallSite;"
		},
		54:{
			"tag":"CONSTANT_Utf8_info",
			"length":36,
			"bytes":"java/lang/invoke/StringConcatFactory"
		},
		55:{
			"tag":"CONSTANT_Class_info",
			"constant_class_index":"#60 java/lang/invoke/MethodHandles$Lookup"
		},
		56:{
			"tag":"CONSTANT_Utf8_info",
			"length":6,
			"bytes":"Lookup"
		},
		57:{
			"tag":"CONSTANT_Utf8_info",
			"length":12,
			"bytes":"InnerClasses"
		},
		58:{
			"tag":"CONSTANT_Utf8_info",
			"length":152,
			"bytes":"(Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/invoke/CallSite;"
		},
		59:{
			"tag":"CONSTANT_Class_info",
			"constant_class_index":"#61 java/lang/invoke/MethodHandles"
		},
		60:{
			"tag":"CONSTANT_Utf8_info",
			"length":37,
			"bytes":"java/lang/invoke/MethodHandles$Lookup"
		},
		61:{
			"tag":"CONSTANT_Utf8_info",
			"length":30,
			"bytes":"java/lang/invoke/MethodHandles"
		}
	},
	"access_flags":33,
	"this_class":"#6  class_study/TestClass",
	"super_class":"#7  java/lang/Object",
	"interfaces_count":1,
	"interfaces_info_array":[
		"#8  class_study/TestInterface"
	],
	"fields_count":2,
	"fields":{
		"field #0":{
			"access_flags":2,
			"name_index":"#9 id",
			"descriptor_index":"#10 I",
			"attributes_count":0,
			"attribute_info":{
				
			}
		},
		"field #1":{
			"access_flags":26,
			"name_index":"#11 str",
			"descriptor_index":"#12 Ljava/lang/String;",
			"attributes_count":1,
			"attribute_info":{
				"attribute #0":{
					"attribute_name_index":"#13 ConstantValue",
					"attribute_length":2,
					"constant_value_index":"#14  test"
				}
			}
		}
	},
	"methods_count":2,
	"methods":{
		"method #0":{
			"access_flags":1,
			"name_index":"#15 <init>",
			"descriptor_index":"#16 ()V",
			"attributes_count":1,
			"attribute_info":{
				"attribute #0":{
					"attribute_name_index":"#17 Code",
					"attribute_length":60,
					"max_stack":2,
					"max_locals":1,
					"code_length":10,
					"code":"KrcAASoGtQACsQ==",
					"exception_table_length":0,
					"attributes_count":2,
					"Code_attribute_info":{
						"attribute #0":{
							"attribute_name_index":"#18 LineNumberTable",
							"attribute_length":14,
							"line_number_table_length":3,
							"line_number_table #0":{
								"start_pc":0,
								"line_number":13
							},
							"line_number_table #1":{
								"start_pc":4,
								"line_number":14
							},
							"line_number_table #2":{
								"start_pc":9,
								"line_number":15
							}
						},
						"attribute #1":{
							"attribute_name_index":"#19 LocalVariableTable",
							"attribute_length":12,
							"local_variable_table_length":1,
							"local_variable_table #0":{
								"start_pc":0,
								"length":10,
								"name_index":"#20 this",
								"index":"#21 Lclass_study/TestClass;"
							}
						}
					}
				}
			}
		},
		"method #1":{
			"access_flags":1,
			"name_index":"#22 hello",
			"descriptor_index":"#16 ()V",
			"attributes_count":2,
			"attribute_info":{
				"attribute #0":{
					"attribute_name_index":"#17 Code",
					"attribute_length":62,
					"max_stack":2,
					"max_locals":1,
					"code_length":16,
					"code":"sgADKrQAAroABAAAtgAFsQ==",
					"exception_table_length":0,
					"attributes_count":2,
					"Code_attribute_info":{
						"attribute #0":{
							"attribute_name_index":"#18 LineNumberTable",
							"attribute_length":10,
							"line_number_table_length":2,
							"line_number_table #0":{
								"start_pc":0,
								"line_number":19
							},
							"line_number_table #1":{
								"start_pc":15,
								"line_number":20
							}
						},
						"attribute #1":{
							"attribute_name_index":"#19 LocalVariableTable",
							"attribute_length":12,
							"local_variable_table_length":1,
							"local_variable_table #0":{
								"start_pc":0,
								"length":16,
								"name_index":"#20 this",
								"index":"#21 Lclass_study/TestClass;"
							}
						}
					}
				},
				"attribute #1":{
					"attribute_name_index":"#23 Exceptions",
					"attribute_length":4,
					"number_of_exceptions":1,
					"exception_index_table":[
						"#24  java/io/IOException"
					]
				}
			}
		}
	},
	"attribute_count":3,
	"attributes":{
		"attribute #0":{
			"attribute_name_index":"#25 SourceFile",
			"attribute_length":2,
			"source_file_index":"#26 TestClass.java"
		},
		"attribute #1":{
			"attribute_name_index":"#57 InnerClasses",
			"attribute_length":10,
			"number_of_classes":1,
			"classes #0":{
				"inner_class_info_index":"#55  java/lang/invoke/MethodHandles$Lookup",
				"outer_class_info_index":"#59  java/lang/invoke/MethodHandles",
				"inner_name_index":"#56 Lookup",
				"inner_class_access":25
			}
		},
		"attribute #2":{
			"attribute_name_index":"#31 BootstrapMethods",
			"attribute_length":8,
			"num_bootstrap_methods":1,
			"bootstrap_methods #0":{
				"bootstrap_method_ref":"#32  CONSTANT_Methodref_info",
				"num_bootstrap_arguments":1,
				"bootstrap_arguments":[
					"#33  Hello\u0001"
				]
			}
		}
	}
}
```

## Log

* ***version 1.1：***
  * 实现了部分属性的解析
  * 优化索引算法
  * 修复了一些bug
* ***version 1.0：***
  * 实现字节码文件基本信息解析

## Refer

* `FormatUtil`类来自于作者 *lizhgb* 开源的**json字符串格式化**的代码
