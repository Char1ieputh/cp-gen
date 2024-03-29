package cp;

import cp.cli.CommandExecutor;

public class Main {
public static void main(String[] args) {

CommandExecutor commandExecutor =new CommandExecutor();
    args = new String[]{"generator","--needGit=true"};
commandExecutor.doExecute(args);

}
}
