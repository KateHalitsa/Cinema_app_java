package Server.src.Data;

public class Actor {
    protected String name;
    protected String lastName;
    public Actor(String name, String lastName){
        this.name=name;
        this.lastName=lastName;
    }
    public void setName(String name){
        this.name=name;
    }
    public void setLastName(String lastName){
        this.lastName=lastName;
    }
    public String getName(){
        return name;
    }
    public String lastName(){
        return lastName;
    }
    @Override
    public String toString() {
        return "Actor{" +
                "name='" + name + "'" +
                ", lastname=" + lastName+
                '}' ;
    }
}
