/**
 * This class encapsulates an event in the shop
 * simulation.  Your task is to replace this
 * class with new classes, following proper OOP principles.
 *
 * @author Lim Zheng Ting
 * @version CS2030S AY22/23 Semester 1
 */
class Shop {
  /**
   * An array to indicate if a service counter is
   * available.  available[i] is true if and only
   * if service counter i is available to serve.
   */

  private Array<Counter> available;

  private Queue<Customer> q;

  public Shop(int numOfCounters, int counterQ, int shopQ) {
    this.q = new Queue<Customer>(shopQ);
    this.available = new Array<Counter>(numOfCounters);
    for (int i = 0; i < numOfCounters; i++) {
      this.available.set(i, new Counter(counterQ));
    }
  }

  public boolean ifShopQueueFull() {
    return this.q.isFull();
  }

  public boolean ifShopQueueEmpty() {
    return this.q.isEmpty();
  }

  public void updateCounter(Counter c) {
    for (int i = 0; i < this.available.length(); i++) {
      Counter curr = this.available.get(i);
      if (curr.checkCounter(c)) {
        this.available.set(i, c);
      }
    }
  }

  public void enqueue(Customer c) {
    this.q.enq(c);
  }

  public Customer dequeue() {
    return this.q.deq();
  }

  public Counter getFirstCounter() {
    for (int i = 0; i < this.available.length(); i++) {
      Counter c = this.available.get(i);
      if (c.getAvailability()) {
        return c;
      }
    }
    return null;
  }

  public Counter getShortestCounterQ() {
    return this.available.min();
  }

  public void closeCounter(Counter c) {
    updateAvailability(c, false);
  }

  public void openCounter(Counter c) {
    updateAvailability(c, true);
  }

  private void updateAvailability(Counter c, boolean b) {
    for (int i = 0; i < this.available.length(); i++) {
      Counter curr = this.available.get(i);
      if (c == curr) {
        curr.setAvailability(b);
        this.available.set(i, curr);
      }
    }
  }

  @Override
  public String toString() {
    return this.q.toString();
  }
}
