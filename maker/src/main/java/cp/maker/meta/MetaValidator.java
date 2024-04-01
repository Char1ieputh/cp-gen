package cp.maker.meta;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import cp.maker.meta.enums.FileGenerateTypeEnum;
import cp.maker.meta.enums.FileTypeEnum;
import cp.maker.meta.enums.ModelTypeEnum;

import java.io.File;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;


public class MetaValidator  {

    public static void doValidAndFill(Meta meta)  {
        //基础信息校验和默认值
        metaRoot(meta);
        //fileConfig默认值
        flieConfig(meta);
        //modelConfig校验和默认值
        modelConfig(meta);
    }


    private static void modelConfig(Meta meta)  {
        //modelConfig校验和默认值
        Meta.ModelConfig modelConfig =  meta.getModelConfig();
        if (modelConfig == null) {
            return;
        }
        List<Meta.ModelConfig.ModelInfo> modelInfoList = modelConfig.getModels();
        if (CollUtil.isEmpty(modelInfoList)){
            return;
        }
        for (Meta.ModelConfig.ModelInfo modelInfo : modelInfoList) {
            //如果为group，则不校验
            String groupKey = modelInfo.getGroupKey();
            if (StrUtil.isNotEmpty(groupKey)){
                List<Meta.ModelConfig.ModelInfo> subModelInfoList = modelInfo.getModels();
                System.out.println(subModelInfoList);
                String allArgsStr = subModelInfoList.stream()
                        .map(subModelInfo -> String.format("\"--%s\"", subModelInfo.getFieldName()))
                        .collect(Collectors.joining(", "));
                modelInfo.setAllArgsStr(allArgsStr);
                System.out.println(allArgsStr);
                continue;
            }
            String fieldName = modelInfo.getFieldName();
            if (StrUtil.isBlank(fieldName)) {
                throw new MetaException("未填写 fieldName");
            }
            System.out.println(fieldName);

            String modelInfoType = modelInfo.getType();
            if (StrUtil.isEmpty(modelInfoType)) {
                modelInfo.setType(ModelTypeEnum.STRING.getValue());
            }
        }
    }

    private static void flieConfig(Meta meta)  {
        //fileConfig默认值
        Meta.FileConfig fileConfig = meta.getFileConfig();
        if (fileConfig == null) {
            return;
        }
        //必填
        String sourceRootPath = fileConfig.getSourceRootPath();
        if (StrUtil.isBlank(sourceRootPath)){
            throw new MetaException("未填写sourceRootPath");
        }
        //inputRootPath .source+sourceRootPath 的最后一层路径
        String inputRootPath = fileConfig.getInputRootPath();
        String defaultInputRootPath = ".source/" + FileUtil.getLastPathEle(Paths.get(sourceRootPath)).getFileName().toString();
        if (StrUtil.isEmpty(inputRootPath)) {
            fileConfig.setInputRootPath(defaultInputRootPath);
        }
        String outputRootPath = fileConfig.getOutputRootPath();
        String defaultOutputRootPath = "generated";
        if (StrUtil.isEmpty(outputRootPath)){
            fileConfig.setOutputRootPath(defaultOutputRootPath);
        }
        String fileConfigType = fileConfig.getType();
        String defaultType = FileTypeEnum.DIR.getValue();
        if (StrUtil.isEmpty(fileConfigType)){
            fileConfig.setType(defaultType);
        }
        List<Meta.FileConfig.FileInfo> fileInfoList = fileConfig.getFiles();
        if (!CollectionUtil.isNotEmpty(fileInfoList)) {
            return;
        }
        for (Meta.FileConfig.FileInfo fileInfo : fileInfoList) {
            String type = fileInfo.getType();
            if (FileTypeEnum.GROUP.getValue().equals(type)){
                continue;
            }
            //inputPath必填
            String inputPath = fileInfo.getInputPath();
            if (StrUtil.isBlank(inputPath)){
                throw new MetaException("未填写inputPath");
            }
            // outputPath: 默认等于 inputPath
            String outputPath = fileInfo.getOutputPath();
            if (StrUtil.isEmpty(outputPath)) {
                fileInfo.setOutputPath(inputPath);
            }

            if (StrUtil.isBlank(type)){
                //无文件后缀
                if (StrUtil.isBlank(FileUtil.getSuffix(inputPath))){
                    fileInfo.setType(FileTypeEnum.DIR.getValue());
                }else {
                    fileInfo.setType(FileTypeEnum.FILE.getValue());
                }
            }
            //
            String generateType = fileInfo.getGenerateType();
            if (StrUtil.isBlank(generateType)){
                if (inputPath.endsWith(".ftl")){
                    fileInfo.setGenerateType(FileGenerateTypeEnum.DYNAMIC.getValue());
                } else {
                    fileInfo.setGenerateType(FileGenerateTypeEnum.STATIC.getValue());
                }
            }
        }
    }

    private static void metaRoot(Meta meta) {
        //基础信息校验和默认值
        String name = StrUtil.blankToDefault(meta.getName(),"my-generator");
        String description = StrUtil.blankToDefault(meta.getDescription(),"ACM 示例模板生成器");
        String basePackage = StrUtil.blankToDefault(meta.getBasePackage(),"cp");
        String version = StrUtil.blankToDefault(meta.getVersion(),"1.0");
        String author = StrUtil.blankToDefault(meta.getAuthor(),"cp");
        String createTime = StrUtil.blankToDefault(meta.getCreateTime(),DateUtil.now());
        meta.setName(name);
        meta.setDescription(description);
        meta.setDescription(basePackage);
        meta.setDescription(version);
        meta.setDescription(author);
        meta.setDescription(createTime);
    }
}
