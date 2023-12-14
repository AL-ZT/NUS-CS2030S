class Counter {

  private final int counterId;
  
  private boolean availability;

  private int customerId = -1;

  public Counter(int id, boolean available) {
    this.counterId = id;
    this.availability = available;
  }

  /**
   * Getters and Setters
   */

  public int getCounterId() {
    return this.counterId;
  }

  public void setCustomerId(int id) {
    this.customerId = id;
  }

  public int getCustomerId() {
    return this.customerId;
  }

  public boolean getAvailability() {
    return this.availability;
  }

  public void setAvailability(boolean available) {
    this.availability = available;
    if (this.availability) {
      this.customerId = -1;
    }
  }
}
