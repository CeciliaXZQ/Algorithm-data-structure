/**
 * Created by ceciliaX on 07/11/16.
 */
public abstract class ErrorPascal {

    public void printPascalerr(int n){
        if(n < 0) {
            System.out.println("Invalid n, n can't be less or equal to 0!");
        }
    }

    public int binomerr(int n, int k){
        if(k < 0 || k > n){
            System.out.println("Invalid k and/or n!");
        }
        return 0;

    }
}

