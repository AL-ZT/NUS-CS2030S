import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Streaming {

  public static <T> List<Pair<Integer, T>> encode(Stream<T> stream) {

    return stream.collect(ArrayList<Pair<Integer, T>>::new, (list, value) -> {
      if (!list.isEmpty()) {
        Pair<Integer, T> p = list.get(list.size() - 1);
        if (p.getSnd().equals(value)) {
          p.setFst(p.getFst() + 1);
        } else {
          list.add(new Pair<>(1, value));
        }
      } else {
        list.add(new Pair<>(1, value));
      }
    }, (list1, list2) -> {
      Pair<Integer, T> p = list1.get(list1.size() - 1);
      if (p.getSnd().equals(list2.get(0).getSnd())) {
        p.setFst(p.getFst() + list2.remove(0).getFst());
      }
      list1.addAll(list2);
    });

  }
}
