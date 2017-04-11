import java.util.Scanner;

/**
 * Created by ceciliaX on 04/11/16.
 */



public class RecursivePascal extends ErrorPascal implements Pascal{


    public int binom(int n, int k) {
        binomerr(n,k);
        if (k == n || k == 0) {
            return 1;
        } else {
               if(Driver.Stored[n][k] != 0) {
                 return Driver.Stored[n][k];
               }
            int a;
            a = binom(n - 1, k - 1) + binom(n - 1, k);
            Driver.Stored[n][k]=a;
            return a;
               }

    }


    public void printPascal(int n) {
        printPascalerr(n);
        if (Driver.bol) {
            if (n == 0)
                System.out.print("1");
            else {
                for (int k = 0; k <= n; k++) {
                    System.out.print(binom(n, k) + " ");
                }
                System.out.println();
                printPascal(n - 1);
            }
        }
        else
        {
            if (n == 0)
                System.out.println("1");
            else {
                printPascal(n - 1);
                for (int k = 0; k <= n; k++) {
                    System.out.print(binom(n, k) + " ");
                }
                System.out.println();
            }
        }

    }
    }


