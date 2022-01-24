import java.util.Random;
/*
 * @author Roberto Rodriguez
 * @date 10/23/2018
 */
public class Board {
    private Node[][] arrN = new Node[15][15];
    public Board(int xS,int yS,int xG,int yG) {

        //loop to set up a 15 x 15 grid of able to be passed through nodes
        for (int x = 0; x < 15; x++) {
            for (int y = 0; y < 15; y++) {
                arrN[x][y] = new Node(x, y, 0);
            }
        }

        //loop to determine random nodes to not be passed through
        for (int i = 0; i < 23; i++) {
            Random rand = new Random();
            int posX = rand.nextInt(15);
            int posY = rand.nextInt(15);
            if (arrN[posX][posY].getType() == 0 && !arrN[xS][yS].equals(arrN[posX][posY]) && !arrN[xG][yG].equals(arrN[posX][posY]))
                arrN[posX][posY] = new Node(posX, posY, 1);
            else
                i--;
        }
    }

    // determine node depending on position
    public Node getNeighbor(int x, int y) {
        return arrN[x][y];
    }
}

