package cp.cli.command;

import cn.hutool.core.util.ReflectUtil;
import cp.model.DataModel;
import picocli.CommandLine;

import java.lang.reflect.Field;
@CommandLine.Command(name = "Config",description = "查看参数信息",mixinStandardHelpOptions = true)
public class ConfigCommand implements Runnable{
    public void run() {
        //实现config逻辑
        System.out.println("查看参数信息");
        Field[] fields = ReflectUtil.getFields(DataModel.class);
        for (Field field : fields) {
        System.out.println("字段名称"+field.getName());
        System.out.println("字段类型"+field.getType());
        System.out.println("---");
 }
}
}