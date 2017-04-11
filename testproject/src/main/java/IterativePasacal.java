import java.util.Scanner;

/**
 * Created by ceciliaX on 06/11/16.
 */




public class IterativePasacal extends ErrorPascal implements Pascal{


    public int binom(int n, int k) {
        binomerr(n, k);
        if (n == k || k == 0) {
            Driver.Stored[n][k] = 1;
            return 1;
        }
        Driver.Stored[0][0] = Driver.Stored[1][0]=1;
        for (int i = 1; i <= n; i++) {
            Driver.Stored[i][0]= Driver.Stored[i][i]=1;
            for (int j = 1; j <= k; j++) {
                Driver.Stored[i][j] = Driver.Stored[i - 1][j - 1] + Driver.Stored[i - 1][j];
                Driver.Stored[n][k]= Driver.Stored[i][j];
            }
        }
        return Driver.Stored[n][k];
    }

    public void printPascal(int n){
        printPascalerr(n);
        int[][] triangle = new int[n+1][n+1];

        if (!Driver.bol) {
        for (int i = 0; i <= n; i++) {
            for (int j = 0; j <= i; j++) {
                triangle[i][j]=binom(i, j);
                    System.out.print(triangle[i][j] + " ");
                    if(i == j){
                        System.out.println();
                    }
                }
            }
        }

        if(Driver.bol){
            for (int i = n; i >= 0; i--) {
                for (int j = 0; j <= i; j++) {
                    triangle[i][j] = binom(i, j);
                    System.out.print(triangle[i][j] + " ");
                    if (i == j) {
                        System.out.println(" ");
                    }
                }
            }
        }

        }

}

