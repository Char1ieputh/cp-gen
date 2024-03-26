package cp.cli.command;

import cn.hutool.core.io.FileUtil;
import picocli.CommandLine.Command;

import java.io.File;
import java.util.List;

@Command(name = "list",description = "查看文件列表",mixinStandardHelpOptions = true)
public class ListCommand implements Runnable{

    @Override
    public void run() {
        String Path = System.getProperty("user.dir");
        //整个项目的根路径
        File parentFile = new File(Path).getParentFile();
        //输入路径
        String inputPath = new File(parentFile,"demo/acm-template").getAbsolutePath();
        List<File> files = FileUtil.loopFiles(inputPath);
        for (File file : files) {
            System.out.println(file);
        }
    }
}
