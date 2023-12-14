/**
 * The main class for CS2030S Lab 1.
 *
 * @author Lim Zheng Ting
 * @version CS2030S AY22/23 Semester 1
 */


class ShopQueueEvent extends Event {

  private Customer customer;
  
  private Shop shop;

  public ShopQueueEvent(Customer c, Shop s) {
    super(c.getArrivalTime());
    this.customer = c;
    this.shop = s;
  }

  @Override
  public String toString() {
    return super.toString() + String.format(": %s joined shop queue %s", 
    this.customer.toString(), this.shop.toString());
  }

  @Override
  public Event[] simulate() {
    // The current event is a service-end event.
    // Mark the counter is available, then schedule
    // a departure event at the current time.
    this.shop.enqueue(this.customer);

    return new Event[] {
    };
  }
}
