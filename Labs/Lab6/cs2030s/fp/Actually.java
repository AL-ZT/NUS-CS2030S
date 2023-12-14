package cs2030s.fp;

/**
 * A common abstraction that is a wrapper around the idea that we think maybe the function call is
 *     successful but actually a Failure (throws an Exception).
 *     Both Success and Failure are Immutable. 
 *
 * @param <T> The type of value in Actually
 */
public abstract class Actually<T> implements Immutatorable<T>, Actionable<T> {

  /**
   * Static method that instantiates a successful Actually Object.
   *
   * @param <T> The type of value in Actually
   * @param res The value of type T that is put into the Object
   * @return An instance of Actually of type T
   */
  public static <T> Actually<T> ok(T res) {
    return new Success<T>(res);
  }

  /**
   * Static method that instantiates an unsuccessful Actually Object.
   *
   * @param <T> The type of value in Actually
   * @param exception The exception to pass into the Failure Object
   * @return An instance of Actually of type T
   */
  public static <T> Actually<T> err(Exception exception) {
    // It is okay to do casting as the methods in Failure do not use T.
    @SuppressWarnings("unchecked")
    Actually<T> failure = (Actually<T>) new Failure(exception);
    return failure;
  }

  /**
   * Returns the value contained inside if successful, otherwise throws the exception stored.
   * 
   * @exception Exception Throws a Checked Exception
   * @return value of type T when successful
   */
  public abstract T unwrap() throws Exception;

  /**
   * Returns the value contained inside if successful,
   *     otherwise return value from the Constant function provided in the parameter.
   * 
   * @param c Constant function to execute
   * @param <U> The type of return value from Constant function
   * @return value of type T
   */
  public abstract <U extends T> T except(Constant<? extends U> c);

  /**
   * Executes an action when successful, 
   *     otherwise do nothing.
   *
   * @param a Action to perform on the value in Actually
   */
  public abstract void finish(Action<? super T> a);

  /**
   * Returns the value contained inside if successful,
   *   otherwise return a given value that is a subtype of T.
   *
   * @param r A value that is a subtype of T
   * @param <R> The type of return value
   * @return A value of type T
   */
  public abstract <R extends T> T unless(R r);

  /**
   * Automatically wraps result in Actually using Immutator if successful. 
   * Otherwise return and propagate the Failure.
   * 
   * @param imm Immutator to transform from type T to Actually of type R
   * @param <R> the return type parameter of Actually of type R
   * @return An Actually of type R
   */
  public abstract <R> Actually<R> next(Immutator<? extends Actually<? extends R>, ? super T> imm);

  /**
   * Transforms from value of type T to R if successful. Otherwise return and propagate the Failure.
   * 
   * @param imm Immutator to transform from value of type T to R
   * @param <R> The type of return value
   * @return An Actually of type R
   */
  public abstract <R> Actually<R> transform(Immutator<? extends R, ? super T> imm);


  /**
   * Represents an abstraction of a successful computation of function calls.
   *
   * @param <T> The type of value in Actually
   */
  static class Success<T> extends Actually<T> {
    
    /** Stores a value of type T. */
    private final T value;

    /**
     * Private Constructor that stores the value of type T inside the Object.
     *
     * @param value A value of type T
     */
    private Success(T value) {
      this.value = value;
    }

    @Override
    public String toString() {
      return "<" + value + ">";
    }

    @Override
    public boolean equals(Object obj) {
      if (obj instanceof Success<?>) {
        Success<?> success = (Success<?>) obj;
        if (this.value == success.value) {
          return true;
        }
        return false;
      }
      return false;
    }

    @Override
    public T unwrap() {
      return this.value;
    }

    @Override
    public <U extends T> T except(Constant<? extends U> c) {
      return unwrap();
    }

    @Override
    public void finish(Action<? super T> a) {
      a.call(this.value);
    }

    @Override
    public <R extends T> T unless(R r) {
      return unwrap();
    }

    @Override 
    public <R> Actually<R> transform(Immutator<? extends R, ? super T> imm) {
      try {
        return Actually.<R>ok(imm.invoke(this.value)); 
      } catch (Exception e) {
        return Actually.<R>err(e);
      }
    }

    @Override
    public void act(Action<? super T> action) {
      action.call(this.value);
    }

    @Override
    public <R> Actually<R> next(Immutator<? extends Actually<? extends R>, ? super T> imm) {
      try {
        @SuppressWarnings("unchecked")
        Actually<R> a = (Actually<R>) imm.invoke(this.value);
        return a;
      } catch (Exception e) {
        return Actually.<R>err(e);
      }
    }

  }


  /**
   * Represents an abstraction of an unsuccessful computation of function calls.
   */
  static class Failure extends Actually<Object> {

    /** Stores an exception inside. */
    private final Exception exc;

    /** 
     * Private constructor that stores the exception inside the Object.
     *
     * @param e An exception to be stored to throw when needed.
     */
    private Failure(Exception e) {
      this.exc = e;
    }

    @Override
    public String toString() {
      return "[" + this.exc.getClass().getName() + "] " + this.exc.getMessage();
    }

    @Override
    public boolean equals(Object obj) {
      if (obj == this) {
        return true;
      }
      if (obj instanceof Failure) {
        Failure f = (Failure) obj;
        if (this.exc.getMessage() == null || f.exc.getMessage() == null) {
          return false;
        } else {
          if (this.exc.getMessage() == f.exc.getMessage()) {
            return true;
          }
        }
      }
      return false;
    }

    @Override
    public Object unwrap() throws Exception {
      throw this.exc;
    }

    @Override
    public <U> U except(Constant<? extends U> c) {
      return c.init();
    }

    @Override
    public void finish(Action<? super Object> a) {
      
    }

    @Override
    public void act(Action<? super Object> action) {

    }

    @Override
    public <R> R unless(R r) {
      return r;
    }

    @Override 
    public <R> Actually<R> transform(Immutator<? extends R, ? super Object> imm) {
      return Actually.<R>err(this.exc);
    }

    @Override
    public <R> Actually<R> next(Immutator<? extends Actually<? extends R>, ? super Object> imm) {
      return Actually.err(this.exc);
    }
  }

}
