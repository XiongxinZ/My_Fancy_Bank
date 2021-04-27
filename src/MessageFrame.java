import javax.swing.*;
import java.awt.*;

public class MessageFrame extends JFrame {
    public MessageFrame(String title, String content){
        super(title);
        JLabel jl = new JLabel(content,JLabel.CENTER);
        add(jl);
        setSize(200,100);
        setVisible(true);
    }
}
