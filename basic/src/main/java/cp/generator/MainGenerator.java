package cp.generator;


import cp.model.MainTemplateConfig;
import freemarker.template.TemplateException;

import java.io.File;
import java.io.IOException;

public class MainGenerator {



    public static void main(String[] args) throws TemplateException, IOException {
        MainTemplateConfig mainTemplateConfig =new MainTemplateConfig();
        mainTemplateConfig.setAuthor("cp");
        mainTemplateConfig.setLoop(false);
        mainTemplateConfig.setOutputText("求和结果：");
        doGenerator(mainTemplateConfig);
    }
    public static void doGenerator(Object model) throws TemplateException, IOException {
        String inputRootPath = "/cp-generator/demo/acm-template-p";
        String outputRootPath = "generated";

        String inputPath;
        String outputPath;
        inputPath = new File(inputRootPath,"src/com/cp/acm/MainTemplate.java.ftl").getAbsolutePath();
        outputPath = new File(outputRootPath,"src/com/cp/acm/MainTemplate.java").getAbsolutePath();
        DynamicGenerator.doGenerate(inputPath,outputPath,model);

        inputPath = new File(inputRootPath,".gitignore").getAbsolutePath();
        outputPath = new File(outputRootPath,".gitignore").getAbsolutePath();
        StaticGenerator.copyFilesHutool(inputPath,outputPath);

        inputPath = new File(inputRootPath,"README.md").getAbsolutePath();
        outputPath = new File(outputRootPath,"README.md").getAbsolutePath();
        StaticGenerator.copyFilesHutool(inputPath,outputPath);


        String Path =System.getProperty("user.dir");
        //整个项目的根目录
        File file = new File(Path).getParentFile();
        //输入路径
        //String inputPath = new File(file,"demo/acm-template").getAbsolutePath();
        //String outputPath = Path;
        //生成静态文件
        //StaticGenerator.copyFilesRec(inputPath,outputPath);
        //生成动态文件
        //String inputDynFilePath = Path + File.separator +"src/main/resources/templates/MainTemplate.java.ftl";
        //String outputDynFilePath = outputPath + File.separator +"acm-template/src/com/cp/arm/MainTemplate.java";
        //DynamicGenerator.doGenerate(inputDynFilePath,outputDynFilePath,model);
    }

}
