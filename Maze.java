import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Maze {

    // private static class Positions {
    // private int row;
    // private int col;
    //
    // public Positions(int row, int col) {
    // this.row = row;
    // this.col = col;
    // }
    //
    // public int up() {
    // return new int[] {row - 1, col};
    // }
    //
    // public int down() {
    // return new int[] {row + 1, col};
    // }
    //
    // // finish??
    // }

    private static int[][] positions = new int[][] {
            // up
            { -1, 0 },
            // down
            { 1, 0 },
            // right
            { 0, 1 },
            // left
            { 0, -1 } };

    private String[][] maze;
    private boolean animate;// false by default

    private int startRow, startCol;

    /**
     * Constructor loads a maze text file, and sets animate to false by default.
     * <ol>
     * <li>The file contains a rectangular ascii maze, made with the following 4
     * characters: '#' - Walls - locations that cannot be moved onto ' ' - Empty
     * Space - locations that can be moved onto 'E' - the location of the goal
     * (exactly 1 per file)
     * 
     * 'S' - the location of the start(exactly 1 per file)</li>
     * <li>The maze has a border of '#' around the edges. So you don't have to check
     * for out of bounds!</li>
     * </ol>
     * 
     * @throws IOException
     * @throws FileNotFoundException When the file is not found
     * @throws IllegalStateException When the file is invalid (not exactly 1 E and 1
     *                               S)
     * 
     */
    public Maze(String filename) throws FileNotFoundException, IOException {
        // COMPLETE CONSTRUCTOR
        
        String filepath = filename;
        
        // For running from eclipse
//        String filepath = System.getProperty("user.dir").concat("\\src\\recursion\\" + filename);

        // For running from bash in /bin
        // filepath = System.getProperty("user.dir").concat("\\..\\src\\recursion\\" +
        // filename);

        // debug
        // System.out.println(filepath);

        StringBuilder lines = new StringBuilder();

        final String lnBk = System.lineSeparator();

        try (BufferedReader file = new BufferedReader(new FileReader(filepath));) {

            String line = file.readLine();

            while (line != null) {
                // debug
                // System.out.println(line);
                lines.append(line);
                lines.append(lnBk);
                line = file.readLine();
            }

        }

        String lineStr = lines.toString();

        Matcher m = Pattern.compile("[E|S]").matcher(lineStr);

        int ECount = 0, SCount = 0;
        int posS = 0;
        while (m.find()) {
            String c = m.group();
            if (c.equals("E"))
                ECount++;
            else if (c.equals("S")) {
                SCount++;
                posS = m.start();
            }
        }

        if (ECount != 1 || SCount != 1) {
            System.err.println(lineStr);
            throw new IllegalStateException("File should contain exactly 1 E and 1 S");
        }

        String[] rows = lineStr.split(lnBk);

        String[][] charArray = new String[rows.length][rows[0].length()];
        for (int i = 0; i < rows.length; i++) {
            charArray[i] = rows[i].split("");
        }

//        this.startRow = posS / charArray[0].length + lnBk.length() - 2;
//        this.startCol %= charArray[0].length + lnBk.length();

        for (int i = 0; i < rows.length; i++) {
            if (rows[i].contains("S")) {
                this.startRow = i;
                this.startCol = rows[i].indexOf("S");
            }
        }

        // debug
        // System.out.format("Row: %d Col: %d%n", startRow, startCol);

        this.maze = charArray;

        this.setAnimate(false);
    }

    private void wait(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
        }
    }

    public void setAnimate(boolean b) {

        animate = b;

    }

    public void clearTerminal() {

        // erase terminal, go to top left of screen.

        System.out.println("\033[2J\033[1;1H");

    }

    /**
     * Wrapper Solve Function returns the helper function
     * 
     * Note the helper function has the same name, but different parameters. Since
     * the constructor exits when the file is not found or is missing an E or S, we
     * can assume it exists.
     * 
     */
    public int solve() {

        // find the location of the S.

        // erase the S

        // and start solving at the location of the s.

        // return solve(???,???);
        this.maze[startRow][startCol] = " ";
        return solve(startRow, startCol, 0);
    }

    /**
     * Recursive Solve function:
     * 
     * A solved maze has a path marked with '@' from S to E.
     * 
     * Returns the number of @ symbols from S to E when the maze is solved, Returns
     * -1 when the maze has no solution.
     * 
     * 
     * Postcondition:
     * 
     * The S is replaced with '@' but the 'E' is not.
     * 
     * All visited spots that were not part of the solution are changed to '.'
     * 
     * All visited spots that are part of the solution are changed to '@'
     */
    private int solve(int row, int col, int level) { // you can add more parameters since this is private

        // automatic animation! You are welcome.
        if (animate) {

            clearTerminal();
            System.out.println(this);

            wait(20);
        }
        
//         debug
//        System.out.println(level);
        
        // COMPLETE SOLVE
        if (maze[row][col].equals(" ")) {
            // try all directions
            maze[row][col] = "@";

            //
//            int up = solve(row - 1, col, level + 1);
//            if (up != -1)
//                return up;
//            int down = solve(row + 1, col, level + 1);
//            if (down != -1)
//                return down;
//            int right = solve(row, col + 1, level + 1);
//            if (right != -1)
//                return right;

            for (int[] pos : positions) {
                int result = solve(row + pos[0], col + pos[1], level + 1);
                if (result != -1)
                    return result;
            }

            maze[row][col] = ".";
            return -1;

        } else if (maze[row][col].equals("E")) {
            return level;
        } else {
            return -1;
        }
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        for (String[] row : this.maze) {
            for (String c : row) {
                str.append(c);
            }
            str.append(String.format("%n"));
        }
        return str.toString();
    }

}
