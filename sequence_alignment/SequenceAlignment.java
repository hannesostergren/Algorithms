import java.util.HashMap;
import java.util.Scanner;

public class SequenceAlignment{
    int numChars;                           /* Number of characters in query strings (width of cost matrix) */
    int numQueries;                         /* Number of queries (pairs of strings that will be aligned) */
    HashMap<Character, Integer> invChars;   /* Inverted char map from Char p -> Integer index */
    int[][] scoreMatrix;                    /* Score matrix for each character pair (c1, c2) (Could be reduced to triangular matrix */
    String[][] queries;                     /* Queries with sequences to align */
    int[][] A;                              /* Matrix A, dynamically filled */
    final int delta = -4;                   /* Cost of inserting space character '*' */

    /**
     * Read the input from stdin and initialize attributes
     */
    private void readInput(){
        Scanner scanner = new Scanner(System.in);
        String inputChars = scanner.nextLine();
        String[] chars = inputChars.split(" ");
        numChars = chars.length;
        invChars = new HashMap<>();
        for (int i = 0; i < numChars; i++){
            invChars.put(chars[i].charAt(0), i);
        }
        scoreMatrix = new int[numChars][numChars];
        for (int i = 0; i < numChars; i++){
            for (int j = 0; j < numChars; j++){
                scoreMatrix[i][j] = scanner.nextInt();
            }
        }
        numQueries = scanner.nextInt();
        queries = new String[numQueries][2];
        scanner.nextLine(); // Skip rest of Q line
        for (int i = 0; i < numQueries; i++){
            String readQueries = scanner.nextLine();
            String[] queryStrings = readQueries.split(" ");
            queries[i][0] = queryStrings[0];
            queries[i][1] = queryStrings[1];
        }
        scanner.close();
    }

    /**
     * Initialize Matrix A in O(mn) time
     * @param X First sequence to align
     * @param Y Second sequence to align
     * @param m Length of the first sequence
     * @param n Length of the second sequence
     */
    private void init(String X, String Y, int m, int n){
        A = new int[m+1][n+1];
        for (int i = 0; i < m + 1; i++){
            A[i][0] = i * delta;
        }
        for (int j = 0; j < n + 1; j++){
            A[0][j] = j * delta;
        }
        for (int i = 1; i < m + 1; i++){
            for (int j = 1; j < n + 1; j++){
                int diag = A[i - 1][j - 1] + scoreMatrix[invChars.get(X.charAt(i - 1))][invChars.get(Y.charAt(j - 1))];
                int redi = delta + A[i - 1][j];
                int redj = delta + A[i][j - 1];
                int min = Math.max(diag, Math.max(redj, redi));
                A[i][j] = min;
            }
        }
    }

    /**
     * Backtrack through the matrix A to find optimal alignment,
     * i.e. the alignment which maximizes the score
     * (Usually we minimize the cost but the input is made for maximizing
     * in this case)
     * @param X First sequence to align
     * @param Y Second sequence to align
     * @param i Index of character in X to align
     * @param j Index of character in Y to align
     * @return String with aligned sequences (%s %s)
     */
    private String alignment(String X, String Y, int i, int j){
        StringBuilder alignedX = new StringBuilder();
        StringBuilder alignedY = new StringBuilder();
        
        // Stop when both i and j reach 0
        while(i > 0 || j > 0){
            if (i > 0 && j > 0){
                int score = scoreMatrix[invChars.get(X.charAt(i - 1))][invChars.get(Y.charAt(j - 1))];
                if (A[i][j] == A[i - 1][j - 1] + score){    // Check if diagonal is the route with maximal score
                    alignedX.insert(0, X.charAt(i - 1));
                    alignedY.insert(0, Y.charAt(j - 1));
                    i--;
                    j--;
                    continue;
                }
            }
            if (i > 0 && A[i][j] == A[i - 1][j] + delta){   // Check if i - 1 is the route with maximal score
                alignedX.insert(0, X.charAt(i - 1));
                alignedY.insert(0, "*");
                i--;
            }
            else if (j > 0 && A[i][j] == A[i][j - 1] + delta){  // Check if j - 1 is the route with maximal score
                alignedX.insert(0, "*");
                alignedY.insert(0, Y.charAt(j - 1));
                j--;
            }
        }
        return alignedX + " " + alignedY;
    }

    /**
     * Read the input, then calculate the matrix A and alignment for each query
     */
    private void run(){
        readInput();
        for (String[] query : queries){
            init(query[0], query[1], query[0].length(), query[1].length());
            System.out.println(alignment(query[0], query[1], query[0].length(), query[1].length()));
        }
    }
    public static void main(String[] args) {
        SequenceAlignment sa = new SequenceAlignment();
        sa.run();
    }
}