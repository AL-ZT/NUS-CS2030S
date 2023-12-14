/**
 * ServiceBeginEvent class for CS2030S Lab 1.
 *
 * @author Lim Zheng Ting
 * @version CS2030S AY22/23 Semester 1
 */

class ServiceBeginEvent extends Event {

  private Customer customer;

  private Counter counter;

  private Shop shop;
 
  public ServiceBeginEvent(Customer c, Counter count, Shop s) {
    super(c.getArrivalTime());
    this.customer = c;
    this.counter = count;
    this.shop = s;
  }

  @Override
  public String toString() {
    return super.toString() + String.format(": %s service begin (by %s)", 
    this.customer.toString(), this.counter.toString());
  }
  
  @Override
  public Event[] simulate() {
    // The current event is a service-begin event.
    // Mark the counter is unavailable, then schedule
    // a service-end event at the current time + service time.
    return new Event[] {
      new ServiceEndEvent(this.customer, this.counter, this.shop)
    };
  }
}
