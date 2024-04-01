package cp.cli.command;

import cn.hutool.core.bean.BeanUtil;
import cp.generator.MainGenerator;
import cp.model.DataModel;
import lombok.Data;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

import java.util.concurrent.Callable;


@Command(name = "generator",description = "生成代码",mixinStandardHelpOptions = true)
@Data
public class GeneratorCommand implements Callable<Integer> {
    @Option(names = {"-ng", "--needGit"},arity = "0..1",description = "是否生成.gitignore文件" ,interactive = true,echo = true)
    private boolean  needGit =true;
    @Option(names = {"-l", "--loop"},arity = "0..1",description = "是否生成循环" ,interactive = true,echo = true)
    private boolean  loop =false;
    /**
    * 核心模板
    */
    static DataModel.MainTemplate mainTemplate = new DataModel.MainTemplate();

    @Command(name = "核心模板",description = "用于生成核心模板文件")
    @Data
    public static class MainTemplateCommand implements Runnable {
            @Option(names = {"-a", "--author"},arity = "0..1",description = "作者注释" ,interactive = true,echo = true)
            private String  author ="cp";
            @Option(names = {"-o", "--outputText"},arity = "0..1",description = "输出信息" ,interactive = true,echo = true)
            private String  outputText ="sum = ";

        @Override
        public void run() {
        mainTemplate.author = author;
        mainTemplate.outputText = outputText;
        }
    }
    public Integer call() throws Exception {
                    if(loop){
            CommandLine commandLine = new CommandLine(MainTemplateCommand.class);
            commandLine.execute("--author", "--outputText");
                }
        DataModel dataModel = new DataModel();
        BeanUtil.copyProperties(this, dataModel);
        dataModel.mainTemplate = mainTemplate;
        MainGenerator.doGenerator(dataModel);
        return 0;
    }
}