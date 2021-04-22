public class User {
    private String name;
    private String password;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User(String name, String password){
        this.name  = name;
        this.password = password;
    }

    public void sendMessage(String message){
        TransactionLog.showMessage(this,message);
    }
}
