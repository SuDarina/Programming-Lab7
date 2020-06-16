package Commands;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Comands <T> implements Serializable {
    public static List<String> commandsNames = new ArrayList<>();
    protected T args;
    protected String name;

    public T getArgs() {
        return args;
    }

    public String getName() {
        return name;
    }

    public void setArgs(T args) {
        this.args = args;
    }

    public void setName(String name) {
        this.name = name;
    }


}
