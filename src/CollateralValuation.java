import java.io.*;
import java.nio.channels.FileChannel;
import java.util.Date;
import java.util.List;

public class CollateralValuation implements Order{

    private String customerId;
    private String cvId;
    private Date requestDate;
    private Date solveDate = null;

    private String name;
    private File file;

    private Double price = null;
    private String status = "Solving";

    public CollateralValuation(String customerId, String name, File file) {
        this.customerId = customerId;
        this.file = file;
        this.name = name;
//        this.cvId = Long.toString(customerId.hashCode()*31L + new Date().hashCode());
        this.requestDate = new Date();
        this.cvId = Long.toString(customerId.hashCode()*31L + requestDate.hashCode());
    }

    public CollateralValuation(String customerId, String name, File file, String id, Date date) {
        this.customerId = customerId;
        this.file = file;
        this.name = name;
        this.requestDate = date;
        this.cvId = id;
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
//        assert price != null;
//        assert approve != null;
        Date solveDate = new Date();
        if (status.equals("Reject")){
            this.solveDate = solveDate;
            CollateralDao.updateCollateralRequest(this);
            return "Reject Request!";
        }else if (status.equals("Approve")){
            this.solveDate = solveDate;
            String coId = Long.toString(customerId.hashCode() * 31L + new Date().hashCode());
            CollateralDao.insertCollateral(customerId, name, price, 0, coId);
            CollateralDao.updateCollateralRequest(this);
            return "Approve and set Price successfully!";
        }else{
            return "No operation";
        }
    }

    public void setPrice(double price){
        this.price = price;
    }

    public void setApprove() {
        this.status = "Approve";
    }

    public void setReject(){
        this.status = "Reject";
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

    public File getFile() {
        return file;
    }

    public Double getPrice() {
        return price;
    }

    public String getStatus() {
        return status;
    }

    public String getCvId() {
        return cvId;
    }

    public Date getRequestDate() {
        return requestDate;
    }

    public Date getSolveDate() {
        return solveDate;
    }

    public void setSolveDate(Date solveDate) {
        this.solveDate = solveDate;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public static void main(String[] args) {
        List<CollateralValuation> list = CollateralDao.selectUnsolvedCollateralRequestList();
        for (CollateralValuation collateralValuation : list) {
            collateralValuation.setReject();
            collateralValuation.execute();
        }
    }
}
