import java.util.*;
public class Driver {
    /*
     * @author Roberto Rodriguez
     * @date 10/23/2018
     */
    public static void main(String[] args) {

        AStarSearch star;
        Node[] path;
        int x1,x2,y1,y2;

        Scanner kb = new Scanner(System.in);

        // asking user to enter the starting and goal node
        System.out.println("Please enter an x position for the starting node: ");
        x1 = kb.nextInt();
        System.out.println("Please enter an y position for the starting node: ");
        y1 = kb.nextInt();
        System.out.println("Please enter an x position for the goal node: ");
        x2 = kb.nextInt();
        System.out.println("Please enter an y position for the goal node: ");
        y2 = kb.nextInt();

        // calling a*search with starting and goal node positions
        star = new AStarSearch(x1,y1,x2,y2);

        System.out.println("Generating path from starting node to goal node: ");

        // searching for path...
        star.searchPath();

        // getting the path from goal to start
        path = star.getPath();

        // Printing path from each node from start to goal
        for (int i = star.getPathCount() - 1; i >= 0; i--) {
            System.out.println(path[i].toString());
        }

        System.out.println("Starting node: S, Goal node: G, Path: X");
        // Printing path on a 15 x 15 grid with starting node: S, goal node: G, and nodes along the path: X
        System.out.println(star.toString());
        kb.close();
    }

}

