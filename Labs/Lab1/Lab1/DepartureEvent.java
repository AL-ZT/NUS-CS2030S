/**
 * The DepartureEvent class for CS2030S Lab 1.
 *
 * @author Lim Zheng Ting
 * @version CS2030S AY22/23 Semester 1
 */

class DepartureEvent extends Event {

  private Customer customer;

  public DepartureEvent(Customer c) {
    super(c.getEndTime());
    this.customer = c;
  }

  @Override
  public String toString() {
    return super.toString() + String.format(": Customer %d departed", this.customer.getCustomerId());
  }

  @Override
  public Event[] simulate() {
    return new Event[] {};
  }
}
