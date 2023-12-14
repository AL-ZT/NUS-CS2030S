/**
 * CS2030S PE2 
 * AY2022/23 Semester 1.
 * 
 * @author Your Student Number
 **/

import java.util.stream.Stream;
import java.util.stream.Collectors;
import java.util.List;
import java.util.ArrayList;

public class Streaming {
  public static Stream<Integer> evenTriangular(int n) {
    return Stream.iterate(1, i -> i+1)
                 .map(j -> (j*(j+1))/2)
                 .filter(k -> k%2 == 0)
                 .limit(n);
  }
  
  public static boolean isPerfect(int n) {
    return Stream.iterate(1, i -> i<n, i -> i+1)
                 .filter(j -> n%j == 0)
                 .reduce(0, (x,y) -> x+y) == n;
  }
  public static List<Integer> allPerfect(int start, int end) {
    return Stream.iterate(start, i -> i<=end, i -> i+1)
                 .filter(n -> Streaming.isPerfect(n))
                 .collect(Collectors.toList());
  }
  
  public static <T> List<T> decode(Stream<Pair<Integer,T>> stream) {
    return stream.flatMap(p -> Stream.generate(() -> p.getSnd())
                                     .limit(p.getFst()))
                 .collect(Collectors.toList());
  }
}