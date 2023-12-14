/**
 * ServiceBeginEvent class for CS2030S Lab 1.
 *
 * @author Lim Zheng Ting
 * @version CS2030S AY22/23 Semester 1
 */

class ServiceBeginEvent extends Event {

  private Customer customer;
 
  public ServiceBeginEvent(Customer c) {
    super(c.getArrivalTime());
    this.customer = c;
  }

  @Override
  public String toString() {
    return super.toString() + String.format(": Customer %d service begin (by Counter %d)", this.customer.getCustomerId(), Shop.getCounterByCustomerId(this.customer.getCustomerId()));
  }
  
  @Override
  public Event[] simulate() {
    // The current event is a service-begin event.
    // Mark the counter is unavailable, then schedule
    // a service-end event at the current time + service time.
    return new Event[] {
      new ServiceEndEvent(this.customer)
    };
  }
}
