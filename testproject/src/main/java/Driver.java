import edu.princeton.cs.introcs.StdIn;
import java.util.Scanner;

/**
 * Created by ceciliaX on 06/11/16.
 */
public class Driver {
    public static int[][] Stored;
    public static boolean bol = false;
    public static int temp;

    public static void main(String args[]) {
        System.out.print("Please enter the value of n: ");
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        temp = n;
        Stored = new int[n+1][n+1];

        System.out.println("Do you want to print the triangle upside down? If yes, press 1; else press 0");
        if(StdIn.readInt() == 1){
            bol = true;
        }


        System.out.println("recursion(1) or iteration(0)?");
        int in2 = StdIn.readInt();
        boolean swit = true;
        if(in2 == 0){
            swit = false;
        }

        if(swit){
            RecursivePascal cho = new RecursivePascal();
            cho.printPascal(n);
            System.out.println("Recursion!");

        }

        if (!swit){
            IterativePasacal cho2 = new IterativePasacal();
            cho2.printPascal(n);
            System.out.println("Iteration!");
        }
    }

}