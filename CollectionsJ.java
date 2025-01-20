import java.util.Collection;

public class CollectionsJ {
    public static void dangerousCall(Collection<Integer> l) {
        l.add(1000);
    }
}
