import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.geom.CubicCurve2D;
import java.io.File;

public class UploadCollateralFrame extends PopupFrame{
    private Customer customer;
    public UploadCollateralFrame(Customer customer) {
        super("Upload collateral");
        this.customer = customer;
        setFrame();
        setVisible(true);
    }

    private void setFrame(){
        JPanel jPanel = new JPanel(new GridLayout(0,2));
        jPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        JLabel nameLabel = new JLabel("Collateral Name: ");
        jPanel.add(nameLabel);

        JTextField collName = new JTextField();
        jPanel.add(collName);

        JLabel collLabel = new JLabel("Certificate: ");
        jPanel.add(collLabel);

//        JFileChooser file = new JFileChooser(System.getProperty("user.dir"));
//        file.setFileSelectionMode(JFileChooser.FILES_ONLY);
        JFileChooser fc = new JFileChooser();
        fc.setCurrentDirectory(new File(System.getProperty("user.dir")));//设置默认显示为当前文件夹
        fc.setFileSelectionMode(JFileChooser.FILES_ONLY);//设置选择模式（只选文件、只选文件夹、文件和文件均可选）
        fc.setMultiSelectionEnabled(false);//是否允许多选
//        fc.addChoosableFileFilter(new FileNameExtensionFilter("zip(*.zip, *.rar)", "zip", "rar"));//文件过滤器
        fc.setFileFilter(new FileNameExtensionFilter("image(*.jpg,*.jpeg, *.png, *.gif)", "jpg","jpeg", "png", "gif"));
        fc.showOpenDialog(null);
        jPanel.add(fc);

        JButton ok = new JButton("OK");
        ok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String name = collName.getText().trim();
                    String message = Collateral.valuateCollateral(customer, name, fc.getSelectedFile());
                    UploadCollateralFrame.this.dispose();
                    new CustomerFrame(customer).setContextPanel(new MultiCurrAccountPanel(customer.getAccount("Loan")));
                    new MessageFrame("Request upload", message);
                    //                    ((MultiCurrAccountPanel)((CustomerFrame)GuiUtil.getFrame(UploadCollateralFrame.this)).getContextPanel()).repaintPanel();
                }catch (NumberFormatException e1){
                    new MessageFrame("Input Error", "Please enter a number");
                }
            }
        });
        jPanel.add(ok);

        JButton back = new JButton("Cancel");
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new CustomerFrame(customer).setContextPanel(new MultiCurrAccountPanel(customer.getAccount("Loan")));
            }
        });
        jPanel.add(back);

        add(jPanel);
    }
}
