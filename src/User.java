import java.io.Serial;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.Objects;

public class User{

    private String name;
    private String password;
    private String type;
    private String id;
    private String email;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public User(String name, String password,String type, String email){
        this.name  = name;
        this.password = password;
        this.type = type;
        this.email = email;
        this.id = Long.toString(email.hashCode() * 31L + type.hashCode());
    }

    public User(String password,String type, String email){
//        this.name  = name;
        this.password = password;
        this.type = type;
        this.email = email;
        this.id = Long.toString(email.hashCode() * 31L + type.hashCode());
    }

    public User(){}

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public String getId() {
        return id;
    }
}
