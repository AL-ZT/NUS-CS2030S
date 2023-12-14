/**
 * CS2030S PE1 Question 2
 * AY22/23 Semester 1
 */
public class Pair<T,S> {
  private T fst;
  private S snd;
  
  public Pair(T fst, S snd) {
    this.fst = fst;
    this.snd = snd;
  }
  
  public T getFst() {
    return this.fst;
  }
  public S getSnd() {
    return this.snd;
  }
  
  @Override
  public String toString() {
    return this.fst + "; " + this.snd;
  }
}