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
        this.setText("¡ø");
        this.setHorizontalTextPosition(SwingConstants.RIGHT );
        addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent arg0) {
                if(isSelected()){
                    setText("¨‹");
                    menu.show(MyMenuButton.this, 0, MyMenuButton.this.getHeight());
                }else{
                    setText("¡ø");
                    menu.setVisible(false);
                }

            }
        });
    }
    public MyMenuButton(final String label){
        super(label);
        this.setText("¡ø "+label);
        this.setHorizontalTextPosition(SwingConstants.RIGHT );
        addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent arg0) {
                if(isSelected()){
                    setText("¨‹"+label);
                    menu.show(MyMenuButton.this, 0, MyMenuButton.this.getHeight());
                }else{
                    setText("¡ø"+label);
                    menu.setVisible(false);
                }
            }
        });
    }
    public void addMenu(JPopupMenu menu){
        this.menu=menu;
    }
}
