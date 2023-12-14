package cs2030s.fp;

import java.util.ArrayList;
import java.util.List;

/**
 * Recursive InfiniteList that may contain a value of type T, encapsulated in Actually class.
 *
 *<p>Value represented either with an Actually.Success or Actually.Failure,
 * negating NullPointerException.
 *
 *<p>Computed values will be Memo-ized so that there will not be any future computations.
 */
public class InfiniteList<T> {

  /** the head that may contain the computed value. */
  private Memo<Actually<T>> head;

  /** the tail that contains another InfiniteList. */
  private Memo<InfiniteList<T>> tail;
  
  /**
   * Private constructor to instantiate new InfiniteList.
   *
   * @param head The head that may contain the computed value.
   * @param tail The tail that may contain the computed value.
   */
  private InfiniteList(Memo<Actually<T>> head, Memo<InfiniteList<T>> tail) {
    this.head = head;
    this.tail = tail;
  }
  // You may add other private constructor but it's not necessary.

  /** Single instance representing the END of a finite "InfiniteList". */
  private static final InfiniteList<Object> END = new End();

  /**
   * Returns the static InfiniteList.END to mark the end of a truncated InfiniteList.
   * Safely supresses the type-casting.
   *
   * @param <T> Type to match existing InfiniteList. Will be inferred so it is not necessary.
   * @return The InfiniteList.END.
   */
  public static <T> InfiniteList<T> end() {
    @SuppressWarnings("unchecked")
    InfiniteList<T> end = (InfiniteList<T>) InfiniteList.END;
    return end;
  }
  
  /**
   * generates an InfiniteList containing only a single value supplied by the Constant function.
   *
   * @param prod A Constant function that produces elements of type T.
   * @param <T> The return type for the InfiniteList.
   * @return A new InfiniteList of type T.
   */
  public static <T> InfiniteList<T> generate(Constant<? extends T> prod) {
    return new InfiniteList<>(
      Memo.from(() -> Actually.ok(prod.init())),
      Memo.from(() -> generate(prod))
    );
  }

  /**
   * Generates an InfiniteList given a function f and seed x to produce
   * [x [f(x) [f(f(x)) [...]]]].
   *
   * @param seed initial starting value of the list.
   * @param func Immutator function that returns the same type of value.
   * @param <T> return type of value.
   * @return a new InfiniteList of type T.
   */
  public static <T> InfiniteList<T> iterate(T seed, Immutator<? extends T, ? super T> func) {
    return new InfiniteList<>(
      Memo.from(Actually.ok(seed)),
      Memo.from(() -> iterate(func.invoke(seed), func))
    );
  }
  
  /**
   * Finds the first non-null of head field recursively and returns this value.
   *
   * @return a value of type T.
   */
  public T head() {
    return this.head.get().except(
      () -> this.tail.get().head()
    );
  }
  
  /**
   * Finds the first non-null of head field recursively and returns the corresponding tail value.
   *
   * @return The corresponding InfiniteList.
   */
  public InfiniteList<T> tail() {
    Actually<InfiniteList<T>> r = this.head.get().transform(
        x -> this.tail.get()
    );
    return r.except(() -> this.tail.get().tail());
  }
  
  /**
   * Lazily applies the given Immutator function to each element in the list of type T and
   * returns the resulting InfiniteList.
   *
   * @param f Immutator function to apply to every element.
   * @param <R> The return type after invoking the function.
   * @return a new InfiniteList of type R.
   */
  public <R> InfiniteList<R> map(Immutator<? extends R, ? super T> f) {
    return new InfiniteList<R>(
      Memo.from(() -> this.head.get().transform(f)),
      Memo.from(() -> this.tail.get().map(f))
    );
  }
  
  /**
   * Filters out elements in the list that fail a given Immutator function.
   * It will mark any filtered (i.e. removed or missing) element as Actually.err()
   * instead of null. The resulting (lazily) filtered InfiniteList is returned.
   *
   * @param pred Predicate function that checks if the condition is met in each element.
   * @return a new InfiniteList of type T.
   */
  public InfiniteList<T> filter(Immutator<Boolean, ? super T> pred) {
    return new InfiniteList<>(
      Memo.from(() -> this.head.get().check(pred)),
      Memo.from(() -> this.tail.get().filter(pred))
    );
  }
  
  /**
   * Creates new InfiniteList such that the element at index n is End.
   * Ignores elements that have been filtered out by 'filter' method.
   *
   * @param n the index of which the the InfiniteList will be truncated up to.
   * @return a new InfiniteList of type T.
   */ 
  public InfiniteList<T> limit(long n) {
    if (n > 0) {
      return new InfiniteList<>(
        this.head,
        Memo.from(() -> this.head.get()
          .transform(x -> this.tail.get().limit(n - 1))
          .except(() -> this.tail.get().limit(n))
        )
      );
    }
    return InfiniteList.end();
  }
  
  /**
   * Takes an Immutator function and truncates the list as soon as it finds
   * an element that evaluates the condition to false. Ignores elements 
   * that have been filtered out by 'filter' method.
   * 
   * @param pred Predicate function that evaluates if condition is met.
   * @return a new truncated InfiniteList of type T.
   */
  public InfiniteList<T> takeWhile(Immutator<Boolean, ? super T> pred) {
    Memo<Actually<T>> head = Memo.from(() -> Actually.ok(this.head()).check(pred)); 
    return new InfiniteList<>(
      head,
      Memo.from(() -> head.get()
        .transform(x -> this.tail().takeWhile(pred))
        .unless(InfiniteList.end())
      )
    );
  }
  
  /**
   * Collects the elements in InfiniteList into a java.util.List.
   * 
   * @return a new List of type T.
   */
  public List<T> toList() {
    return this.reduce(new ArrayList<>(), (arr, element) -> {
      arr.add(element);
      return arr;
    });
  }

  /**
   * Performs fold-left reduction operation similar to java.util.Stream.
   *
   * @param id The identity of the method.
   * @param acc Combiner function that accumulates the elements in the list with the identity.
   * @param <U> return type of the Combined value.
   * @return Accumulated value.
   */
  public <U> U reduce(U id, Combiner<U, U, ? super T> acc) {
    return this.head.get()
             .transform(x -> this.tail.get()
               .reduce(acc.combine(id, this.head()), acc))
             .except(() -> this.tail.get().reduce(id, acc));
  }

  /**
   * Counts the total number of elements in a finite list.
   *
   * @return The total, in long.
   */
  public long count() {
    return this.reduce(0L, (x, y) -> x + 1L);
  }

  @Override
  public String toString() {
    return "[" + this.head + " " + this.tail + "]";
  }
  
  /**
   * Checks if the list is finite. (i.e. instance of END)
   *
   *@return true or false.
   */
  public boolean isEnd() {
    return false;
  }
  
  
  /**
   * A nested class that represents a list that contains nothing and
   * is used to mark the end of the list. (i.e. Finite list)
   */
  private static class End extends InfiniteList<Object> {
    
    /** Private constructor for End. Instantiates an empty InfiniteList. */
    private End() {
      super(null, null);
    }

    @Override
    public Exception head() {
      throw new java.util.NoSuchElementException();
    }

    @Override
    public InfiniteList<Object> tail() {
      throw new java.util.NoSuchElementException();
    }

    @Override
    public <R> InfiniteList<R> map(Immutator<? extends R, ? super Object> f) {
      return InfiniteList.end();
    }

    @Override
    public InfiniteList<Object> filter(Immutator<Boolean, ? super Object> f) {
      return InfiniteList.end();
    }

    @Override
    public InfiniteList<Object> takeWhile(Immutator<Boolean, ? super Object> pred) {
      return InfiniteList.end();
    }

    @Override
    public InfiniteList<Object> limit(long n) {
      return InfiniteList.end();
    }

    @Override
    public <U> U reduce(U id, Combiner<U, U, ? super Object> acc) {
      return id;
    }

    @Override
    public List<Object> toList() {
      return new ArrayList<>();
    }

    @Override
    public String toString() {
      return "-";
    }

    @Override
    public boolean isEnd() {
      return true;
    }
  }
}
