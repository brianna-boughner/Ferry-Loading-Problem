import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

class Main {
    //global variables
    static int bestK;
    static Integer[] currX;
    static Integer[] bestX;
    static int n;
    static HashMap<Integer,Boolean> visited;
    static Integer[] length;
    static int L;


    //backtrack procedure
    static void BacktrackSolve(int currK, int currS){
        // currK cars have been added; currS space remains at the left side
        if(currK>bestK){
            bestK = currK;
            bestX = currX.clone();
        }
        if(currK<n){
            if(currS>=length[currK] && !visited.getOrDefault(hashcode(currK + 1, currS - length[currK]), false)){//!visited[currK+1][currS-length[currK]]){ //if possible to add car to the left side
                currX[currK] = 1;
                int newS = currS-length[currK];
                BacktrackSolve(currK+1, newS);
                visited.put(hashcode(currK+1, newS), true);
            }
            if(rightSpace(currK, currS) >= length[currK] && !visited.getOrDefault(hashcode(currK+1, currS), false)){// && !visited[currK+1][currS]){
                currX[currK] = 0;
                BacktrackSolve(currK+1,currS);
                visited.put(hashcode(currK+1, currS), true);
            }
        }
    }
    static int rightSpace(int currK, int currS){ // find the space available on the right
        int sum = 0;
        for(int i =0; i<currK; i++){
            sum+=length[i];
        }
        return (L)-(sum-((L)-currS));
    }
    static int hashcode(int k, int s){
        return k*n+s;
    }
    //main function
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int cases = sc.nextInt();
        for (int i = 0; i < cases; i++) {
            bestK = -1;
            L = sc.nextInt() * 100; //length of ferry
            int line = sc.nextInt();
            List<Integer> carLengths = new ArrayList<Integer>();
            n = 0; // number of cars
            while (line != 0) {
                carLengths.add(line);
                line = sc.nextInt();
                n++;
            }
            length = carLengths.toArray(new Integer[n]);

            //initialize arrays
            visited = new HashMap<Integer,Boolean>();
            currX = new Integer[n];
            bestX = new Integer[n];

            BacktrackSolve(0, L);

            System.out.println(bestK);
            for (int j = 0; j < bestK; j++) {
                if (bestX[j] == 0) {
                    System.out.println("port");
                } else if (bestX[j] == 1){
                    System.out.println("starboard");
                }
            }
            if(i<cases-1){
                System.out.println();
            }
        }
    }
}
