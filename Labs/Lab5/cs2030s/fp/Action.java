package cs2030s.fp;

/**
 * The Action interface that can be called
 * on an object of type T to act.
 *
 *<p>Contains a single abstract method call.
 *
 *<p>CS2030S Lab 4
 * AY22/23 Semester 1
 *
 * @author Lim Zheng Ting (Lab Group 10B)
 */

public interface Action<T> {
  void call(T obj);
}
