import java.io.*;

public class FileUtil {
    public static Object getData(String filename){
        File f = new File(filename);
        if (!f.exists()){
            try {
                f.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f))){
            return ois.readObject();
        } catch (FileNotFoundException e) {
            System.out.println("No such file. Create a new file");;
        } catch (IOException e) {
            System.out.println("Empty! Return null");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static void writeData(String filename, Serializable object){
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))){
            oos.writeObject(object);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
