package cp.generator;

import freemarker.template.TemplateException;

import java.io.File;
import java.io.IOException;

public class MainGenerator {

    public static void doGenerator(Object model) throws TemplateException, IOException {
        String inputRootPath = "D:/work/study/cp-gennerator/demo/acm-template-p";
        String outputRootPath = "generated";

        String inputPath;
        String outputPath;
            inputPath = new File(inputRootPath,"src/com/cp/acm/MainTemplate.java.ftl").getAbsolutePath();
            outputPath = new File(outputRootPath,"src/com/cp/acm/MainTemplate.java").getAbsolutePath();
                DynGenerator.doGenerate(inputPath,outputPath,model);

            inputPath = new File(inputRootPath,".gitignore").getAbsolutePath();
            outputPath = new File(outputRootPath,".gitignore").getAbsolutePath();
                StaGenerator.copyFilesHutool(inputPath,outputPath);

            inputPath = new File(inputRootPath,"README.md").getAbsolutePath();
            outputPath = new File(outputRootPath,"README.md").getAbsolutePath();
                StaGenerator.copyFilesHutool(inputPath,outputPath);


    }
}