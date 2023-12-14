class Counter {

  private final int counterId;

  private static int lastId = 0;

  private boolean availability = true;

  public Counter() {
    this.counterId = lastId;
    Counter.lastId++;
  }
  
  //Getters and Setters

  public boolean getAvailability() {
    return this.availability;
  }

  public void setAvailability(boolean b) {
    this.availability = b;
  }

  @Override
  public String toString() {
    return String.format("S%d", this.counterId);
  }
}
