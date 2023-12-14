package cs2030s.fp;

public abstract class Transformer<R, P> implements Immutator<R, P> {

  public <N> Transformer<R, N> after(Transformer<P, N> first) {
    
    Transformer<R, P> second = this;

    return new Transformer<R, N>() {
      @Override
      public R invoke(N n) {
        return second.invoke(first.invoke(n));
      }
    };
  }

  public <T> Transformer<T, P> before(Transformer<T, R> second) {
    
    Transformer<R, P> first = this;

    return new Transformer<T, P>() {
      @Override
      public T invoke(P p) {
        return second.invoke(first.invoke(p));
      }
    };
  }

}
