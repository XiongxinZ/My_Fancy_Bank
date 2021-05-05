import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class PopupFrame extends MyFrame{
    public PopupFrame(String title){
        super(title);
        setSize(ConfigUtil.getConfigInt("PopupWidth"), ConfigUtil.getConfigInt("PopupHeight"));
    }
}
