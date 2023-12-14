class Counter implements Comparable<Counter> {

  private final int counterId;

  private static int lastId = 0;

  private boolean availability = true;

  private Queue<Customer> q;

  public Counter(int qSize) {
    this.counterId = lastId;
    Counter.lastId++;
    this.q = new Queue<Customer>(qSize);
  }
  
  //Getters and Setters

  public boolean getAvailability() {
    return this.availability;
  }

  public void setAvailability(boolean b) {
    this.availability = b;
  }

  public void enqueue(Customer c) {
    this.q.enq(c);
  }

  public Customer dequeue() {
    return this.q.deq();
  }

  public boolean queueIsEmpty() {
    return this.q.isEmpty();
  }

  public boolean queueIsFull() {
    return this.q.isFull();
  }

  public boolean checkCounter(Counter c) {
    if (this.counterId == c.counterId) {
      return true;
    }
    return false;
  }

  @Override
  public int compareTo(Counter obj) {
    return this.q.length() - obj.q.length();
  }

  @Override
  public String toString() {
    return String.format("S%d %s", this.counterId, this.q.toString());
  }
}
