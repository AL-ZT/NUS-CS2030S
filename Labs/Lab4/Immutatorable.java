/**
 * The Immutatorable interface that can
 * transform when given something that is
 * Immutator.
 *
 * Contains a single abstract method transform.
 *
 * CS2030S Lab 4
 * AY22/23 Semester 1
 *
 * @author Lim Zheng Ting (Lab Group 10B)
 */

 interface Immutatorable<T> {
   <R> Immutatorable<R> transform(Immutator<? extends R, ? super T> imm);
}
