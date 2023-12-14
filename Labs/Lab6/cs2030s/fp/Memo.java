package cs2030s.fp;

/**
 * Memo Class that is able to lazily compute and cache result of computation
 * such that it does not have to be computed again.
 * CS2030S Lab6
 * AY22/23 Semester 1
 * 
 * @param <T> The type of the value to be stored in Memo
 */
public class Memo<T> extends Lazy<T> {

  /** 
   * Value of computation that might be initialised or uninitialised,
   * represented by Success or Failure.
   */
  private Actually<T> value;

  /**
   * Private Memo Constructor that initialises the Constant function in Lazy
   * and value.
   *
   * @param c Constant function to be lazily run.
   * @param a Value to be stored in <code>value</code>, either the computed value or no value
   */
  private Memo(Constant<? extends T> c, Actually<T> a) {
    super(c);
    this.value = a;
  }
  
  /**
   * Static method that instantiates a Memo Object with
   * given value using the protected Constructor.
   * 
   * @param v The value of type T
   * @param <T> Type of value to be stored in Memo
   * @return A new Memo Object of type T
   */
  public static <T> Memo<T> from(T v) {
    return new Memo<T>(() -> v, Actually.ok(v));
  }

  /**
   * Static method that takes in a Constant that produces the value
   * when needed and instantiate the Memo Object.
   * 
   * @param c The Constant function to be lazily run
   * @param <T> Type of value to be stored in Lazy
   * @return A new Memo Object of type T
   */
  public static <T> Memo<T> from(Constant<? extends T> c) {
    return new Memo<T>(c, Actually.err(new Exception()));
  }

  /**
   * Method is called when the value is needed.
   * Compute the value and return. Never compute again after.
   * 
   * @return Value of type T
   */
  @Override
  public T get() {
    T val = this.value.except(() -> super.get());
    this.value = Actually.ok(val);
    return val;
  }
  
  /** String representation of the value.  */
  @Override
  public String toString() {
    return this.value.transform(p -> String.valueOf(p)).except(() -> "?");
  }

  /**
   * Transforms from current value of type T to R.
   * 
   * @param f An Immutator function that transform from type T to R
   * @param <R> The type of return value
   * @return A Lazy Object of type R
   */
  @Override
  public <R> Memo<R> transform(Immutator<? extends R, ? super T> f) {
    return Memo.from(() -> f.invoke(this.get()));
  }

  /**
   * Method that returns a new Memo Object that chains computations.
   * 
   * @param f An Immutator function that takes value of type T and returns a Lazy Object
   * @param <R> The type of the return value in Lazy
   * @return A Lazy Object of type R
   */
  @Override
  public <R> Memo<R> next(Immutator<? extends Lazy<? extends R>, ? super T> f) {
    return Memo.from(() -> f.invoke(this.get()).get());
  }

  /**
   * Combines values of current Memo with another, both can be of different types.
   * Computation of the values is provided with a Combiner Object.
   *
   * @param <R> The type of the return value after computation
   * @param <S> The type of the second Memo, not the target of invocation
   * @param m The Memo Object of type S
   * @param c The Combiner Object that combines values of type T and S to R
   * @return A new Memo Object of type R after computation
   */
  public <R, S> Memo<R> combine(Memo<? extends S> m, 
      Combiner<? extends R, ? super T, ? super S> c) {
    return Memo.from(() -> c.combine(this.get(), m.get()));
  }
}
