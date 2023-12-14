/**
 * The main class for CS2030S Lab 1.
 *
 * @author Lim Zheng Ting
 * @version CS2030S AY22/23 Semester 1
 */


class CounterQueueEvent extends Event {

  private Customer customer;
  
  private Counter counter;

  private Shop shop;

  private Customer nextCustomer = null;

  public CounterQueueEvent(Customer c, Counter count, Shop s) {
    super(c.getArrivalTime());
    this.customer = c;
    this.counter = count;
    this.shop = s;
    // Scenario 1 : ArrivalEvent puts customer in counter Q
    // because counter Q is first available before shop Q
    // Shop Q will therefore be always empty
    //
    // Scenario 2 : ServiceEndEvent brings customer from shop Q to counter Q
    // it will pass in the next customer for service instead
    if (!this.shop.ifShopQueueEmpty()) {
      this.customer = this.shop.dequeue();
      this.nextCustomer = c;
    }
  }

  @Override
  public String toString() {
    return super.toString() + String.format(": %s joined counter queue (at %s)", 
    this.customer.toString(), this.counter.toString());
  }

  @Override
  public Event[] simulate() {
    // The current event is a Counter Queue event.
    // dequeue customer from Shop if any and enqueue to counter, and begin next service

    // if this came from ServiceEnd, 
    if (this.nextCustomer != null) {
      this.customer.changeArrivalTime(this.nextCustomer.getEndTime());
      this.shop.closeCounter(this.counter);
      this.counter.enqueue(this.customer);
      this.shop.updateCounter(this.counter);

      return new Event[] {
        new ServiceBeginEvent(this.nextCustomer, this.counter, this.shop)
      };
    } else {
      // for events that came directly from ArrivalEvent class
      this.counter.enqueue(this.customer);
      this.shop.updateCounter(this.counter);
      return new Event[] {
      };
    }
  }
}
