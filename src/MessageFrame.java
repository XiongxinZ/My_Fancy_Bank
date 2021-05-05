import javax.swing.*;

public class MessageFrame extends PopupFrame {
    public MessageFrame(String title, String content){
        super(title);
        JLabel jl = new JLabel("<html><body style='width: %1spx'>" + content,JLabel.CENTER);
        add(jl);
        setVisible(true);
    }
}
