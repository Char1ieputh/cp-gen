package cp.maker;

//import cp.maker.cli.CommandExecutor;

import cp.maker.generator.MainGenerator;
import cp.maker.generator.main.GenerateTemplate;
import cp.maker.generator.main.ZipGenerator;
import freemarker.template.TemplateException;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws TemplateException, IOException, InterruptedException {
        //MainGenerator mainGenerator = new MainGenerator();
        GenerateTemplate generateTemplate = new ZipGenerator();
        generateTemplate.doGenerate();
    }
}
