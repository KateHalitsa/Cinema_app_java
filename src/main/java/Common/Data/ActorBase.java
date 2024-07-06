package Common.Data;

public class ActorBase extends BaseObject{
    public String name;
    public String lastName;

    @Override
    public String toString() {
        return  name + " " + lastName;
    }
}
