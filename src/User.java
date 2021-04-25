import java.io.Serial;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.Objects;

public class User implements Serializable {
    @Serial
    private static final long serialVersionUID = -4965774420061036775L;

    private String name;
    private String password;
    private String type;
    private String id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public User(String name, String password,String type){
        this.name  = name;
        this.password = password;
        this.type = type;
        this.id = Long.toString((name.hashCode() * 31L + type.hashCode())*31 + password.hashCode());
    }

    public User(){}

    public void setPassword(String password) {
        this.password = password;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setId(String id) {
        this.id = id;
    }


    //    public User(String name, String password){
//        this.name  = name;
//        this.password = password;
//        this.id = name.hashCode() + System.currentTimeMillis();
//    }

//    public String EncoderByMd5(String str) throws UnsupportedEncodingException, NoSuchAlgorithmException {
//        //确定计算方法
//        MessageDigest md5 = MessageDigest.getInstance("MD5");
//        BASE64Encoder base64en = new BASE64Encoder();
//        //加密后的字符串
//        String newstr=base64en.encode(md5.digest(str.getBytes("utf-8")));
//        return newstr;
//    }


    public String getId() {
        return id;
    }

    public void sendMessage(String message){
        TransactionLog.showMessage(this,message);
    }
}
