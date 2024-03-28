package cp.cli;

import cp.cli.command.ConfigCommand;
import cp.cli.command.GeneratorCommand;
import cp.cli.command.ListCommand;
import picocli.CommandLine;
import picocli.CommandLine.Command;

@Command(name = "acm-template-p",mixinStandardHelpOptions = true)
public class CommandExecutor implements Runnable{
    private final CommandLine commandLine;
{
commandLine = new CommandLine(this)
.addSubcommand(new GeneratorCommand())
.addSubcommand(new ListCommand())
.addSubcommand(new ConfigCommand());
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
