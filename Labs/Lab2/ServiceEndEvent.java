/**
 * The main class for CS2030S Lab 1.
 *
 * @author Lim Zheng Ting
 * @version CS2030S AY22/23 Semester 1
 */


class ServiceEndEvent extends Event {

  private Customer customer;

  private Counter counter;

  private Shop shop;

  public ServiceEndEvent(Customer c, Counter count, Shop s) {
    super(c.getEndTime());
    this.customer = c;
    this.counter = count;
    this.shop = s;
  }

  @Override
  public String toString() {
    return super.toString() + String.format(": %s service done (by %s)", this.customer.toString(), this.counter.toString());
  }
  
  @Override
  public Event[] simulate() {
    // The current event is a service-end event.
    // Mark the counter is available, then schedule
    // a departure event at the current time.
    this.shop.openCounter(this.counter);

    if (!this.shop.ifQueueEmpty()) {
      // if queue not empty, get next guy
      Customer c = this.shop.dequeue();
      c.moveToCounter(this.customer.getEndTime());
      this.shop.closeCounter(this.counter);

      return new Event[] {
        new DepartureEvent(this.customer),
        new ServiceBeginEvent(c, this.counter, this.shop)
      };
    }
    // else if q is empty, just depart
    return new Event[] {
      new DepartureEvent(this.customer)
    };
  }
}
