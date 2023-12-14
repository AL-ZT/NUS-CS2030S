/**
 * ArrivalEvent class for CS2030S Lab 1.
 *
 * @author Lim Zheng Ting
 * @version CS2030S AY22/23 Semester 1
 */

class ArrivalEvent extends Event {

  private Customer customer;

  private Shop shop;

  public ArrivalEvent(Customer c, Shop s) {
    super(c.getArrivalTime());
    this.customer = c;
    this.shop = s;
  }

  @Override
  public String toString() {
    return super.toString() + String.format(": %s arrived %s", this.customer, this.shop.toString());
  }
  
  @Override
  public Event[] simulate() {
    // The current event is an arrival event.
    // Find the first available counter.
    Counter firstCounter = this.shop.getFirstCounter();
    if (firstCounter == null) {
      // If no such counter can be found,
      // check if queue is full.
      if (this.shop.ifQueueFull()) {
        // if full, depart.
        // else, go to first available counter and get served.
        this.customer.depart();
        return new Event[] {
          new DepartureEvent(this.customer)
        };
      } else {
        return new Event[] {
          new QueueEvent(this.customer, this.shop)
        };
      }
    } else {
      this.shop.closeCounter(firstCounter);
      return new Event[] {
        new ServiceBeginEvent(this.customer, firstCounter, this.shop)
      };
    }
  }
}
