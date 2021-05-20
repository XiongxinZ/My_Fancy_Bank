import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPopupMenu;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;
public class MyMenuButton extends JToggleButton{
    private JPopupMenu menu;
    private static int i=0;
    public MyMenuButton(){
        super();
        this.setText("^^");
        this.setHorizontalTextPosition(SwingConstants.RIGHT );
        addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent arg0) {
                if(isSelected()){
                    setText("ww");
                    menu.show(MyMenuButton.this, 0, MyMenuButton.this.getHeight());
                }else{
                    setText("^^");
                    menu.setVisible(false);
                }

            }
        });
    }
    public MyMenuButton(final String label){
        super(label);
        this.setText("^^"+label);
        this.setHorizontalTextPosition(SwingConstants.RIGHT );
        addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent arg0) {
                if(isSelected()){
                    setText("ww"+label);
                    menu.show(MyMenuButton.this, 0, MyMenuButton.this.getHeight());
                }else{
                    setText("^^"+label);
                    menu.setVisible(false);
                }
            }
        });
    }
    public void addMenu(JPopupMenu menu){
        this.menu=menu;
    }

}
