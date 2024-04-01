# acm-template-p

>2024-03-27
>
>作者cp
>
>通过命令行交互输入的方式，动态生成项目代码

##使用说明

执行项目根目录下的脚本文件：
、、、
generator <命令> <选项参数>
、、、

示例命令：
、、、
generator generate  -ng  -l  -a  -o 

##参数说明


needGit

类型：boolean

描述：是否生成.gitignore文件

默认值：true

缩写： -ng


loop

类型：boolean

描述：是否生成循环

默认值：false

缩写： -l

    <--核心模块-->

author

类型：String

描述：作者注释

默认值："cp"

缩写： -a

outputText

类型：String

描述：输出信息

默认值："sum = "

缩写： -o
