package cp.generator;

import cp.model.MainTemplateConfig;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

public class DynamicGenerator {
    public static void main(String[] args) throws IOException, TemplateException {
        String Path = System.getProperty("user.dir");
        String inputPath = Path + File.separator +"src/main/resources/templates/MainTemplate.java.ftl";
        String outputPath = Path + File.separator + "MainTemplate.java";
        MainTemplateConfig mainTemplateConfig = new MainTemplateConfig();
        mainTemplateConfig.setAuthor("cp");
        mainTemplateConfig.setLoop(false);
        mainTemplateConfig.setOutputText("求和结果：");
        doGenerate(inputPath,outputPath,mainTemplateConfig);
    }

    public static void doGenerate(String inputPath, String outputPath, Object model) throws IOException, TemplateException {
        Configuration configuration = new Configuration(Configuration.VERSION_2_3_32);
        File templateDir =new File(inputPath).getParentFile();
        configuration.setDirectoryForTemplateLoading(templateDir);
        configuration.setDefaultEncoding("utf-8");
        String templateName = new File(inputPath).getName();
        Template template = configuration.getTemplate(templateName);

        MainTemplateConfig mainTemplateConfig = new MainTemplateConfig();
        mainTemplateConfig.setAuthor("cp");
        mainTemplateConfig.setLoop(false);
        mainTemplateConfig.setOutputText("求和结果");

        Writer out = new FileWriter(outputPath);
        template.process(model,out);
        out.close();
    }
}
