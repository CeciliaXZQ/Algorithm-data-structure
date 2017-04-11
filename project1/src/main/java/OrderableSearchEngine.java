/**
 * Created by ceciliaX on 04/12/16.
 */
import java.util.List;

public interface OrderableSearchEngine {
        <T> List<T> search(String[] words, String orderBy, String direction);
    }

