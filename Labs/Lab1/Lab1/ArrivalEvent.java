/**
 * ArrivalEvent class for CS2030S Lab 1.
 *
 * @author Lim Zheng Ting
 * @version CS2030S AY22/23 Semester 1
 */

class ArrivalEvent extends Event {

  private Customer customer;
	
  public ArrivalEvent(Customer c) {
    super(c.getArrivalTime());
    this.customer = c;
      /**
   * Constructor for a Arrival event.
   *
   * @param time       The time this event occurs.
   * @param customerId The customer associated with this
   *                   event.
   * @param serviceTime The time this customer takes
   *                    for service.
   */
  }

  @Override
  public String toString() {
    return super.toString() + String.format(": Customer %d arrives", this.customer.getCustomerId());
  }
  
  @Override
  public Event[] simulate() {
    // The current event is an arrival event.
    // Find the first available counter.
    int counter = Shop.checkFirstCounter();
    if (!Shop.checkCounterAvailability(counter)) {
      // If no such counter can be found, the customer
      // should depart.
      this.customer.depart();
      return new Event[] {
        new DepartureEvent(this.customer)
      };
    } else {
      // Else, the customer should go the the first
      // available counter and get served.
      Shop.markCounterUnavailable(counter, this.customer.getCustomerId()); 
      return new Event[] {
        new ServiceBeginEvent(this.customer)
      };
    }
  }
}
