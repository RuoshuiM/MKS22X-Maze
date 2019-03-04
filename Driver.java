/**
 * 
 */
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 *
 */
public class Driver {

    public static void main(String[] args) {
        String filename;
        if (args.length == 1) {
            filename = args[0];
        } else {
            filename = "data3.dat";
        }
        try {
            
            Maze f;
            f = new Maze(filename);// true animates the maze.

//            f.setAnimate(true);
            
            System.out.println(f.solve());
            
            System.out.println(f);
            
        } catch (FileNotFoundException e) {
            System.err.println("Invalid filename: " + filename);
        } catch (IOException e) {
            System.err.println("Error reading file");
        }
    }

}
