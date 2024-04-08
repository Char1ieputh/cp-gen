package cp.maker.template.model;

import cp.maker.meta.Meta;
import lombok.Data;

@Data
public class TemplateMakerConfig {
    private Long id;

    private Meta meta =new Meta();

    private String originPath;

    TemplateMakerFileConfig fileConfig =new TemplateMakerFileConfig();

    TemplateMakerModelConfig modelConfig = new TemplateMakerModelConfig();

    TemplateMakerOutputConfig outputConfig = new TemplateMakerOutputConfig();

}
