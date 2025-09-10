import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ClosestPair{
    int N;                          /* Number of nodes */
    ArrayList<Player> playerList;   /* List of players */

    /**
     * Read the input from stdin, create Players from coordinates
     * and append them to the playerList
     */
    private void readInput(){
        Scanner scanner = new Scanner(System.in);
        N = scanner.nextInt();
        playerList = new ArrayList<>();
        while(scanner.hasNext()){
            int x = scanner.nextInt();
            int y = scanner.nextInt();
            Player player = new Player(x, y);
            playerList.add(player);
        }
    }

    /**
     * Sort the provided list of players by the x coordinate
     * from smallest to largest
     * @param unsortedPlayers The initial list of players before the sort
     * @return list of players sorted by x coordinate
     */
    private ArrayList<Player> sortByX(List<Player> unsortedPlayers){
        ArrayList<Player> players = new ArrayList<>(unsortedPlayers);
        players.sort((a, b) -> a.x - b.x);
        return players;
    }

    /**
     * Sort the provided list of players by the y coordinate
     * from smallest to largest
     * @param unsortedPlayers The initial list of players before the sort
     * @return list of players sorted by y coordinate
     */
    private ArrayList<Player> sortByY(List<Player> unsortedPlayers){
        ArrayList<Player> players = new ArrayList<>(unsortedPlayers);
        players.sort((a, b) -> a.y - b.y );
        return players;
    }

    /**
     * Find the smallest distance between two players using 
     * brute-force. Should only be used for lists of players
     * smaller than or equal to 3
     * @param px List of players sorted by ascending x coordinate
     * @param py List of players sorted by ascending x coordinate
     * @return The Pair of Players with the smallest distance between them
     */
    private Pair getSmallest(List<Player> px, List<Player> py){
        Pair currentSmallest = new Pair(px.get(0), px.get(1));
        for (Player p : px){
            for (Player q : py){
                if (!p.equals(q)){
                    if (distance(p, q) < distance(currentSmallest.p1, currentSmallest.p2)){
                        currentSmallest = new Pair(p, q);
                    }
                }
            }
        }
        return currentSmallest;
    }

    /**
     * Calculate the distance between two Players
     * @param p1 First Player
     * @param p2 Second Player
     * @return distance between the player's coordinates
     */
    private double distance(Player p1, Player p2){
        return Math.sqrt(Math.pow(p1.x - p2.x, 2) + Math.pow(p1.y - p2.y, 2));
    }

    /**
     * Recursive function to calculate the closest pair
     * Divides the playing field into two halves with equally many
     * players on each side. If the number of players on one side is
     * equal to or smaller than 3, then the answer is calculated through
     * brute force (which is O(1) as long as the upper bound 3 exists).
     * Finally, check the pairs across the Line x = x*, where x* is the x
     * coordinate of the rightmost point in the left half of a field.
     * Because of (5.10) in Algorithm Design, Chapter 5.4, only the nearest
     * 15 points have to be checked - making the loop complete in O(n) time.
     * @param px List of Players sorted by x coordinate
     * @param py List of Players sorted by y coordinate
     * @return Closest Pair of Players in the entire playing field
     */
    private Pair closestPairRec(List<Player> px, List<Player> py){
        if (px.size() <= 3 ){
            return getSmallest(px, py);
        }

        int listLength = px.size();
        List<Player> qx = px.subList(0, listLength/2);
        List<Player> qy = sortByY(px.subList(0, listLength/2));
        List<Player> rx = px.subList(listLength/2, listLength);
        List<Player> ry = sortByY(px.subList(listLength/2, listLength));

        Pair qPair = closestPairRec(qx, qy);
        Pair rPair = closestPairRec(rx, ry);

        double delta = Math.min(distance(qPair.p1, qPair.p2), distance(rPair.p1, rPair.p2));
        int Lx = qx.getLast().x;
        List<Player> sy = new ArrayList<>();
        for (Player p : py){
            if (Math.abs(Lx - p.x) < delta){
                sy.add(p);
            }
        }
        sy.sort((a, b) -> a.y - b.y);
        Pair smallestS;
        smallestS = new Pair(new Player(100000, 100000), new Player(-100000, -100000));
        for (int i = 0; i < sy.size(); i++){
            for (int j = i + 1; j < Math.min(i + 16, sy.size()); j++){
                if (distance(sy.get(i), sy.get(j)) < distance(smallestS.p1, smallestS.p2)){
                    smallestS = new Pair(sy.get(i), sy.get(j));
                }
            }
        }

        if (distance(smallestS.p1, smallestS.p2) < delta) {
            return smallestS;
        }
        else if (distance(qPair.p1, qPair.p2) < distance(rPair.p1, rPair.p2)) {
            return qPair;
        }
        else {
            return rPair;
        }

    }

    /**
     * Calculate the closest Pair in a list of Players
     * @return The closest Pair in the entire playing field
     */
    private Pair closestPair(){
        ArrayList<Player> xSortedPlayers = sortByX(playerList);
        ArrayList<Player> ySortedPlayers = sortByY(playerList);
        return closestPairRec(xSortedPlayers, ySortedPlayers);
    }

    /**
     * Read the input, run the algorithm and print the result
     */
    private void run(){
        readInput();
        Pair resultPair = closestPair();
        System.out.println(String.format("%.6f", distance(resultPair.p1, resultPair.p2)));
    }

    public static void main(String[] args){
        ClosestPair closestPair = new ClosestPair();
        closestPair.run();
    }
}