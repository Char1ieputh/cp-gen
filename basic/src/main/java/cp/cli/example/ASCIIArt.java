package cp.cli.example;

import picocli.CommandLine.Command;
import picocli.CommandLine;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

@Command(name = "ASCIIArt",version = "ASCIIArt 1.0",mixinStandardHelpOptions = true)
public class ASCIIArt implements Runnable{
    @Option(names = {"-s","--font-size" },description = "Font size",required = true)
    int fontSize = 19;

    @Parameters(paramLabel = "<word>",defaultValue = "Hello picocli",
                description = "words translate")
    private String[] words = {"hello,","picocli"};

    @Override
    public void run() {
        System.out.println(fontSize);
        System.out.println(String.join(",",words));
    }

    public static void main(String[] args) {
        int exitCode = new CommandLine(new ASCIIArt()).execute(args);
        System.exit(exitCode);
    }
}
