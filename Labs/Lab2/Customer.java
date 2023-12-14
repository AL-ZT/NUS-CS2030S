class Customer {

  private double arrivalTime = 0;

  private double serviceTime = 0;

  private final int customerId;
  
  private static int lastId = 0;

  private double endTime;

  public Customer(double serviceTime, double arrivalTime) {
    this.arrivalTime = arrivalTime;
    this.serviceTime = serviceTime;
    this.customerId = lastId;
    this.endTime = arrivalTime + serviceTime;
    Customer.lastId++;
  }

  public void depart() {
    this.endTime = arrivalTime;
  }

  @Override
  public String toString() {
    return String.format("C%d", this.customerId);
  }

  public double getArrivalTime() {
    return this.arrivalTime;
  }

  public double getEndTime() {
    return this.endTime;
  }

  public void moveToCounter(double arrivalTime) {
    this.arrivalTime = arrivalTime;
    this.endTime = this.arrivalTime + this.serviceTime;
  }
}
