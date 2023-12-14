public class Queues {
  public static <T> Queueable<? super Pair<? super T, ? super T>> zip(Queueable<? extends T> q1, Queueable<? extends T> q2) {
    Queueable<? super Pair<? super T, ? super T>> que = new EndQueue<>();
    while (q1.size() > 0 && q2.size() > 0) {
      que = que.enq(new Pair<T,T>(q1.peek(), q2.peek()));
      q1 = q1.deq();
      q2 = q2.deq();
    }
    return que;
  }
  
  public static <T> Queueable<? super T> merge(Queueable<? extends T> q1, Queueable<? extends T> q2) {
    Queueable<T> que = new EndQueue<>();
    while (q1.size() > 0 && q2.size() > 0) {
      que = que.enq(q1.peek()).enq(q2.peek());
      q1 = q1.deq();
      q2 = q2.deq();
    }
    return que;
  }
}