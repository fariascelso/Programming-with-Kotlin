import java.io.FileNotFoundException;

public class FileJava {
    public void throwsExample() throws FileNotFoundException {
        boolean exists = new File("somefile.txt").exists();
        System.out.println("File exists");
    }
}
