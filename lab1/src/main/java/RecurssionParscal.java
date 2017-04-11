/**
 * Created by ceciliaX on 04/11/16.
 */
public class RecurssionParscal {
    public static void main(String args[]) {
        int a = binom(2,3);
        System.out.println(a);
    }

    public static int binom(int n, int k) {
        if (k == n || k == 0) {
            return 1;
        } else {
            return binom(n - 1, k - 1) + binom(n - 1, k);
        }
    }

}
