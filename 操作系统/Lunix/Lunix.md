# Lunix

+ 搜索匹配的文件名

```shell
find / -name $name
```

+ 权限不足

```shell
chmod 777 $file
chmod -Rf 777 $floder
```

+ 复制文件

```shell
cp -r $source $direct
```



## vim

开始编辑

```shell
vim filename.suffix
i # 进入编辑
```

退出编辑

```shell
Esc
:wq 保存并退出
:q! 不保存直接退出
```

搜索命令

```shell
/ # 进入后直接搜索; 编辑模式需要Esc退出编辑再使用 /
/关键字 # 向下查找
?关键字 # 向上查找
/关键字\> # 匹配末尾
/\<关键字 # 匹配开头
/\<关键字\> # 匹配全部
:set ignorecase # 不区分大小写
:set noignorecase # 恢复大小写敏感
:set hlsearch # 高亮搜索
:set incsearch # 递进搜索; 每输入一个字符, 搜索一次
n # 移动当下一个匹配处
shift + n # 移动到上一个匹配处
* # 光标移动到关键字任意位置按键; 搜索选定的关键字
```

```shell
gg # 跳到第一行
10gg # 跳到最后一行
shift + g # 跳到最后一行
```