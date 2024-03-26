package cp.cli.example;


import picocli.CommandLine;
import picocli.CommandLine.Option;

import java.util.concurrent.Callable;

public class interactive implements Callable<Integer> {
    @Option(names = {"-u","--user"},description = "Username")
    String user;

    @Option(names = {"-p","--password"},arity = "0..1",description = "Password",interactive = true)
    String password;

    @Option(names = {"-cp","--checkPassword"},description = "Check Password",interactive = true)
    String checkPassword;


    public Integer call() throws Exception {
        System.out.println(password);
        System.out.println(checkPassword);
        return 0;
    }

    public static void main(String[] args) {
        new CommandLine(new interactive()).execute("-u","user","-p","cp","-cp");
    }
}
