import se.kth.id1020.Driver;
import se.kth.id1020.TinySearchEngineBase;

/**
 * Created by ceciliaX on 01/12/16.
 */
public class main {
    public static void main(String[] args) throws Exception {
        TinySearchEngineBase searchEngine = new MySearchEngine();
        Driver.run(searchEngine);
        System.out.println();

    }
}
