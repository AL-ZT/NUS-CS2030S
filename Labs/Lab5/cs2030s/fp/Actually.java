package cs2030s.fp;

public abstract class Actually<T> implements Immutatorable<T>, Actionable<T> {

  public static <T> Actually<T> ok(T res) {
    return new Success<T>(res);
  }

  public static <T> Actually<T> err(Exception exception) {
    // It is okay to do casting as the methods in Failure do not use T.
    @SuppressWarnings("unchecked")
    Actually<T> failure = (Actually<T>) new Failure(exception);
    return failure;
  }

  public abstract T unwrap() throws Exception;

  public abstract <U extends T> T except(Constant<U> c);

  public abstract void finish(Action<? super T> a);

  public abstract <R extends T> T unless(R r);

  public abstract <R> Actually<R> next(Immutator<Actually<R>, ? super T> imm);



  static class Success<T> extends Actually<T> {
    
    private final T value;

    private Success(T value) {
      this.value = value;
    }

    @Override
    public String toString() {
      return "<" + value + ">";
    }

    public boolean equals(Success<?> success) {
      if (this.value == success.value) {
        return true;
      }
      return false;
    }

    @Override
    public T unwrap() {
      return this.value;
    }

    @Override
    public <U extends T> T except(Constant<U> c) {
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
    public <R> Actually<R> next(Immutator<Actually<R>, ? super T> imm) {
      try {
        return imm.invoke(this.value);
      } catch (Exception e) {
        return Actually.<R>err(e);
      }
    }

  }



  static class Failure extends Actually<Object> {

    private final Exception exc;

    public Failure(Exception e) {
      this.exc = e;
    }

    @Override
    public String toString() {
      return "[" + this.exc.getClass().getName() + "] " + this.exc.getMessage();
    }

    public boolean equals(Failure f) {
      if (this.exc.getMessage() == null || f.exc.getMessage() == null) {
        return false;
      } else {
        if (this.exc.getMessage() == f.exc.getMessage()) {
          return true;
        }
      }
      return false;
    }

    @Override
    public Exception unwrap() throws Exception {
      throw exc;
    }

    @Override
    public <U> U except(Constant<U> c) {
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
      return Actually.<R>err(exc);
    }

    @Override
    public <R> Actually<R> next(Immutator<Actually<R>, ? super Object> imm) {
      return Actually.<R>err(exc);
    }
  }

  @Override
  public boolean equals(Object obj) {
    if (this instanceof Success<?> && obj instanceof Success<?>) {
      Success<?> first = (Success<?>) this;
      Success<?> s = (Success<?>) obj;
      return first.equals(s);
    } else {
      if (this instanceof Failure && obj instanceof Failure) {
        Failure first = (Failure) this;
        Failure f = (Failure) obj;
        return first.equals(f);
      }
      return false;
    }
  }

}
