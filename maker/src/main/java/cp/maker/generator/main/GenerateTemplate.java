package cp.maker.generator.main;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.resource.ClassPathResource;
import cn.hutool.core.util.StrUtil;
import cp.maker.generator.JarGenerator;
import cp.maker.generator.ScriptGenerator;
import cp.maker.generator.file.DynFilesGenerator;
import cp.maker.meta.Meta;
import cp.maker.meta.MetaManager;
import freemarker.template.TemplateException;

import java.io.File;
import java.io.IOException;

public class GenerateTemplate {
    public void  doGenerate() throws TemplateException, IOException, InterruptedException {

        Meta metaObject = MetaManager.getMetaObject();
        System.out.println(metaObject);

        //输出根路径
        String path = System.getProperty("user.dir");//D:\work\study\cp-gennerator\maker;
        String outputPath = path + File.separator +"generated"+File.separator + metaObject.getName();//D:\work\study\cp-gennerator\maker\generated\acm-template-p;
        if (!FileUtil.exist(outputPath)){
            FileUtil.mkdir(outputPath);
        }
        //1.复制原始文件
        String sourceCopyPath = copySource(metaObject, outputPath);

        //2.代码生成
        generateCode(metaObject, outputPath);

        //3.构建jar包
        String jarPath = buildJar(outputPath, metaObject);

        //4.封装脚本
        String shellOutputPath  =buildScript(outputPath, jarPath);

        //5.生成精简版程序
        buildDist(outputPath,sourceCopyPath,jarPath,shellOutputPath);
    }

    /**
     * 封装脚本
     * @param outputPath
     * @param sourceCopyPath
     * @param jarPath
     * @param shellOutputPath
     */
    protected void buildDist(String outputPath,String sourceCopyPath,String jarPath,String shellOutputPath) {
        String distOutputPath = outputPath + "-dist";
        //-jar包
        String targetAbsolutePath = distOutputPath +File.separator +"target";
        FileUtil.mkdir(targetAbsolutePath);
        String jarAbsolutePath = outputPath + File.separator + jarPath;
        FileUtil.copy(jarAbsolutePath,targetAbsolutePath,true);
        //拷贝脚本文件
        FileUtil.copy(shellOutputPath,distOutputPath,true);
        FileUtil.copy(shellOutputPath +".bat",distOutputPath,true);
        //拷贝源模板文件
        FileUtil.copy(sourceCopyPath,distOutputPath,true);
    }

    /**
     * 封装脚本
     * @param outputPath
     * @param jarPath
     * @return
     */
    protected String buildScript(String outputPath, String jarPath) {
        String shellOutputPath = outputPath + File.separator +"generator";
        ScriptGenerator.doGenerator(shellOutputPath,jarPath);
        return shellOutputPath;
    }

    /**
     * 构建jar包
     * @param outputPath
     * @throws IOException
     * @throws InterruptedException
     */
    protected String buildJar(String outputPath,Meta metaObject) throws IOException, InterruptedException {
        JarGenerator.doGenerator(outputPath);
        String jarName = String.format("%s-%s-jar-with-dependencies.jar", metaObject.getName(), metaObject.getVersion());
        String jarPath = "target/" + jarName;
        return jarPath;
    }

    /**
     * 代码生成
     * @param metaObject
     * @param outputPath
     * @throws IOException
     * @throws TemplateException
     */
    protected void generateCode(Meta metaObject, String outputPath) throws IOException, TemplateException {
        //读取resourc目录
        ClassPathResource classPathResource = new ClassPathResource("");
        String inputResourcePath = classPathResource.getAbsolutePath();//D:/work/study/cp-gennerator/maker/target/classes/

        //java包基础路径
        String outputBasePackage = metaObject.getBasePackage();//cp
        String outputBasePackagePath = StrUtil.join("/",StrUtil.split(outputBasePackage,"."));
        String outputBaseJavaPackagePath = outputPath +File.separator + "src/main/java/" + outputBasePackagePath; //+ outputBasePackagePath;//D:\work\study\cp-gennerator\maker\generated\src/main/java/cp

        String inputFilePath;
        String outputFilePath;

        //DataModel
        inputFilePath = inputResourcePath + File.separator + "templates/java/model/DataModel.java.ftl";
        outputFilePath = outputBaseJavaPackagePath +"/model/DataModel.java";
        DynFilesGenerator.doGenerate(inputFilePath,outputFilePath, metaObject);
        //MainGenerator
        inputFilePath = inputResourcePath + File.separator + "templates/java/generator/MainGenerator.java.ftl";
        outputFilePath = outputBaseJavaPackagePath +"/generator/MainGenerator.java";
        DynFilesGenerator.doGenerate(inputFilePath,outputFilePath, metaObject);
        //ConfigCommand
        inputFilePath = inputResourcePath + File.separator + "templates/java/cli/command/ConfigCommand.java.ftl";
        outputFilePath = outputBaseJavaPackagePath +"cli/command/ConfigCommand.java";
        DynFilesGenerator.doGenerate(inputFilePath,outputFilePath, metaObject);
        //ListCommand
        inputFilePath = inputResourcePath + File.separator + "templates/java/cli/command/ListCommand.java.ftl";
        outputFilePath = outputBaseJavaPackagePath +"cli/command/ListCommand.java";
        DynFilesGenerator.doGenerate(inputFilePath,outputFilePath, metaObject);
        //CommandExecutor
        inputFilePath = inputResourcePath + File.separator + "templates/java/cli/CommandExecutor.java.ftl";
        outputFilePath = outputBaseJavaPackagePath +"cli/CommandExecutor.java";
        DynFilesGenerator.doGenerate(inputFilePath,outputFilePath, metaObject);
        //Main
        inputFilePath = inputResourcePath + File.separator + "templates/java/Main.java.ftl";
        outputFilePath = outputBaseJavaPackagePath +"/Main.java";
        DynFilesGenerator.doGenerate(inputFilePath,outputFilePath, metaObject);
        //gene.Dyn
        inputFilePath = inputResourcePath + File.separator + "templates/java/generator/DynGenerator.java.ftl";
        outputFilePath = outputBaseJavaPackagePath +"/generator/DynGenerator.java";
        DynFilesGenerator.doGenerate(inputFilePath,outputFilePath, metaObject);
        //gene.Sta
        inputFilePath = inputResourcePath + File.separator + "templates/java/generator/StaGenerator.java.ftl";
        outputFilePath = outputBaseJavaPackagePath +"/generator/StaGenerator.java";
        DynFilesGenerator.doGenerate(inputFilePath,outputFilePath, metaObject);
        //GeneratorCommand
        inputFilePath = inputResourcePath + File.separator + "templates/java/cli/command/GeneratorCommand.java.ftl";
        outputFilePath = outputBaseJavaPackagePath +"cli/command/GeneratorCommand.java";
        DynFilesGenerator.doGenerate(inputFilePath,outputFilePath, metaObject);
        //pom.xml
        inputFilePath = inputResourcePath + File.separator + "templates/pom.xml.ftl";
        outputFilePath = outputPath +File.separator +"pom.xml";
        DynFilesGenerator.doGenerate(inputFilePath,outputFilePath, metaObject);

    }

    /**
     * 复制原始文件
     * @param metaObject
     * @param outputPath
     * @return
     */
    protected String copySource(Meta metaObject, String outputPath) {
        String sourceRootPath = metaObject.getFileConfig().getSourceRootPath();
        String sourceCopyPath = outputPath + File.separator + ".source";
        FileUtil.copy(sourceRootPath,sourceCopyPath,false);
        return sourceCopyPath;
    }
}
