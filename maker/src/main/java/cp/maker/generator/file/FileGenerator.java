package cp.maker.generator.file;


import cp.maker.generator.file.DynFilesGenerator;
import cp.maker.model.DataModel;
import freemarker.template.TemplateException;

import java.io.File;
import java.io.IOException;

public class FileGenerator {
    public static void main(String[] args) throws TemplateException, IOException {
        DataModel dataModel =new DataModel();
        dataModel.setAuthor("cp/maker");
        dataModel.setLoop(false);
        dataModel.setOutputText("求和结果：");
        doGenerator(dataModel);
    }
    public static void doGenerator(Object model) throws TemplateException, IOException {
        String inputRootPath = "D:\\work\\study\\cp-gennerator\\demo\\acm-template-p";
        String outputRootPath = "D:\\work\\study\\cp-gennerator";

        String inputPath;
        String outputPath;
        //生成动态文件
        inputPath = new File(inputRootPath,"src/com/cp/arm/MainTemplate.java.flt").getAbsolutePath();
        outputPath = new File(outputRootPath,"src/com/cp/arm/MainTemplate.java").getAbsolutePath();
        DynFilesGenerator.doGenerate(inputPath,outputPath,model);
        //生成静态文件
        inputPath = new File(inputRootPath,".gitignore").getAbsolutePath();
        outputPath = new File(outputRootPath,".gitignore").getAbsolutePath();
        StaFilesGenerator.copyFilesHutool(inputPath,outputPath);

        //生成静态文件
        inputPath = new File(inputRootPath,"README.md").getAbsolutePath();
        outputPath = new File(outputRootPath,"README.md").getAbsolutePath();
        StaFilesGenerator.copyFilesHutool(inputPath,outputPath);
    }
}
