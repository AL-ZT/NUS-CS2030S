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
      // If no available counter,
      // check for shortest counter queue.
      Counter shortestCounterQ = this.shop.getShortestCounterQ();
      if (shortestCounterQ.queueIsFull()) {
        // if shortest counter Q not available, check shop queue
        if (this.shop.ifShopQueueFull()) {
          // if shop queue full, depart
          this.customer.depart();
          return new Event[] {
            new DepartureEvent(this.customer)
          };
        } else {
          // else if shop queue not full, create Shop queue Event
          return new Event[] {
            new ShopQueueEvent(this.customer, this.shop)
          };
        }
      } else {
        //else if shortest counter Q available, join Q
        return new Event[] {
          new CounterQueueEvent(this.customer, shortestCounterQ, this.shop)
        };
      }
    } else {
      // else if there is an available counter, begin service
      this.shop.closeCounter(firstCounter);
      return new Event[] {
        new ServiceBeginEvent(this.customer, firstCounter, this.shop)
      };
    }
  }
}
