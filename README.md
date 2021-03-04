```
   _____ _                 _____                            __   ___  
  / ____| |               |  __ \                          /_ | / _ \ 
 | |    | | __ _ ___ ___  | |__) |_ _ _ __ ___  ___ _ __    | || | | |
 | |    | |/ _` / __/ __| |  ___/ _` | '__/ __|/ _ \ '__|   | || | | |
 | |____| | (_| \__ \__ \ | |  | (_| | |  \__ \  __/ |      | || |_| |
  \_____|_|\__,_|___/___/ |_|   \__,_|_|  |___/\___|_|      |_(_)___/ 
                                                                      
                                                                      
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

目前对于属性的解析仍然不够完善，当前的做法是把解析到具体的属性信息放到一个bytes数组里，后续会加入对具体属性的解析。

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

* 具体属性的解析
* 命令行使用工具

## Example

```java
//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package class_study;

public class TestClass implements TestInterface {
    private int id = 3;

    public TestClass() {
    }

    public void hello() {
        System.out.println("Hello" + this.id);
    }
}
```

解析结果如下：

```
                                                                      
{
	"magic":-889275714,
	"minor_version":0,
	"major_version":55,
	"constant_pool_count":54,
	"constant_pool":{
		1:{
			"tag":"CONSTANT_Methodref_info",
			"class_index":"#7 CONSTANT_Class_info",
			"nameAndType_index":"#21 CONSTANT_NameAndType_info"
		},
		2:{
			"tag":"CONSTANT_Fieldref_info",
			"class_index":"#6 CONSTANT_Class_info",
			"nameAndType_index":"#22 CONSTANT_NameAndType_info"
		},
		3:{
			"tag":"CONSTANT_Fieldref_info",
			"class_index":"#23 CONSTANT_Class_info",
			"nameAndType_index":"#24 CONSTANT_NameAndType_info"
		},
		4:{
			"tag":"CONSTANT_Invoke_Dynamic_info",
			"bootstrap_method_attr_index":0,
			"nameAndType_index":"#28 CONSTANT_NameAndType_info"
		},
		5:{
			"tag":"CONSTANT_Methodref_info",
			"class_index":"#29 CONSTANT_Class_info",
			"nameAndType_index":"#30 CONSTANT_NameAndType_info"
		},
		6:{
			"tag":"CONSTANT_Class_info",
			"constant_class_index":"#31 class_study/TestClass"
		},
		7:{
			"tag":"CONSTANT_Class_info",
			"constant_class_index":"#32 java/lang/Object"
		},
		8:{
			"tag":"CONSTANT_Class_info",
			"constant_class_index":"#33 class_study/TestInterface"
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
			"length":6,
			"bytes":"<init>"
		},
		12:{
			"tag":"CONSTANT_Utf8_info",
			"length":3,
			"bytes":"()V"
		},
		13:{
			"tag":"CONSTANT_Utf8_info",
			"length":4,
			"bytes":"Code"
		},
		14:{
			"tag":"CONSTANT_Utf8_info",
			"length":15,
			"bytes":"LineNumberTable"
		},
		15:{
			"tag":"CONSTANT_Utf8_info",
			"length":18,
			"bytes":"LocalVariableTable"
		},
		16:{
			"tag":"CONSTANT_Utf8_info",
			"length":4,
			"bytes":"this"
		},
		17:{
			"tag":"CONSTANT_Utf8_info",
			"length":23,
			"bytes":"Lclass_study/TestClass;"
		},
		18:{
			"tag":"CONSTANT_Utf8_info",
			"length":5,
			"bytes":"hello"
		},
		19:{
			"tag":"CONSTANT_Utf8_info",
			"length":10,
			"bytes":"SourceFile"
		},
		20:{
			"tag":"CONSTANT_Utf8_info",
			"length":14,
			"bytes":"TestClass.java"
		},
		21:{
			"tag":"CONSTANT_NameAndType_info",
			"constant_name_index":"#11 <init>",
			"constant_type_index":"#12 ()V"
		},
		22:{
			"tag":"CONSTANT_NameAndType_info",
			"constant_name_index":"#9 id",
			"constant_type_index":"#10 I"
		},
		23:{
			"tag":"CONSTANT_Class_info",
			"constant_class_index":"#34 java/lang/System"
		},
		24:{
			"tag":"CONSTANT_NameAndType_info",
			"constant_name_index":"#35 out",
			"constant_type_index":"#36 Ljava/io/PrintStream;"
		},
		25:{
			"tag":"CONSTANT_Utf8_info",
			"length":16,
			"bytes":"BootstrapMethods"
		},
		26:{
			"tag":"CONSTANT_Method_Handle_info",
			"reference_kind":6,
			"reference_index":"#37 CONSTANT_Methodref_info"
		},
		27:{
			"tag":"CONSTANT_String_info",
			"constant_string_index":"#38 Hello\u0001"
		},
		28:{
			"tag":"CONSTANT_NameAndType_info",
			"constant_name_index":"#39 makeConcatWithConstants",
			"constant_type_index":"#40 (I)Ljava/lang/String;"
		},
		29:{
			"tag":"CONSTANT_Class_info",
			"constant_class_index":"#41 java/io/PrintStream"
		},
		30:{
			"tag":"CONSTANT_NameAndType_info",
			"constant_name_index":"#42 println",
			"constant_type_index":"#43 (Ljava/lang/String;)V"
		},
		31:{
			"tag":"CONSTANT_Utf8_info",
			"length":21,
			"bytes":"class_study/TestClass"
		},
		32:{
			"tag":"CONSTANT_Utf8_info",
			"length":16,
			"bytes":"java/lang/Object"
		},
		33:{
			"tag":"CONSTANT_Utf8_info",
			"length":25,
			"bytes":"class_study/TestInterface"
		},
		34:{
			"tag":"CONSTANT_Utf8_info",
			"length":16,
			"bytes":"java/lang/System"
		},
		35:{
			"tag":"CONSTANT_Utf8_info",
			"length":3,
			"bytes":"out"
		},
		36:{
			"tag":"CONSTANT_Utf8_info",
			"length":21,
			"bytes":"Ljava/io/PrintStream;"
		},
		37:{
			"tag":"CONSTANT_Methodref_info",
			"class_index":"#44 CONSTANT_Class_info",
			"nameAndType_index":"#45 CONSTANT_NameAndType_info"
		},
		38:{
			"tag":"CONSTANT_Utf8_info",
			"length":6,
			"bytes":"Hello\u0001"
		},
		39:{
			"tag":"CONSTANT_Utf8_info",
			"length":23,
			"bytes":"makeConcatWithConstants"
		},
		40:{
			"tag":"CONSTANT_Utf8_info",
			"length":21,
			"bytes":"(I)Ljava/lang/String;"
		},
		41:{
			"tag":"CONSTANT_Utf8_info",
			"length":19,
			"bytes":"java/io/PrintStream"
		},
		42:{
			"tag":"CONSTANT_Utf8_info",
			"length":7,
			"bytes":"println"
		},
		43:{
			"tag":"CONSTANT_Utf8_info",
			"length":21,
			"bytes":"(Ljava/lang/String;)V"
		},
		44:{
			"tag":"CONSTANT_Class_info",
			"constant_class_index":"#46 java/lang/invoke/StringConcatFactory"
		},
		45:{
			"tag":"CONSTANT_NameAndType_info",
			"constant_name_index":"#39 makeConcatWithConstants",
			"constant_type_index":"#50 (Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/invoke/CallSite;"
		},
		46:{
			"tag":"CONSTANT_Utf8_info",
			"length":36,
			"bytes":"java/lang/invoke/StringConcatFactory"
		},
		47:{
			"tag":"CONSTANT_Class_info",
			"constant_class_index":"#52 java/lang/invoke/MethodHandles$Lookup"
		},
		48:{
			"tag":"CONSTANT_Utf8_info",
			"length":6,
			"bytes":"Lookup"
		},
		49:{
			"tag":"CONSTANT_Utf8_info",
			"length":12,
			"bytes":"InnerClasses"
		},
		50:{
			"tag":"CONSTANT_Utf8_info",
			"length":152,
			"bytes":"(Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/invoke/CallSite;"
		},
		51:{
			"tag":"CONSTANT_Class_info",
			"constant_class_index":"#53 java/lang/invoke/MethodHandles"
		},
		52:{
			"tag":"CONSTANT_Utf8_info",
			"length":37,
			"bytes":"java/lang/invoke/MethodHandles$Lookup"
		},
		53:{
			"tag":"CONSTANT_Utf8_info",
			"length":30,
			"bytes":"java/lang/invoke/MethodHandles"
		}
	},
	"access_flags":33,
	"this_class":"#6 class_study/TestClass",
	"super_class":"#7 java/lang/Object",
	"interfaces_count":1,
	"interfaces_info_array":[
		"#8 class_study/TestInterface"
	],
	"fields_count":1,
	"fields":{
		1:{
			"access_flags":2,
			"name_index":"#9 id",
			"descriptor_index":"#10 I",
			"attributes_count":0,
			"attribute_info":{
				
			}
		}
	},
	"methods_count":2,
	"methods":{
		1:{
			"access_flags":1,
			"name_index":"#11 <init>",
			"descriptor_index":"#12 ()V",
			"attributes_count":1,
			"attribute_info":{
				1:{
					"attribute_name_index":"#13 Code",
					"attr_info":"AAIAAQAAAAoqtwABKga1AAKxAAAAAgAOAAAADgADAAAACgAEAAsACQAMAA8AAAAMAAEAAAAKABAAEQAA"
				}
			}
		},
		2:{
			"access_flags":1,
			"name_index":"#18 hello",
			"descriptor_index":"#12 ()V",
			"attributes_count":1,
			"attribute_info":{
				1:{
					"attribute_name_index":"#13 Code",
					"attr_info":"AAIAAQAAABCyAAMqtAACugAEAAC2AAWxAAAAAgAOAAAACgACAAAAEAAPABEADwAAAAwAAQAAABAAEAARAAA="
				}
			}
		}
	},
	"attribute_count":3,
	"attributes":{
		1:{
			"attribute_name_index":"#19 SourceFile",
			"attr_info":"ABQ="
		},
		2:{
			"attribute_name_index":"#49 InnerClasses",
			"attr_info":"AAEALwAzADAAGQ=="
		},
		3:{
			"attribute_name_index":"#25 BootstrapMethods",
			"attr_info":"AAEAGgABABs="
		}
	}
}
```

## Log

* version 1.0：实现字节码文件基本信息解析

## Refer

* `FormatUtil`类来自于作者 *lizhgb* 开源的代码
