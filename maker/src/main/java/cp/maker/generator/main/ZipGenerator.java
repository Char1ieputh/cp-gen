package cp.maker.generator.main;

public class ZipGenerator extends GenerateTemplate {
    @Override
    protected String buildDist(String outputPath, String sourceCopyPath, String jarPath, String shellOutputPath) {
        String disPath = super.buildDist(outputPath,sourceCopyPath,jarPath,shellOutputPath);
        return super.buildZip(disPath);
    }
}

