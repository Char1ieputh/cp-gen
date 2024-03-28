package cp.maker.meta;

import cn.hutool.core.io.resource.ResourceUtil;
import cn.hutool.json.JSONUtil;

public class MetaManager {
    private static volatile Meta meta;
    public static Meta getMetaObject(){
        if (meta == null){
            synchronized (MetaManager.class){
                if (meta == null){
                    meta = initMeta();
                }
            }
        }
        return meta;
    }
    public static Meta initMeta(){
        String json = ResourceUtil.readUtf8Str("meta.json");
        Meta meta = JSONUtil.toBean(json, Meta.class);
        //todo 校验配置文件，处理默认值
        return meta;
    }
}
