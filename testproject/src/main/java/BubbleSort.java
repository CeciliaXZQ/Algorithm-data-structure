import java.util.LinkedList;
import java.util.Scanner;

/**
 * Created by ceciliaX on 18/11/16.
 */
public class BubbleSort {
    public void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int i, size;
        LinkedList<Integer> a = new LinkedList();
        System.out.print("Input size: ");
        size = scan.nextInt();
        for (i = 0; i < size; i++) {
            System.out.print("Input data: ");
            a.add(scan.nextInt());
        }
        bubble(a);
        System.out.println();

    }

    public static void bubble(LinkedList<Integer> a) {

        boolean swapped = true;
        int R = a.size() - 2;
        while (R >= 0 && swapped == true) {
            swapped = false;
            for (int i = 0; i <= R; i++) {
                if (a.get(i) > a.get(i + 1)) {
                    swapped = true;
                    a.set(i, a.get(i + 1));
                    a.set(i + 1, a.get(i));
                }
            }
            R--;
        }
    }
}
