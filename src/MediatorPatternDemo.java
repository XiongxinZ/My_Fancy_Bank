public class MediatorPatternDemo {
    public static void main(String[] args) {
        User client = new User("Xiongxin", "123");
        User banker = new Banker("CPK","123");

        client.sendMessage("Hi! CPK!");
        banker.sendMessage("Hello! " + client.getName() + "! What can I do for you?");
    }
}