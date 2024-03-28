package cp.generator;

import cn.hutool.core.io.FileUtil;

public class StaGenerator {
    /**
     * 拷贝文件（Hutool实现）
     * @param inputPath
     * @param outputPath
     */
    public static void copyFilesHutool(String inputPath,String outputPath){
        //拷贝文件
        FileUtil.copy(inputPath,outputPath,false);
    }

}