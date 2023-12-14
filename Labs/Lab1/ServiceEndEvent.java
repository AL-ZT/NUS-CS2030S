/**
 * The main class for CS2030S Lab 1.
 *
 * @author Lim Zheng Ting
 * @version CS2030S AY22/23 Semester 1
 */


class ServiceEndEvent extends Event {

  private Customer customer;

  public ServiceEndEvent(Customer c) {
    super(c.getEndTime());
    this.customer = c;
  }

  @Override
  public String toString() {
    return super.toString() + String.format(": Customer %d service done (by Counter %d)", this.customer.getCustomerId(), Shop.getCounterByCustomerId(this.customer.getCustomerId()));
  }
  
  @Override
  public Event[] simulate() {
    // The current event is a service-end event.
    // Mark the counter is available, then schedule
    // a departure event at the current time.
    Shop.markCounterAvailable(Shop.getCounterByCustomerId(this.customer.getCustomerId()));
    Shop.
    return new Event[] {
      new DepartureEvent(this.customer)
    };
  }
}
