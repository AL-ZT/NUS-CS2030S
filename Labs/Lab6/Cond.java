/**
 * A Condition Interface that is able to evaluate and do negation.
 */
interface Cond {

  /**
   * Evaluates the expression of the conditional.
   *
   * @return a boolean of either a true or false.
   */
  boolean eval();

  /**
   * Performs negation on its values.
   *
   * @return A condition that either returns a true or false on evaluation.
   */
  Cond neg();

}
