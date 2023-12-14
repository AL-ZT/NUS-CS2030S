/**
 * Condition class that performs a negation on a value.
 */
class Not implements Cond {
  
  /** Value of Condition to do negation on. */
  private Cond val;
  
  /**
   * Public constructor to initialise value.
   *
   * @param val Condition to do negation on
   */
  public Not(Cond val) {
    this.val = val;
  }
  
  /**
   * Returns the evaluation of !val.
   *
   * @return A boolean, either <code>true</code> or <code>false</code>
   */
  @Override
  public boolean eval() {
    return !this.val.eval();
  }

  /** String representation of the operation. */
  @Override
  public String toString() {
    return "!(" + this.val + ")";
  }
  

  /**
   * Do a negation to the value.
   *
   * @return a condition that can be either <code>true</code>
   *     or <code>false</code> on evaluation.
   */
  @Override
  public Cond neg() {
    return this.val;
  }
}
