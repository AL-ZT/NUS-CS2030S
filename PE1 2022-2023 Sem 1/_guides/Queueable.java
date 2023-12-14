public interface Queueable<T> {
  Queueable<T> enq(T elem);
  Queueable<T> deq();
  T peek();
  int size();
}