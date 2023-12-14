/**
 * A conjunction of two boolean expressions. Operations are short-circuit,
 * meaning if first boolean expression is false, second expression will not
 * be evaluated.
 */
class And implements Cond {

  /** Value of the left boolean expression. */
  private Cond lVal;

  /** Value of the right boolean expression. */
  private Cond rVal;
  
  /**
   * Public Constructor to instantiate And, that takes in two Conditions.
   *
   * @param lVal Value to be stored as the left boolean expression
   * @param rVal Value to be stored as the right boolean expression
   */
  public And(Cond lVal, Cond rVal) {
    this.lVal = lVal;
    this.rVal = rVal;
  }
  
  /**
   * Returns the evaluation of lVal {@literal &&} rVal.
   *
   * @return A boolean, either <code>true</code> or <code>false</code>
   */
  @Override
  public boolean eval() {
    return this.lVal.eval() && this.rVal.eval();
  }
  
  /** String representation of the operation. */
  @Override
  public String toString() {
    return "(" + this.lVal + " & " + this.rVal + ")";
  }
  
  /**
   * Do a negation to the left and right values.
   *
   * @return a Condition that can be either <code>true</code>
   *     or <code>false</code> on evaluation.
   */
  @Override
  public Cond neg() {
    return new Or(lVal.neg(), rVal.neg());
  }
}
