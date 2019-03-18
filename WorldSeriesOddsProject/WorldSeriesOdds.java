package WorldSeriesOddsProject;

/**
 * @author Han Zhang
 * 95771 HW2 Part 2 - Dynamic Programming - WorldSeriesOdds
 */
public class WorldSeriesOdds {
    double[][] res = null;
    public static void main(String[] args) {
        WorldSeriesOdds w = new WorldSeriesOdds();
        int i = 50;
        int j = 40;
        long start = System.currentTimeMillis();
        System.out.println("RE " + w.computeProbRecursive(i, j));
        long end = System.currentTimeMillis();
        System.out.println(0.001 * (end - start) + " seconds");

        long start2 = System.currentTimeMillis();
        System.out.println("DY " + w.computeProbDynamicProgramming(i, j));
        long end2 = System.currentTimeMillis();
        System.out.println(0.001 * (end2 - start2) + " seconds");


    }

    //P(i,j) = 1  if i = 0 and j > 0
    //       = 0  if i > 0 and j = 0
    //       = (P(i-1,j) + P(i,j-1))/2  if i > 0 and j > 0

    public double computeProbRecursive(int i, int j){
        return computeProbRecursiveHelper(i, j);
    }


    private double computeProbRecursiveHelper(int i, int j){
        if(i == 0 && j > 0){
            return 1.0;
        }
        if(i > 0 && j == 0){
            return 0.0;
        }
        return 0.5 * (computeProbRecursiveHelper(i - 1, j) + computeProbRecursiveHelper(i, j - 1));
    }

    public double computeProbDynamicProgramming(int i, int j){
        int maxLen = Math.max(i + 1, j + 1);
        if(res == null){
            //if res hasn't been initialized, initialize res
            res = new double[maxLen][maxLen];
            //what to put at 00??????????????????????????????????????
            res[0][0] = 0.0;
            //set up all the base cases
            for(int b = 1; b < maxLen; b++){
                res[0][b] = 1.0;
                res[b][0] = 0.0;
            }
            for(int a = 1; a < maxLen; a++){
                for(int b = a; b < maxLen; b++){
                    res[a][b] = 0.5 * (res[a - 1][b] + res[a][b - 1]);
                    res[b][a] = 1 - res[a][b];
                }
            }

        } else if (maxLen > res.length){
            //if new 2-dimension array is bigger than the old one
            //create the new one and copy the one one
            double[][] tmp = res;
            res = new double[maxLen][maxLen];
            for(int a = 0; a < tmp.length; a++){
                res[a][a] = tmp[a][a];
            }
            for(int a = tmp.length; a < maxLen; a++){
                for(int b = a; b < maxLen; b++){
                    res[a][b] = 0.5 * (res[a - 1][b] + res[a][b - 1]);
                    res[b][a] = 1 - res[a][b];
                }
            }
        } else {
            //if the one to compute is small than the old one
            //use the old value
        }
        return 0.5 * (res[i][j-1] + res[i - 1][j]);
    }
}
