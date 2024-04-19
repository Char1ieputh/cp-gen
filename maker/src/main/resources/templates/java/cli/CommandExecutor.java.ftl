package ${basePackage}.cli;

import ${basePackage}.cli.command.ConfigCommand;
import ${basePackage}.cli.command.GeneratorCommand;
import ${basePackage}.cli.command.ListCommand;
import ${basePackage}.cli.command.JsonGenerateCommand;
import picocli.CommandLine;
import picocli.CommandLine.Command;

@Command(name = "${name}",mixinStandardHelpOptions = true)
public class CommandExecutor implements Runnable{
    private final CommandLine commandLine;
{
commandLine = new CommandLine(this)
    .addSubcommand(new GeneratorCommand())
    .addSubcommand(new ListCommand())
    .addSubcommand(new ConfigCommand())
    .addSubcommand(new JsonGenerateCommand());
}

@Override
public void run() {
//不输入子命令时提示
System.out.println("请输入具体命令，或输入--help获取具体命令");
}

public Integer doExecute(String[] args){
return commandLine.execute(args);
}
}
