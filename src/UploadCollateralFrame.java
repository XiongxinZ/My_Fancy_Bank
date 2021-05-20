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
    private String fileName;
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


        JButton chooseFile = new JButton("Choose File");
        chooseFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fc = new JFileChooser();
                fc.setCurrentDirectory(new File(System.getProperty("user.dir")));
                fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
                fc.setMultiSelectionEnabled(false);//single choice
                fc.setFileFilter(new FileNameExtensionFilter("image(*.jpg,*.jpeg, *.png, *.gif)", "jpg","jpeg", "png", "gif"));
                fc.showDialog(new JLabel(),"choose");
//                fc.showOpenDialog(null);

                UploadCollateralFrame.this.fileName=fc.getSelectedFile().getAbsolutePath();
                chooseFile.setText(fc.getSelectedFile().getName());
            }
        });
        jPanel.add(chooseFile);
//        JFileChooser file = new JFileChooser(System.getProperty("user.dir"));
//        file.setFileSelectionMode(JFileChooser.FILES_ONLY);

//        jPanel.add(fc);

        JButton ok = new JButton("OK");
        ok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String name = collName.getText().trim();
                    String message = Collateral.valuateCollateral(customer, name, new File(fileName));
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
