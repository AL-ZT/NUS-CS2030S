import java.util.Scanner;

/**
 * This class implements a shop simulation.
 *
 * @author Lim Zheng Ting
 * @version CS2030S AY22/23 Semester 1
 */ 
class ShopSimulation extends Simulation {
  /** 
   * The availability of counters in the shop. 
   */
  /** 
   * The list of customer arrival events to populate
   * the simulation with.
   */
  private Event[] initEvents;


  public ShopSimulation(Scanner sc) {
    this.setInitialEvents(new Event[sc.nextInt()]);
    int numOfCounters = sc.nextInt();

    Shop.setShopCounters(numOfCounters); 

    int id = 0;
    while (sc.hasNextDouble()) {
      double arrivalTime = sc.nextDouble();
      double serviceTime = sc.nextDouble();
      this.setInitialEvent(id, new ArrivalEvent(new Customer(id, serviceTime, arrivalTime)));
      id += 1;
    }
  }

  /**
   * Retrieve an array of events to populate the 
   * simulator with.
   *
   * @return An array of events for the simulator.
   */
  @Override
  public Event[] getInitialEvents() {
    return initEvents;
  }

  public void setInitialEvents(Event[] event) {
    this.initEvents = event;
  }

  public void setInitialEvent(int eventId, Event event) {
    this.initEvents[eventId] = event;
  }
}
