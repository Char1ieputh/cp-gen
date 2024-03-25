package cp.generator;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.ArrayUtil;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class StaticGenerator {

    public static void main(String[] args){
        //获取根路径
        String path= System.getProperty("user.dir");
        File file = new File(path).getParentFile();
        //输入路径
        String inputPath = new File(file,"demo/acm-template").getAbsolutePath();
        String ouputPath = path;
        copyFilesHutool(inputPath,ouputPath);
        copyFilesRec(inputPath,ouputPath);
    }

    public static void copyFilesHutool(String inputPath,String outputPath){
        //拷贝文件
        FileUtil.copy(inputPath,outputPath,false);
    }

    public static void copyFilesRec(String inputPath,String outputPath) {
        File inputFile = new File(inputPath);
        File outputFile = new File(outputPath);
        try {
            copyFilesRec(inputFile,outputFile);
        } catch (Exception e){
            System.out.println("文件复制失败");
            e.printStackTrace();
        }
    }

    private static void copyFilesRec(File inputFile, File outputFile) throws IOException {
        //判断是文件还是目录
        if (inputFile.isDirectory()){
            System.out.println(inputFile.getName());
            File destOutputFile =new File(outputFile,inputFile.getName());
            //如果是目录，则创建目标目录
            if (!destOutputFile.exists()){
                destOutputFile.mkdirs();
            }
            //获取目录下的所有文件和子目录
            File[] files = inputFile.listFiles();
            //无子文件直接结束
            if (ArrayUtil.isEmpty(files)){
                return;
            }
            //递归拷贝下一次层文件
            for (File file: files){
                copyFilesRec(file,destOutputFile);
            }
        } else {
            //是文件就复制到目标目录下
            Path destPath = outputFile.toPath().resolve(inputFile.getName());
            Files.copy(inputFile.toPath(),destPath, StandardCopyOption.REPLACE_EXISTING);
        }
    }
}
