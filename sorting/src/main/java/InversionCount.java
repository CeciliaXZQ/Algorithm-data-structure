/**
 * Created by ceciliaX on 22/11/16.
 */
public class InversionCount {

    private static Comparable[] aux;  // auxiliary array for merges
     private static boolean less(Comparable v, Comparable w) {
         return v.compareTo(w) < 0;
     }

    public static int countInversions(Comparable[] a) {
        aux = new Comparable[a.length];
        return sort(a, 0, a.length-1);
    }

        private static int sort(Comparable[] a, int lo, int hi)
        {  // Sort a[lo..hi].
            if (hi <= lo) return 0;
            int mid = lo + (hi - lo)/2;
            return sort (a, lo, mid) + sort (a, mid+1, hi) + merge (a, lo, mid, hi);
        }

   private static int merge(Comparable[] a, int lo, int mid, int hi)
    {  // Merge a[lo..mid] with a[mid+1..hi].
        int count=0;
        int i = lo, j = mid+1;
        for (int k = lo; k <= hi; k++){  // Copy a[lo..hi] to aux[lo..hi].
            aux[k] = a[k];
        }
        for (int k = lo; k <= hi; k++) {  // Merge back to a[lo..hi].
            if (i > mid) {
                a[k] = aux[j++];
            } else if (j > hi) {
                a[k] = aux[i++];
            } else if (less(aux[j], aux[i])) {
                if(mid == i){
                    count=count+1;
                    a[k] = aux[j++];
                }
                else {
                    count = count + ((mid+1)-i);
                    a[k] = aux[j++];
                }
            } else {
                a[k] = aux[i++];
            }
        }
        return count;
    }
  }



