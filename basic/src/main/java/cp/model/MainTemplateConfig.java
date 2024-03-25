package cp.model;

import lombok.Data;

@Data
public class MainTemplateConfig {
    private String author = "cp";
    private String outputText = "sum=";
    private boolean loop;
}
