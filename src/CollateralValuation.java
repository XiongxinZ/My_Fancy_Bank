import java.io.*;
import java.nio.channels.FileChannel;
import java.util.Date;

public class CollateralValuation implements Order{

    @Serial
    private static final long serialVersionUid = 8331184208579642887L;
    private String customerId;

    private String name;
    private File file;

    private Double price = null;

    public CollateralValuation(String customerId, String name, File file) {
        this.customerId = customerId;
        this.file = file;
        this.name = name;
    }


    @Override
    public String apply() {
        try{
            FileChannel fis = new FileInputStream(file).getChannel();
            String filePath = System.getProperty("user.dir")+"/certificate/"+ customerId +name+file.getName();
            File out = new File(filePath);
            if (!out.getParentFile().exists()){
                out.getParentFile().mkdir();
            }
            out.createNewFile();
            FileChannel fos = new FileOutputStream(filePath).getChannel();
            fos.transferFrom(fis, 0, fis.size());
            file = out;
            CollateralDao.insertCollateralRequest(this);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Request submitted! Please wait the manager to valuate";
    }

    @Override
    public String execute() {
        assert price != null;
        String coId = Long.toString(customerId.hashCode() * 31L + new Date().hashCode());
        CollateralDao.insertCollateral(customerId, name, price, 0, coId);
        return "Success";
    }

    public void setPrice(double price){
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public String getFileName() {
        return file.getName();
    }

    public String getCustomerId() {
        return customerId;
    }
}
