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

  private Counter[] available;

  private Queue q;

  public Shop(int maxQ, int numOfCounters) {
    this.q = new Queue(maxQ);
    this.available = new Counter[numOfCounters];
    for (int i = 0; i < numOfCounters; i++) {
      this.available[i] = new Counter();
    }
  }

  public boolean ifQueueFull() {
    return this.q.isFull();
  }

  public boolean ifQueueEmpty() {
    return this.q.isEmpty();
  }

  public void enqueue(Customer c) {
    this.q.enq(c);
  }

  public Customer dequeue() {
    return (Customer) this.q.deq();
  }

  public Counter getFirstCounter() {
    for (Counter c : this.available) {
      if (c.getAvailability()) {
        return c;
      }
    }
    return null;
  }

  public void closeCounter(Counter c) {
    updateAvailability(c, false);
  }

  public void openCounter(Counter c) {
    updateAvailability(c, true);
  }

  private void updateAvailability(Counter c, boolean b) {
    for (int i = 0; i < this.available.length; i++) {
      if (c == this.available[i]) {
        this.available[i].setAvailability(b);
      }
    }
  }


  @Override
  public String toString() {
    return this.q.toString();
  }
}
