/*
 * @author Roberto Rodriguez
 * @date 10/23/2018
 */
public class AStarSearch {

    private Heap openList = new Heap();
    private Node[] closedList = new Node[225];
    private Node[] path = new Node[225];
    private Board board;
    private Node currNode;
    private Node startNode;
    private Node goalNode;
    private int pos = 0;
    private int numPath = 0;

    //constructor to create board, starting node, and goal node
    public AStarSearch(int sX, int sY, int gX, int gY) {
        board = new Board(sX,sY,gX,gY);
        startNode = board.getNeighbor(sX, sY);
        startNode.setG(0);
        goalNode = board.getNeighbor(gX, gY);
        manhattanMethod(startNode);
        startNode.setF();
        currNode = startNode;
    }

    // method to find path from starting node to goal node
    public void searchPath() {
        //determine if the current node equals the current node or there is no possible path or create neighbors
        if (currNode == null) {
            System.out.println("No path could be found!");
        }
        else if (currNode.equals(goalNode)) {
            setPath(currNode);
        }
        else {
            Node neighbor;

            //setting up location of neighbors
            int x1 = currNode.getRow() - 1;
            int x2 = currNode.getRow() + 1;
            int y1 = currNode.getCol() - 1;
            int y2 = currNode.getCol() + 1;

            if (currNode.getRow() - 1 < 0)
                x1 = currNode.getRow();
            else if (currNode.getRow() + 1 > 14)
                x2 = currNode.getRow();
            if (currNode.getCol() - 1 < 0)
                y1 = currNode.getCol();
            else if (currNode.getCol() + 1 > 14)
                y2 = currNode.getCol();

            // nested for loop to find neighbors
            for (int x = x1; x <= x2; x++) {
                for (int y = y1; y <= y2; y++) {
                    // setting the neighbor
                    neighbor = board.getNeighbor(x, y);
                    // determine if the neighbor is able to be entered, the neighbor does not equal the current node and the neighbor is not in the closed list
                    if (neighbor.getType() == 0 && !neighbor.equals(currNode) && !inClosedList(neighbor)) {
                        // determine if the neighbor has a parent
                        if (neighbor.getParent() != null) {
                            // determine if neighbor is diagonal or beside the current node
                            if (currNode.getRow() == x || currNode.getCol() == y)
                                // determine if the current node should be the new parent of the neighbor
                                if (neighbor.getG() > currNode.getG() + 15) {
                                    neighbor.setParent(currNode);
                                    neighbor.setG(currNode.getG() + 15);
                                    manhattanMethod(neighbor);
                                    neighbor.setF();
                                }
                                else {
                                    // determine if the current node should be the new parent of the neighbor
                                    if (neighbor.getG() > currNode.getG() + 21) {
                                        neighbor.setParent(currNode);
                                        neighbor.setG(currNode.getG() + 21);
                                        manhattanMethod(neighbor);
                                        neighbor.setF();
                                    }
                                }
                        }
                        else {
                            // setting the current node as the parent of the neighbor
                            neighbor.setParent(currNode);
                            // determine if neighbor is diagonal or beside the current node
                            if (currNode.getRow() == x || currNode.getCol() == y) {
                                neighbor.setG(currNode.getG() + 15); // setting the g for the neighbor that is beside the current node
                            }
                            else {
                                neighbor.setG(currNode.getG() + 21); // setting the g for the neighbor that is diagonal from the current node
                            }
                            manhattanMethod(neighbor); // setting the h for the neighbor
                            neighbor.setF(); // setting the f for the neighbor
                            openList.add(neighbor); // adding neighbor to the open list
                        }
                    }
                }
            }
            closedList[pos] = currNode; // adding current node to the closed list
            pos++;
            currNode = openList.remove(); // removing next node from open list to the current list
            searchPath(); // recursively using searchPath() to search the entire grid to find path
        }
    }

    // method to determine if a node is in the closed list
    public boolean inClosedList(Node node) {
        boolean value = false;
        for (int i = 0; i < pos; i++) {
            if (closedList[i].equals(node))
                value = true;
        }
        return value;
    }

    //method to determine if a node is in the path from the starting node to the goal node
    public boolean inPathList(Node node) {
        boolean value = false;
        for (int i = 0; i < numPath; i++) {
            if (path[i].equals(node))
                value = true;
        }
        return value;
    }

    //method to recursively set the path with a node and its parent.
    public void setPath(Node node) {
        if (node.getParent() != null) {
            path[numPath] = node;
            numPath++;
            setPath(node.getParent());
        }
        else {
            path[numPath] = node;
            numPath++;
        }
    }

    //method to return the path
    public Node[] getPath() {
        return path;
    }

    //method to get the number of nodes in the path
    public int getPathCount() {
        return numPath;
    }

    //method to determine the manhattan method and set the heuristic value
    public void manhattanMethod(Node node) {
        int value = Math.abs(node.getRow() - goalNode.getRow()) + Math.abs(node.getCol() - goalNode.getCol());
        node.setH(value * 15);
    }

    //method to display the board and the starting (S) to the (X's) the goal node (G)
    public String toString() {
        String display = "";

        for (int x = 0; x < 15; x++) {
            for (int y = 0; y < 15; y++) {
                if(y == 14) {
                    if (startNode.equals(board.getNeighbor(x, y)))
                        display = display.concat("S");
                    else if (goalNode.equals(board.getNeighbor(x, y)))
                        display = display.concat("G");
                    else if (inPathList(board.getNeighbor(x, y))) {
                        for (int i = 0; i < numPath; i++) {
                            if (path[i].equals(board.getNeighbor(x, y))) {
                                display = display.concat("X");
                                i = numPath;
                            }
                        }
                    }
                    else
                        display = display.concat(board.getNeighbor(x, y).getType()+ "");
                }
                else {
                    if (startNode.equals(board.getNeighbor(x, y)))
                        display = display.concat("S,");
                    else if (goalNode.equals(board.getNeighbor(x, y)))
                        display = display.concat("G,");
                    else if (inPathList(board.getNeighbor(x, y))) {
                        for (int j = 0; j < numPath; j++) {
                            if (path[j].equals(board.getNeighbor(x, y))) {
                                display = display.concat("X,");
                                j = numPath;
                            }
                        }
                    }
                    else
                        display = display.concat(board.getNeighbor(x, y).getType() + ",");
                }
            }
            display = display.concat("\n");
        }
        return display;
    }
}

