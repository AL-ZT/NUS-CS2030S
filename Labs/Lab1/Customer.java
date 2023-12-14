class Customer {

  private double arrivalTime = 0;

  private double serviceTime = 0;

  private final int customerId;

  private double endTime;

  public Customer(int customerId, double serviceTime, double arrivalTime) {
    this.arrivalTime = arrivalTime;
    this.serviceTime = serviceTime;
    this.customerId = customerId;
    this.endTime = arrivalTime + serviceTime;
  }

  public void depart() {
    this.endTime = arrivalTime;
  }

  @Override
  public String toString() {
    return String.format("C%d", this.getCustomerId());
  }

  public int getCustomerId() {
    return this.customerId;
  }

  public double getArrivalTime() {
    return this.arrivalTime;
  }

  public double getEndTime() {
    return this.endTime;
  }
}
