package Server.src.Data;


public class User {
    private String login;
    private String password;
    private int status;
    public User(String login,String password,int status){
        this.login=login;
        this.status=status;
        this.password=password;
    }

    public void setStatus (int status) {
        this.status = status;
    }

    public void setLogin (String login) {
        this.login = login;
    }

    public void setPassword (String password) {
        this.password = password;
    }

    public int getStatus () {
        return this.status;
    }

    public String getLogin () {
        return this.login;
    }

    public String getPassword () {
        return this.password;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + login + '\'' +
                ", password=" + password +
                ", skills=" + status ;
    }

}
