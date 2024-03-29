package cp.maker.generator;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.resource.ClassPathResource;
import cn.hutool.core.util.StrUtil;
import cp.maker.generator.file.DynFilesGenerator;
import cp.maker.generator.main.GenerateTemplate;
import cp.maker.meta.Meta;
import cp.maker.meta.MetaExecption;
import cp.maker.meta.MetaManager;
import freemarker.template.TemplateException;

import java.io.File;
import java.io.IOException;

public class MainGenerator extends GenerateTemplate {
    @Override
    protected void buildDist(String outputPath, String sourceCopyPath, String jarPath, String shellOutputPath) {
        System.out.println("不要生成精简版");
    }
}

//        Meta metaObject = MetaManager.getMetaObject();
//        System.out.println(metaObject);
//
//        //输出根路径
//        String path = System.getProperty("user.dir");//D:\work\study\cp-gennerator\maker;
//        String outputPath = path + File.separator +"generated"+File.separator + metaObject.getName();//D:\work\study\cp-gennerator\maker\generated\acm-template-p;
//        if (!FileUtil.exist(outputPath)){
//            FileUtil.mkdir(outputPath);
//        }
//
//        //从原始模板文件复制到生成的代码包中
//        String sourceRootPath = metaObject.getFileConfig().getSourceRootPath();
//        String sourceCopyPath = outputPath + File.separator + ".source";
//        FileUtil.copy(sourceRootPath,sourceCopyPath,false);
//
//        //读取resourc目录
//        ClassPathResource classPathResource = new ClassPathResource("");
//        String inputResourcePath = classPathResource.getAbsolutePath();//D:/work/study/cp-gennerator/maker/target/classes/
//
//        //java包基础路径
//        String outputBasePackage = metaObject.getBasePackage();//cp
//        String outputBasePackagePath = StrUtil.join("/",StrUtil.split(outputBasePackage,"."));
//        String outputBaseJavaPackagePath = outputPath +File.separator + "src/main/java/" + outputBasePackage; //+ outputBasePackagePath;//D:\work\study\cp-gennerator\maker\generated\src/main/java/cp
//
//        String inputFilePath;
//        String outputFilePath;
//
//        inputFilePath = inputResourcePath + File.separator + "templates/java/model/DataModel.java.ftl";
//        outputFilePath = outputBaseJavaPackagePath +"/model/DataModel.java";
//        DynFilesGenerator.doGenerate(inputFilePath,outputFilePath,metaObject);
//
//        //ConfigCommand
//        inputFilePath = inputResourcePath + File.separator + "templates/java/cli/command/ConfigCommand.java.ftl";
//        outputFilePath = outputBaseJavaPackagePath +"cli/command/ConfigCommand.java";
//        DynFilesGenerator.doGenerate(inputFilePath,outputFilePath,metaObject);
//        //GeneratorCommand
//        inputFilePath = inputResourcePath + File.separator + "templates/java/cli/command/GeneratorCommand.java.ftl";
//        outputFilePath = outputBaseJavaPackagePath +"cli/command/GeneratorCommand.java";
//        DynFilesGenerator.doGenerate(inputFilePath,outputFilePath,metaObject);
//        //ListCommand
//        inputFilePath = inputResourcePath + File.separator + "templates/java/cli/command/ListCommand.java.ftl";
//        outputFilePath = outputBaseJavaPackagePath +"cli/command/ListCommand.java";
//        DynFilesGenerator.doGenerate(inputFilePath,outputFilePath,metaObject);
//        //CommandExecutor
//        inputFilePath = inputResourcePath + File.separator + "templates/java/cli/CommandExecutor.java.ftl";
//        outputFilePath = outputBaseJavaPackagePath +"cli/CommandExecutor.java";
//        DynFilesGenerator.doGenerate(inputFilePath,outputFilePath,metaObject);
//        //Main
//        inputFilePath = inputResourcePath + File.separator + "templates/java/Main.java.ftl";
//        outputFilePath = outputBaseJavaPackagePath +"/Main.java";
//        DynFilesGenerator.doGenerate(inputFilePath,outputFilePath,metaObject);
//        //gene.Dyn
//        inputFilePath = inputResourcePath + File.separator + "templates/java/generator/DynGenerator.java.ftl";
//        outputFilePath = outputBaseJavaPackagePath +"/generator/DynGenerator.java";
//        DynFilesGenerator.doGenerate(inputFilePath,outputFilePath,metaObject);
//        //gene.Sta
//        inputFilePath = inputResourcePath + File.separator + "templates/java/generator/StaGenerator.java.ftl";
//        outputFilePath = outputBaseJavaPackagePath +"/generator/StaGenerator.java";
//        DynFilesGenerator.doGenerate(inputFilePath,outputFilePath,metaObject);
//        //gene.Main
//        inputFilePath = inputResourcePath + File.separator + "templates/java/generator/MainGenerator.java.ftl";
//        outputFilePath = outputBaseJavaPackagePath +"/generator/MainGenerator.java";
//        DynFilesGenerator.doGenerate(inputFilePath,outputFilePath,metaObject);
//        //pom.xml
//        inputFilePath = inputResourcePath + File.separator + "templates/pom.xml.ftl";
//        outputFilePath = outputPath +File.separator +"pom.xml";
//        DynFilesGenerator.doGenerate(inputFilePath,outputFilePath,metaObject);
//        //README.md
//        inputFilePath = inputResourcePath + File.separator + "templates/README.md.ftl";
//        outputFilePath = outputPath +File.separator +"README.md";
//        DynFilesGenerator.doGenerate(inputFilePath,outputFilePath,metaObject);
//        //jar包
//        JarGenerator.doGenerator(outputPath);
//
//        //封装脚本
//        String shellOutputPath = outputPath + File.separator +"generator";
//        String jarName = String.format("%s-%s-jar-with-dependencies.jar",metaObject.getName(),metaObject.getVersion());
//        String jarPath = "target/" + jarName;
//        ScriptGenerator.doGenerator(shellOutputPath,jarPath);
//
//        //生成精简版程序
//        String  distOutputPath = outputPath + "-dist";
//        //拷贝jar包
//        String targetAbsolutePath = distOutputPath +File.separator +"target";
//        FileUtil.mkdir(targetAbsolutePath);
//        String jarAbsolutePath = outputPath + File.separator + jarPath;
//        FileUtil.copy(jarAbsolutePath,targetAbsolutePath,true);
//        //拷贝脚本文件
//        FileUtil.copy(shellOutputPath,distOutputPath,true);
//        FileUtil.copy(shellOutputPath+".bat",distOutputPath,true);
//        //拷贝源模板文件
//        FileUtil.copy(sourceCopyPath,distOutputPath,true);}

