package cp.maker.template;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import cp.maker.meta.Meta;
import cp.maker.meta.enums.FileGenerateTypeEnum;
import cp.maker.meta.enums.FileTypeEnum;
import cp.maker.template.enums.FileFilterRangeEnum;
import cp.maker.template.enums.FileFilterRuleEnum;
import cp.maker.template.model.FileFilterConfig;
import cp.maker.template.model.TemplateMakerFileConfig;
import cp.maker.template.model.TemplateMakerModelConfig;

import java.io.File;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 模板制作工具
 */
public class TemplateMaker {
    public static void main(String[] args) {
        //1.项目基本信息
        Meta meta = new Meta();
        meta.setName("acm-template-generator");
        meta.setDescription("ACM示例模板生成器");

        //指定原项目路径
        String path = System.getProperty("user.dir");
        String originPath = FileUtil.getAbsolutePath(new File(path).getParentFile())+File.separator+"demo/springboot-init";
        String inputFilePath1 = "src/main/java/cp/springbootinit/common";
        String inputFilePath2 = "src/main/resources/application.yml";
        List<String> inputFilePathList = Arrays.asList(inputFilePath1,inputFilePath2);

        //输入模型信息
//        Meta.ModelConfig.ModelInfo modelInfo = new Meta.ModelConfig.ModelInfo();
//        modelInfo.setFieldName("outputText");
//        modelInfo.setType("String");
//        modelInfo.setDefaultValue("sum = ");
        //第二次
        Meta.ModelConfig.ModelInfo modelInfo = new Meta.ModelConfig.ModelInfo();
        modelInfo.setFieldName("className");
        modelInfo.setType("String");
        //替换变量
//        String searchStr = "Sum = ";
        String searchStr = "BaseResponse";

        //文件过滤配置
        TemplateMakerFileConfig.FileInfoConfig fileInfoConfig1 = new TemplateMakerFileConfig.FileInfoConfig();
        fileInfoConfig1.setPath(inputFilePath1);
        List<FileFilterConfig> fileFilterConfigList = new ArrayList<>();
        FileFilterConfig fileFilterConfig = FileFilterConfig.builder()
                .range(FileFilterRangeEnum.FILE_NAME.getValue())
                .rule(FileFilterRuleEnum.CONTAINS.getValue())
                .value("Base")
                .build();
        fileFilterConfigList.add(fileFilterConfig);
        fileInfoConfig1.setFilterConfigList(fileFilterConfigList);

        TemplateMakerFileConfig.FileInfoConfig fileInfoConfig2 = new TemplateMakerFileConfig.FileInfoConfig();
        fileInfoConfig2.setPath(inputFilePath2);

        List<TemplateMakerFileConfig.FileInfoConfig> fileInfoConfigList = Arrays.asList(fileInfoConfig1,fileInfoConfig2);
        TemplateMakerFileConfig templateMakerFileConfig = new TemplateMakerFileConfig();
        templateMakerFileConfig.setFiles(fileInfoConfigList);

        //分组配置
        TemplateMakerFileConfig.FileGroupConfig fileGroupConfig = new TemplateMakerFileConfig.FileGroupConfig();
        fileGroupConfig.setCondition("outputText2");
        fileGroupConfig.setGroupKey("test");
        fileGroupConfig.setGroupName("测试分组2");
        templateMakerFileConfig.setFileGroupConfig(fileGroupConfig);

        TemplateMakerModelConfig templateMakerModelConfig = new TemplateMakerModelConfig();

        TemplateMakerModelConfig.ModelGroupConfig modelGroupConfig = new TemplateMakerModelConfig.ModelGroupConfig();
        modelGroupConfig.setGroupKey("mysql");
        modelGroupConfig.setGroupName("数据库配置");
        templateMakerModelConfig.setModelGroupConfig(modelGroupConfig);

        TemplateMakerModelConfig.ModelInfoConfig modelInfoConfig1 = new TemplateMakerModelConfig.ModelInfoConfig();
        modelInfoConfig1.setFieldName("url");
        modelInfoConfig1.setType("String");
        modelInfoConfig1.setDefaultValue("jdbc:mysql://localhost:3306/my_db");
        modelInfoConfig1.setReplaceText("jdbc:mysql://localhost:3306/my_db");


        TemplateMakerModelConfig.ModelInfoConfig modelInfoConfig2 = new TemplateMakerModelConfig.ModelInfoConfig();
        modelInfoConfig2.setFieldName("username");
        modelInfoConfig2.setType("String");
        modelInfoConfig2.setDefaultValue("root");
        modelInfoConfig2.setReplaceText("root");

        List<TemplateMakerModelConfig.ModelInfoConfig> modelInfoConfigList = Arrays.asList(modelInfoConfig1,modelInfoConfig2);
        templateMakerModelConfig.setModels(modelInfoConfigList);

        long id = TemplateMaker.makeTemplate(meta, originPath, templateMakerFileConfig,templateMakerModelConfig,1775152751752269824L);
        System.out.println(id);
    }

    /**
     * 制作模板
     * @param newMeta
     * @param originPath
     * @param templateMakerFileConfig
     * @param templateMakerModelConfig

     * @param id
     * @return
     */
    private static long makeTemplate(Meta newMeta, String originPath, TemplateMakerFileConfig templateMakerFileConfig, TemplateMakerModelConfig templateMakerModelConfig,Long id){
        //没有id，则生成
        if (id == null){
            id = IdUtil.getSnowflakeNextId();
        }
        //复制目录
        String path = System.getProperty("user.dir");
        String tempDirPath = path + File.separator + ".temp";
        String tempPath = tempDirPath + File.separator +id;
        //是否首次制作
        if (!FileUtil.exist(tempPath)){
            FileUtil.mkdir(tempPath);
            FileUtil.copy(originPath,tempPath,true);
        }
        //一、输入信息
        //输入文件信息
        File tempFile = new File(tempPath);
        tempPath = tempFile.getAbsolutePath();
        String sourceRootPath = tempPath +File.separator + FileUtil.getLastPathEle(Paths.get(originPath)).toString();
        //win需要转义
        sourceRootPath = sourceRootPath.replaceAll("\\\\","/");

        List<TemplateMakerFileConfig.FileInfoConfig> fileInfoConfigList = templateMakerFileConfig.getFiles();
        //遍历输入文件
        List<Meta.FileConfig.FileInfo> newFileInfoList = new ArrayList<>();
        for (TemplateMakerFileConfig.FileInfoConfig fileInfoConfig : fileInfoConfigList) {
            String inputFilePath = fileInfoConfig.getPath();
            String inputFileAbsolutePath = sourceRootPath +File.separator +inputFilePath;
            //传入绝对路径
            List<File> fileList = FileFilter.doFilter(inputFileAbsolutePath, fileInfoConfig.getFilterConfigList());
            //得到过滤后的文件列表
            for (File file : fileList) {
                Meta.FileConfig.FileInfo fileInfo = makeFileTemplate(templateMakerModelConfig, sourceRootPath,file);
                newFileInfoList.add(fileInfo);
            }
        }

        //如果是文件组
        TemplateMakerFileConfig.FileGroupConfig fileGroupConfig = templateMakerFileConfig.getFileGroupConfig();
        if (fileGroupConfig!=null){
            String condition = fileGroupConfig.getCondition();
            String groupKey = fileGroupConfig.getGroupKey();
            String groupName = fileGroupConfig.getGroupName();

            Meta.FileConfig.FileInfo groupFileInfo = new Meta.FileConfig.FileInfo();
            groupFileInfo.setCondition(condition);
            groupFileInfo.setGroupKey(groupKey);
            groupFileInfo.setGroupName(groupName);
            //文件全放在一个分组内
            groupFileInfo.setFiles(newFileInfoList);

            newFileInfoList = new ArrayList<>();
            newFileInfoList.add(groupFileInfo);
        }
        //模型组
        List<TemplateMakerModelConfig.ModelInfoConfig> models = templateMakerModelConfig.getModels();
        List<Meta.ModelConfig.ModelInfo> inputModelInfoList = models.stream().map(modelInfoConfig -> {
            Meta.ModelConfig.ModelInfo modelInfo = new Meta.ModelConfig.ModelInfo();
            BeanUtil.copyProperties(modelInfoConfig,modelInfo);
            return modelInfo;
        } ).collect(Collectors.toList());

        //本次新增
        List<Meta.ModelConfig.ModelInfo> newModelInfoList = new ArrayList<>();

        //如果是模型组
        TemplateMakerModelConfig.ModelGroupConfig modelGroupConfig = templateMakerModelConfig.getModelGroupConfig();
        if (modelGroupConfig!=null){
            String condition = modelGroupConfig.getCondition();
            String groupKey = modelGroupConfig.getGroupKey();
            String groupName = modelGroupConfig.getGroupName();

            Meta.ModelConfig.ModelInfo groupModelInfo = new Meta.ModelConfig.ModelInfo();
            groupModelInfo.setCondition(condition);
            groupModelInfo.setGroupKey(groupKey);
            groupModelInfo.setGroupName(groupName);
            //文件全放在一个分组内
            groupModelInfo.setModels(inputModelInfoList);
            newModelInfoList = new ArrayList<>();
            newModelInfoList.add(groupModelInfo);
        }else {
            //不分组，全部添加
            newModelInfoList.addAll(inputModelInfoList);
        }

        //三、生成配置文件
        String metaOutputPath = sourceRootPath +File.separator+"meta.json";

        //已有meta文件，则修改
        if (FileUtil.exist(metaOutputPath)){
            newMeta = JSONUtil.toBean(FileUtil.readUtf8String(metaOutputPath),Meta.class);
            //1.追加配置参数
            List<Meta.FileConfig.FileInfo> fileInfoList = newMeta.getFileConfig().getFiles();
            fileInfoList.addAll(newFileInfoList);
            List<Meta.ModelConfig.ModelInfo> modelInfoList = newMeta.getModelConfig().getModels();
            modelInfoList.addAll(newModelInfoList);

            //配置去重
            newMeta.getFileConfig().setFiles(distinctFiles(fileInfoList));
            newMeta.getModelConfig().setModels(distinctModels(modelInfoList));

        }else {
            //1.构造配置参数对象
            Meta.FileConfig fileConfig = new Meta.FileConfig();
            newMeta.setFileConfig(fileConfig);
            fileConfig.setSourceRootPath(sourceRootPath);
            List<Meta.FileConfig.FileInfo> fileInfoList = new ArrayList<>();
            fileConfig.setFiles(fileInfoList);
            fileInfoList.addAll(newFileInfoList);

            Meta.ModelConfig modelConfig = new Meta.ModelConfig();
            newMeta.setModelConfig(modelConfig);
            List<Meta.ModelConfig.ModelInfo> modelInfoList = new ArrayList<>();
            modelConfig.setModels(modelInfoList);
            modelInfoList.addAll(newModelInfoList);

        }
        //输出元信息文件
        FileUtil.writeUtf8String(JSONUtil.toJsonPrettyStr(newMeta),metaOutputPath);
        return id;
    }

    /**
     * 制作模板文件
     * @param templateMakerModelConfig
     * @param sourceRootPath
     * @param inputFile
     * @return
     */
    private static Meta.FileConfig.FileInfo makeFileTemplate(TemplateMakerModelConfig templateMakerModelConfig, String sourceRootPath,File inputFile) {
        //目标文件绝对路径
        String fileInputAbsolutePath = inputFile.getAbsolutePath();
        fileInputAbsolutePath =fileInputAbsolutePath.replaceAll("\\\\","/");

        String fileOutputAbsolutePath = fileInputAbsolutePath +".ftl";
        //要处理的文件（一定要相对路径）
        String fileInputPath = fileInputAbsolutePath.replace(sourceRootPath+"/","");
        String fileOutputPath = fileInputPath + ".ftl";

        //二、使用字符串替换，生成模板文件
        String fileContent;
        //如果已有模板文件，则在基础上继续
        if (FileUtil.exist(fileOutputAbsolutePath)){
            fileContent = FileUtil.readUtf8String(fileOutputAbsolutePath);
        }else {
            fileContent = FileUtil.readUtf8String(fileInputAbsolutePath);
        }
        //支持多个模型：同一文件内容，遍历并进行多轮替换
        TemplateMakerModelConfig.ModelGroupConfig modelGroupConfig = templateMakerModelConfig.getModelGroupConfig();
        String newFileContent = fileContent;
        String replacement;
        for (TemplateMakerModelConfig.ModelInfoConfig modelInfoConfig : templateMakerModelConfig.getModels()) {
            //不是分组
            if (modelGroupConfig == null){
                replacement = String.format("${%s}",modelInfoConfig.getFieldName());
            }else {
                String groupKey = modelGroupConfig.getGroupKey();
                replacement = String.format("${%s.%s}",groupKey,modelInfoConfig.getFieldName());
            }
            //多次替换
            newFileContent = StrUtil.replace(newFileContent,modelInfoConfig.getReplaceText(),replacement);
        }

        //文件配置信息
        Meta.FileConfig.FileInfo fileInfo = new Meta.FileConfig.FileInfo();
        fileInfo.setInputPath(fileInputPath);
        fileInfo.setType(FileTypeEnum.FILE.getValue());

        //和原文件一致，静态生成
        if (newFileContent.equals(fileContent)){
            //输出路径=输入路径
            fileInfo.setOutputPath(fileInputPath);
            fileInfo.setGenerateType(FileGenerateTypeEnum.STATIC.getValue());
        }else {
            //输出模板文件
            fileInfo.setGenerateType(FileGenerateTypeEnum.DYNAMIC.getValue());
            FileUtil.writeUtf8String(newFileContent,fileOutputAbsolutePath);
        }
        return fileInfo;
    }

    /**
     * 文件去重
     * @param fileInfoList
     * @return
     */
    private static List<Meta.FileConfig.FileInfo> distinctFiles(List<Meta.FileConfig.FileInfo> fileInfoList){

        //1.将所有文件分为有分组和无分组
        Map<String,List<Meta.FileConfig.FileInfo>> groupKeyFileInfoListMap = fileInfoList
                .stream()
                .filter(fileInfo -> StrUtil.isNotBlank(fileInfo.getGroupKey()))
                .collect(
                        Collectors.groupingBy(Meta.FileConfig.FileInfo::getGroupKey)
                );
        //2.有分组：相同分组合并，不同保留
        Map<String,Meta.FileConfig.FileInfo> groupKeyMergedFileInfoMap = new HashMap<>();
        for (Map.Entry<String, List<Meta.FileConfig.FileInfo>> entry : groupKeyFileInfoListMap.entrySet()) {
            List<Meta.FileConfig.FileInfo> tempFileInfoList = entry.getValue();
            List<Meta.FileConfig.FileInfo> newFileInfoList = new ArrayList<>(tempFileInfoList.stream()
                    .flatMap(fileInfo -> fileInfo.getFiles().stream())
                    .collect(
                            Collectors.toMap(Meta.FileConfig.FileInfo::getInputPath, o -> o, (exist, replacement) -> replacement)
                    ).values());
            //使用新的group配置
            Meta.FileConfig.FileInfo newFileInfo = CollUtil.getLast(tempFileInfoList);
            newFileInfo.setFiles(newFileInfoList);
            String groupKey = entry.getKey();
            groupKeyMergedFileInfoMap.put(groupKey,newFileInfo);
        }

        //3.创建新的文件配置列表，将合并的分组保留
        List<Meta.FileConfig.FileInfo> resultList = new ArrayList<>(groupKeyMergedFileInfoMap.values());
        //4.将无分组添加
        List<Meta.FileConfig.FileInfo> noGroupFileInfoList = fileInfoList.stream()
                .filter(fileInfo -> StrUtil.isBlank(fileInfo.getGroupKey()))
                .collect(Collectors.toList());
        resultList.addAll(new ArrayList<>(noGroupFileInfoList.stream()
                .collect(
                        Collectors.toMap(Meta.FileConfig.FileInfo::getInputPath,o -> o, (exist, replacement) -> replacement)
                ).values()));
        return resultList;
    }

    /**
     * 模型去重
     * @param modelInfoList
     * @return
     */
    private static List<Meta.ModelConfig.ModelInfo> distinctModels(List<Meta.ModelConfig.ModelInfo> modelInfoList){
        //1.将所有文件分为有分组和无分组
        Map<String,List<Meta.ModelConfig.ModelInfo>> groupKeyModelInfoListMap = modelInfoList
                .stream()
                .filter(modelInfo -> StrUtil.isNotBlank(modelInfo.getGroupKey()))
                .collect(
                        Collectors.groupingBy(Meta.ModelConfig.ModelInfo::getGroupKey)
                );
        //2.有分组：相同分组合并，不同保留
        Map<String,Meta.ModelConfig.ModelInfo> groupKeyMergedModelInfoMap = new HashMap<>();
        for (Map.Entry<String, List<Meta.ModelConfig.ModelInfo>> entry : groupKeyModelInfoListMap.entrySet()) {
            List<Meta.ModelConfig.ModelInfo> tempModelInfoList = entry.getValue();
            List<Meta.ModelConfig.ModelInfo> newModelInfoList = new ArrayList<>(tempModelInfoList.stream()
                    .flatMap(modelInfo -> modelInfo.getModels().stream())
                    .collect(
                            Collectors.toMap(Meta.ModelConfig.ModelInfo::getFieldName, o -> o, (exist, replacement) -> replacement)
                    ).values());
            //使用新的group配置
            Meta.ModelConfig.ModelInfo newModelInfo = CollUtil.getLast(tempModelInfoList);
            newModelInfo.setModels(newModelInfoList);
            String groupKey = entry.getKey();
            groupKeyMergedModelInfoMap.put(groupKey,newModelInfo);
        }

        //3.创建新的文件配置列表，将合并的分组保留
        List<Meta.ModelConfig.ModelInfo> resultList = new ArrayList<>(groupKeyMergedModelInfoMap.values());
        //4.将无分组添加
        List<Meta.ModelConfig.ModelInfo> noGroupModelInfoList = modelInfoList.stream()
                .filter(modelInfo -> StrUtil.isBlank(modelInfo.getGroupKey()))
                .collect(Collectors.toList());
        resultList.addAll(new ArrayList<>(noGroupModelInfoList.stream()
                .collect(
                        Collectors.toMap(Meta.ModelConfig.ModelInfo::getFieldName,o -> o, (exist, replacement) -> replacement)
                ).values()));
        return resultList;
    }
}
