package cp.maker.meta;

public class MetaExecption extends RuntimeException{
    public MetaExecption(String message){
        super(message);
    }
    public MetaExecption(String message,Throwable throwable){
        super(message,throwable);
    }

}
