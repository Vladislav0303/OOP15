import java.io.Serializable;

public class Courier implements Serializable {
    private String name;
    private boolean isFree;

    public Courier(String name, boolean isFree) {
        this.name = name;
        this.isFree = isFree;
    }

    public String getName() {
        return name;
    }
    public boolean isFree() {
        return isFree;
    }
    public void setFree(boolean free) {
        isFree = free;
    }

    public void setName(String name) {
        this.name = name;
    }
}
