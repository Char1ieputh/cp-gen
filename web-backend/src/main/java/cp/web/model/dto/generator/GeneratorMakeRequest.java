package cp.web.model.dto.generator;

import cp.maker.meta.Meta;
import lombok.Data;

@Data
public class GeneratorMakeRequest {
    /**
     * 压缩文件路径
     */
    private String zipFilePath;

    /**
     * 元信息
     */
    private Meta meta;

    private static final long serialVersionUID = 1L;
}
