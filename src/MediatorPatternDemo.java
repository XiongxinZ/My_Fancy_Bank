public class MediatorPatternDemo {
    public static void main(String[] args) {
        User client = new User("Robert");
        User banker = new Banker("CPK");

        client.sendMessage("Hi! CPK!");
        banker.sendMessage("Hello! Xiongxin! What can I do for you?");
    }
}