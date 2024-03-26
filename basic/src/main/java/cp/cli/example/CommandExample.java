package cp.cli.example;

import picocli.CommandLine;
import picocli.CommandLine.Command;

@Command(name = "main",mixinStandardHelpOptions = true)
public class CommandExample implements Runnable{

    @Override
    public void run() {
        System.out.println("主命令执行中");
    }
    @Command(name = "add",description = "增加",mixinStandardHelpOptions = true)
    static class AddCommond implements Runnable{
        @Override
        public void run() {
            System.out.println("增加命令执行中");
        }
    }
    @Command(name = "delete",description = "删除",mixinStandardHelpOptions = true)
    static class DelCommond implements Runnable{
        @Override
        public void run() {
            System.out.println("删除命令执行中");
        }
    }
    @Command(name = "query",description = "查找",mixinStandardHelpOptions = true)
    static class QueryCommond implements Runnable{
        @Override
        public void run() {
            System.out.println("查找命令执行中");
        }
    }

    public static void main(String[] args) {
        //主命令执行
        //String[] myArgs = new String[]{};
        //主命令帮助手册
        String[] myArgs = new String[]{"--help"};
        //增加命令执行
        //String[] myArgs = new String[]{"add"};
        //增加命令帮助手册
        //String[] myArgs = new String[]{"add","--help"};
        //不存在命令报错
        //String[] myArgs = new String[]{"update"};
        int exitCode =new CommandLine(new CommandExample())
                .addSubcommand(new AddCommond())
                .addSubcommand(new DelCommond())
                .addSubcommand(new QueryCommond())
                .execute(myArgs);
        System.exit(exitCode);
    }
}
