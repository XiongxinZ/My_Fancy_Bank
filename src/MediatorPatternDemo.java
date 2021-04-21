public class MediatorPatternDemo {
    public static void main(String[] args) {
        User client = new User("Xiongxin");
        User banker = new Banker("CPK");

        client.sendMessage("Hi! CPK!");
        banker.sendMessage("Hello! " + client.getName() + "! What can I do for you?");
    }
}