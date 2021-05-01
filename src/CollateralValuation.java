import java.io.*;
import java.nio.channels.FileChannel;

public class CollateralValuation implements Order{

    @Serial
    private static final long serialVersionUid = 8331184208579642887L;
    Customer customer;

    String name;
    File file;

    public CollateralValuation(Customer customer, String name, File file) {
        this.customer = customer;
        this.file = file;
        this.name = name;
    }


    @Override
    public String apply() {
        try(FileChannel fis = new FileInputStream(file).getChannel();
            FileChannel fos = new FileOutputStream("/certificate/"+file.getName()).getChannel()
        ){
            fos.transferFrom(fis, 0, fis.size());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String execute() {
        return null;
    }
}
