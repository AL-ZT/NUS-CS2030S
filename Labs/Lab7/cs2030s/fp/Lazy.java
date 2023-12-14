package cs2030s.fp;

/**
 * Lazy Class that holds off computation until you call a function to get value.
 * Implements Immutatorable::transform function 
 * to transform T into a different type R.
 * 
 *@param <T> The type of the value stored in Lazy.
 */
public class Lazy<T> implements Immutatorable<T> {

  /** Stores a Constant function that returns type T. */
  private Constant<? extends T> init;

  /** 
   * Protected Constructor that stores Constant function into init.
   * 
   * @param c The Constant function to be lazily run.
   */
  protected Lazy(Constant<? extends T> c) {
    this.init = c;
  }

  /**
   * Static method that instantiates a Lazy Object with
   * given value using the protected Constructor.
   * 
   * @param v The value of type T
   * @param <T> Type of value to be stored in Lazy
   * @return A new Lazy Object of type T
   */
  public static <T> Lazy<T> from(T v) {
    return new Lazy<T>(() -> v);
  }

  /**
   * Static method that takes in a Constant that produces the value
   * when needed and instantiate the Lazy Object.
   * 
   * @param c The Constant function to be lazily run
   * @param <T> Type of value to be stored in Lazy
   * @return A new Lazy Object of type T
   */
  public static <T> Lazy<T> from(Constant<? extends T> c) {
    return new Lazy<T>(c);
  }

  /**
   * Method is called when the value is needed.
   * Compute the value and return.
   * 
   * @return Value of type T
   */
  public T get() {
    return this.init.init();
  } 

  /**
   * Method that returns a new Lazy Object that can chain computations.
   * 
   * @param f An Immutator function that takes value of type T and returns a Lazy Object
   * @param <R> The type of the return value in Lazy
   * @return A Lazy Object of type R
   */
  public <R> Lazy<R> next(Immutator<? extends Lazy<? extends R>, ? super T> f) {
    return Lazy.from(() -> f.invoke(this.get()).get());
  }

  /** String representation of the value. */
  @Override
  public String toString() {
    return this.get().toString();
  }

  /**
   * Transforms from current value of type T to R.
   * 
   * @param f An Immutator function that transform from type T to R
   * @param <R> The type of return value
   * @return A Lazy Object of type R
   */
  @Override
  public <R> Lazy<R> transform(Immutator<? extends R, ? super T> f) {
    return Lazy.from(() -> f.invoke(this.get()));
  }
}
