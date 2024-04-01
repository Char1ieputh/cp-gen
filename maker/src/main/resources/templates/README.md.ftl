# ${name}

>${description}
>
>作者${author}
>
>通过命令行交互输入的方式，动态生成项目代码

##使用说明

执行项目根目录下的脚本文件：
、、、
generator <命令> <选项参数>
、、、

示例命令：
、、、
generator generate <#list modelConfig.models as model><#if model.groupKey??><#list model.models as subModel> -${subModel.abbr} </#list></#if><#if model.abbr??> -${model.abbr} </#if></#list>

##参数说明
<#list modelConfig.models as modelInfo>

<#if modelInfo.groupKey??>
    <--核心模块-->
    <#list modelInfo.models as subModelInfo>

${subModelInfo.fieldName}

类型：${subModelInfo.type}

描述：${subModelInfo.description}

默认值：${subModelInfo.defaultValue?c}

缩写： -${subModelInfo.abbr}
</#list>
<#else>
    <#if modelInfo.fieldName??>

${modelInfo.fieldName}

类型：${modelInfo.type}

描述：${modelInfo.description}

默认值：${modelInfo.defaultValue?c}

缩写： -${modelInfo.abbr}
</#if>
</#if>
</#list>