# 构造方法快速生成get set 方法代码模板

## customGetter

```java
#if($field.modifierStatic)
static ##
#end
$field.type ##
$field.name ##
() {
return $field.name;
}
```

## customSetter

```java
#set($paramName = $helper.getParamName($field, $project))
public ##
#if($field.modifierStatic)
static void ##
#else
    $classSignature ##
#end
$field.name ($field.type $paramName) {
#if ($field.name == $paramName)
    #if (!$field.modifierStatic)
    this.##
    #else
        $classname.##
    #end
#end
$field.name = $paramName;
#if(!$field.modifierStatic)
return self();
#end
}
```