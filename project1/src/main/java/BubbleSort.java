import java.util.Comparator;
import java.util.List;

/**
 * Created by ceciliaX on 01/12/16.
 */
public class BubbleSort <T> {
    Comparator<T> comparator;

    public BubbleSort(Comparator<T> comparator) {
        this.comparator = comparator;
    }

    public void bubblesort(List<T> list) {
        int R = list.size() - 2;
        boolean swapped = true;

        while (R >= 0 && swapped) {
            swapped = false;

            for (int i = 0; i <= R; i += 1) {
                int compareResult = this.comparator.compare(list.get(i), list.get(i + 1));

                if (compareResult > 0) {
                 //   System.out.println(" in bubble sort");
                    T temp = list.get(i);
                    list.set(i, list.get(i + 1));
                    list.set(i + 1, temp);
                    swapped = true;
                }
            }
            R -= 1;
        }
    }
}






       /* boolean swapped = true;
        int R = list.size() - 2;

        if(!reverse) {
            while (R >= 0 && swapped) {
                //    n1 = this.getHead();
                swapped = false;


                for (int i = 0; i <= R; i++) {
                    //n2 = n1.getNextNode();
                    T list1 = list.get(i);
                    T list2 = list.get(i + 1);
                    int compareResult = this.comparator.compare(list1, list2);

                    if (compareResult > 0) {
                        T temp = list.get(i);
                        list.set(i, list.get(i + 1));
                        list.set(i + 1, temp);
                        swapped = true;
                    }
                }
                R--;
            }
        }

        else{
            while (R >= 0 && swapped) {
                //    n1 = this.getHead();
                swapped = false;


                for (int i = 0; i <= R; i++) {
                    //n2 = n1.getNextNode();
                    T list1 = list.get(i);
                    T list2 = list.get(i + 1);
                    int compareResult = this.comparator.compare(list1, list2);

                    if (compareResult <0) {
                        T temp = list.get(i);
                        list.set(i, list.get(i + 1));
                        list.set(i + 1, temp);
                    }
                }
                R--;
            }

        }
    }*/

