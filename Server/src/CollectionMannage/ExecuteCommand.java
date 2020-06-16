package CollectionMannage;
import java.util.concurrent.Callable;


public class ExecuteCommand implements Callable <byte[]> {
    Execute execute;
    public ExecuteCommand(Execute execute){
        this.execute = execute;
    }

    @Override
    public byte[] call() throws Exception {

        return execute.execute();
    }
}
