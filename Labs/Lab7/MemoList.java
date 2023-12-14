import cs2030s.fp.Combiner;
import cs2030s.fp.Immutator;
import cs2030s.fp.Memo;
import java.util.ArrayList;
import java.util.List;

/**
 * A wrapper around a lazily evaluated and memoized
 * list that can be generated with a lambda expression.
 *
 * @author Lim Zheng Ting
 * @version CS2030S AY 22/23 Sem 1
 */
class MemoList<T> {
  /** The wrapped java.util.List object */
  private List<Memo<T>> memoList;

  /** 
   * A private constructor to initialize the list to the given one. 
   *
   * @param list The given java.util.List to wrap around.
   */
  private MemoList(List<Memo<T>> list) {
    this.memoList = list;
  }

  /** 
   * Generate the content of the list.  Given x and a lambda f, 
   * generate the list of n elements as [x, f(x), f(f(x)), f(f(f(x))), 
   * ... ]
   *
   * @param <T> The type of the elements in the list.
   * @param n The number of elements.
   * @param seed The first element.
   * @param f The immutator function on the elements.
   * @return The created list.
   */
  public static <T> MemoList<T> generate(int n, T seed, Immutator<? extends T, ? super T> f) {
    MemoList<T> list = new MemoList<>(new ArrayList<>());
    Memo<T> curr = Memo.from(seed);
    for (int i = 0; i < n; i++) {
      list.memoList.add(curr);
      curr = curr.transform(f);
    }
    return list;
  }

  /** 
   * Overloaded generate() that generate the content of the list by combining the last two elements.
   * let fst = x and snd = y. generate the list of n elements as [x, y, f(x, y), f(y, f(x, y)), 
   * f(f(x, y), f(y, f(x, y))), ... ]
   *
   * @param <T> The type of the elements in the list.
   * @param n The number of elements.
   * @param fst The first element.
   * @param snd The second element.
   * @param c The Combiner function on the last two elements.
   * @return The created list.
   */
  public static <T> MemoList<T> generate(int n, T fst, T snd, 
      Combiner<? extends T, ? super T, ? super T> c) {
    Memo<T> first = Memo.from(fst);
    Memo<T> second = Memo.from(snd);
    MemoList<T> list = new MemoList<>(
                         new ArrayList<>(List.of(
                           first, second
                         ))
                       );
    for (int i = 0; i < n - 2; i++) {
      Memo<T> val = first.combine(second, c);
      list.memoList.add(val);
      first = second;
      second = val;
    }
    return list;
  }

  /**
   * lazily applies given Immutator function on each element in the list.
   * Returns a new MemoList of type R.
   *
   * @param <R> Return type of value
   * @param f Immutator function to apply to the elements in the list
   * @return A new MemoList of type R
   */
  public <R> MemoList<R> map(Immutator<? extends R, ? super T> f) {
    MemoList<R> list = new MemoList<R>(new ArrayList<>());
    for (Memo<T> item : this.memoList) {
      list.memoList.add(Memo.from(() -> f.invoke(item.get())));
    }
    return list;
  }

  /**
   * lazily applies given Immutator function on each element on the list.
   * Immutator function returns a MemoList of type R, flatmap flattens any nested list.
   *
   * @param <R> Return type of value
   * @param f Immutator function to apply to the elements in the list
   * @return A new MemoList of type R
   */
  public <R> MemoList<R> flatMap(Immutator<? extends MemoList<R>, ? super T> f) {
    MemoList<R> list = new MemoList<R>(new ArrayList<>());
    for (Memo<T> currItem : this.memoList) {
      MemoList<R> newList = f.invoke(currItem.get());
      for (Memo<R> newItem : newList.memoList) {
        list.memoList.add(newItem);
      }
    }
    return list;
  }

  /** 
   * Return the element at index i of the list.  
   *
   * @param i The index of the element to retrieved (0 for the 1st element).
   * @return The element at index i.
   */
  public T get(int i) {
    return this.memoList.get(i).get();
  }

  /** 
   * Find the index of a given element.
   *
   * @param v The value of the element to look for.
   * @return The index of the element in the list.  -1 is element is not in the list.
   */
  public int indexOf(T v) {
    return this.memoList.indexOf(Memo.from(v));
  }

  /** 
   * Return the string representation of the list.
   *
   * @return The string representation of the list.
   */
  @Override
  public String toString() {
    return this.memoList.toString();
  }
}
