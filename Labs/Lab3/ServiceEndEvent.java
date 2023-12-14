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
    return super.toString() + String.format(": %s service done (by %s)",
    this.customer.toString(), this.counter.toString());
  }
  
  @Override
  public Event[] simulate() {
    // The current event is a service-end event.
    // Mark the counter is available, then schedule
    // a departure event at the current time.
    this.shop.openCounter(this.counter);
    
    Customer nextCustomer = null;
    // check if there's a next customer from counter first, then shop.
    // if counter queue not empty, get customer from counter queue and check shop queue
    if (!this.counter.queueIsEmpty()) {
      nextCustomer = this.counter.dequeue();
      nextCustomer.changeArrivalTime(this.customer.getEndTime());

      // if Shop Queue is empty, close counter, depart finished customer and service next customer
      if (this.shop.ifShopQueueEmpty()) {
        this.shop.closeCounter(this.counter);
        return new Event[] {
          new DepartureEvent(this.customer),
          new ServiceBeginEvent(nextCustomer, this.counter, this.shop)
        };
        // else if Shop queue not empty, 
        // depart current and CounterQueueEvent will handle moving from Shop to Counter Q
      } else {
        return new Event[] {
          new DepartureEvent(this.customer),
          new CounterQueueEvent(nextCustomer, this.counter, this.shop)
        };
      }
    } else {
      // else if counter queue is empty,
      // check if shop queue is empty in case counter Q is 0
      // if not empty, get customer from shop queue,
      // close counter, depart finished customer and service next.
      if (!this.shop.ifShopQueueEmpty()) {
        nextCustomer  = this.shop.dequeue();
        nextCustomer.changeArrivalTime(this.customer.getEndTime());
        this.shop.closeCounter(this.counter);

        return new Event[] {
          new DepartureEvent(this.customer),
          new ServiceBeginEvent(nextCustomer, this.counter, this.shop)
        };
        // else if both shop and counter Q is empty, depart finished customer
      } else {
        return new Event[] {
          new DepartureEvent(this.customer)
        };
      }
    }
  }
}
