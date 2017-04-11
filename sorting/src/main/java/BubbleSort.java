
import java.util.Scanner;

/**
 * Created by ceciliaX on 18/11/16.
 */
public class BubbleSort {
    public static void main(String[] args) {
        Linked a = new Linked();
        Scanner scan = new Scanner(System.in);
        int i, size;
        System.out.print("Input size: ");
        size = scan.nextInt();
        for (i = 0; i < size; i++) {
            System.out.print("Input data: ");
            int number = scan.nextInt();
            a.add(number);
        }

        Comparable[] myArray = a.toArray();
         a.bubblesort();
        a.printList();
        System.out.println("InversionCount: "+ InversionCount.countInversions(myArray));
    }
}
