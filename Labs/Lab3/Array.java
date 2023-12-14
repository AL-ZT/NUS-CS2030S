/**
 * The Array<T> for CS2030S 
 *
 * @author Lim Zheng Ting
 * @version CS2030S AY22/23 Semester 1
 */
class Array<T extends Comparable<T>> { // Change to bounded type parameter
  private T[] array;

  Array(int size) {
    // Only way to modify array is through get and set method,
    // and we only put object of type T inside. So it is safe to cast Object[] to T[].
    @SuppressWarnings({"unchecked", "rawtypes"})
    T[] temp = (T[]) new Comparable[size];
    this.array = temp;
  }

  public void set(int index, T item) {
    this.array[index] = item;
  }

  public T get(int index) {
    return this.array[index];
  }

  public T min() {
    T currentMin = null;
    for (T curr : this.array) {
      if (currentMin == null) {
        currentMin = curr;
      } else {
        if (curr.compareTo(currentMin) < 0) {
          currentMin = curr;
        }
      }
    }
    return currentMin;
  }

  public int length() {
    return this.array.length;
  }

  @Override
  public String toString() {
    StringBuilder s = new StringBuilder("[ ");
    for (int i = 0; i < array.length; i++) {
      s.append(i + ":" + array[i]);
      if (i != array.length - 1) {
        s.append(", ");
      }
    }
    return s.append(" ]").toString();
  }
}
