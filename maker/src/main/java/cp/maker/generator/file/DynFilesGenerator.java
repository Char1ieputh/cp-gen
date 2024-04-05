package cp.maker.generator.file;

import cn.hutool.core.io.FileUtil;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

public class DynFilesGenerator {


    public static void doGenerate(String inputPath, String outputPath, Object model) throws IOException, TemplateException {
        //new Configuration对象，参数为FreeMarker版本号
        Configuration configuration = new Configuration(Configuration.VERSION_2_3_32);
        //指定文件所在路径
        File templateDir =new File(inputPath).getParentFile();
        configuration.setDirectoryForTemplateLoading(templateDir);
        //设置模板文件使用的字符集
        configuration.setDefaultEncoding("utf-8");
        //创建模板对象，并加载
        String templateName = new File(inputPath).getName();
        Template template = configuration.getTemplate(templateName);
        //如果文件不存在，则创建
        if(!FileUtil.exist(outputPath)){
            FileUtil.touch(outputPath);
        }

        Writer out = new FileWriter(outputPath);
        template.process(model,out);
        out.close();
    }
}
