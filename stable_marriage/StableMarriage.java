import java.util.Arrays;
import java.util.Scanner;

class StableMarriage{
    int N;              /* Number of m and w */
    int[][] wpref;      /* w preference list */
    int[][] mpref;      /* m preference list */
    int[] mprops;       /* Number of proposals for each m */
    boolean[] freem;    /* Indicator of whether m is free */
    boolean[] freew;    /* Indicator of whether w is free */
    int[] matches;      /* Store which m w is matched with */

    /**
     * Add all numbers (except N) from the input to an array.
     * This allows parsing messy input
     * @param fileReader - Scanner object used to read lines from the input
     * @return Array of each number (except N) in the input as a string. E.g. ["1", "2", "3"]
     */
    String[] getAllNums(Scanner fileReader){
        int tot = 0;
        String[] allNums = new String[(N + 1) * 2 * N];
        while(fileReader.hasNext()){
            String data = fileReader.nextLine();
            String[] inargs = data.split(" ");
            int rowLength = inargs.length;
            for (int r = 0; r < rowLength; r++){
                allNums[tot] = inargs[r];
                tot++;
            }
        }
        return allNums;
    }

    /**
     * Convert array of strings to int matrix.
     * This reformats messy input to the correct 2N chunks of (N + 1) numbers
     * @param allNums - Array of string representations of all input numbers
     * @return int matrix with 2N chunks of (N + 1) numbers
     */
    int[][] getCleanLines(String[] allNums){
        int[][] lines = new int[2 * N][N + 1];
        for (int i = 0; i < 2 * N; i++){
            for (int j = 0; j < N + 1; j++){
                lines[i][j] = Integer.parseInt(allNums[(i * (N + 1)) + j]);
            }
        }
        return lines;
    }

    /**
     * Initialize arrays (and fill boolean arrays with true)
     */
    void init() {
        wpref = new int[N][N];
        mpref = new int[N][N];
        mprops = new int[N];
        freew = new boolean[N];
        Arrays.fill(freew, true);
        freem = new boolean[N];
        Arrays.fill(freem, true);
        matches = new int[N];
    }

    /**
     * Read the input from stdin create initial data structures
     * necessary to run the algorithm
     */
    void readInput(){
        Scanner fileReader = new Scanner(System.in);
        N = Integer.parseInt(fileReader.nextLine());
        boolean[] wEncountered = new boolean[N];

        init();
        String[] allNums = getAllNums(fileReader);
        int[][] lines = getCleanLines(allNums);
        
        int i = 0;
        int j = 0;
        while(i < 2 * N){
            int index = lines[i][0] - 1;
            if (!wEncountered[index]){
                wEncountered[index] = true;
                int[] vals = new int[N];
                for(j = 1; j < N + 1; j++){
                    vals[lines[i][j] - 1] = j - 1;
                }
                wpref[index] = vals;
            }
            else {
                for(j = 1; j < N + 1; j++){
                    mpref[index][j - 1] = lines[i][j] - 1;
                }
            }
            i++;
        }
        fileReader.close();
    }

    /**
     * Get the index of the first free m
     * or -1 if no ms are free
     * @return index of m
     */
    int firstFreeM(){
        for (int i = 0; i < N; i++){
            if (freem[i] == true){
                return i;
            }
        }
        return -1;
    }

    /**
     * Run Gale-Shapley algorithm to find stable matching
     */
    void gs(){
        int m = 0;
        while(m != -1){
            m = firstFreeM();
            if (m == -1){
                continue;
            }
            int mNumProposals = mprops[m];
            int preferredIndex = mpref[m][mNumProposals];
            if (preferredIndex == -1){
                continue;
            }
            mprops[m]++;
            if (freew[preferredIndex]){
                matches[preferredIndex] = m;
                freem[m] = false;
                freew[preferredIndex] = false;
            }
            else if (wpref[preferredIndex][m] < wpref[preferredIndex][matches[preferredIndex]]){
                int currentM = matches[preferredIndex];
                matches[preferredIndex] = m;
                freem[m] = false;
                freem[currentM] = true;
            }
        }
    }

    /**
     * Run sequence to solve problem
     */
    public void run(){
        readInput();
        gs();
        for (int i = 0; i < N; i++){
            System.out.println(matches[i] + 1);
        }
    }

    public static void main(String[] args){
        StableMarriage sm = new StableMarriage();
        sm.run();
    }
}