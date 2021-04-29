import javax.swing.*;

public class TestPanel {

    public static void test(JPanel panel){
        JFrame jf = new JFrame();
        jf.add(panel);
        jf.setSize(ConfigUtil.getConfigInt("FrameWidth"), ConfigUtil.getConfigInt("FrameHeight"));
        jf.setVisible(true);
    }
}
