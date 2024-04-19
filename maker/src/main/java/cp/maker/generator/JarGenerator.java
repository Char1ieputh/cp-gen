package cp.maker.generator;

import java.io.*;

public class JarGenerator {

    public static void doGenerator(String projectDir) throws IOException, InterruptedException {
        //核心逻辑，调用Process
        String winCommand = "mvn.cmd clean package -DskipTests=true";
        String otherCommand = "mvn clean package -DskipTests=true";
        String mavenCommand = winCommand;

        ProcessBuilder processBuilder = new ProcessBuilder(mavenCommand.split(" "));
        processBuilder.directory(new File(projectDir));

        Process process = processBuilder.start();
        //读取命令输出
        InputStream inputStream = process.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        while ((line = reader.readLine()) != null){
            System.out.println(line);
        }
        int exitCode = process.waitFor();
        System.out.println("退出码"+exitCode);
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        doGenerator("D:\\work\\study\\cp-gennerator\\maker\\generator\\acm-template-p");
    }
}
