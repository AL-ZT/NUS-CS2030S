import cs2030s.fp.Constant;
import cs2030s.fp.Memo;

/**
 * Condition that stores a boolean value which may be true or false.
 */
class Bool implements Cond {

  
  /** Boolean Memo that only computes value when needed, and only once. */
  private Memo<Boolean> boolMemo;

  /**
   * Public constructor to initialise boolean value.
   *
   * @param val A Constant function that returns either true or false.
   */
  public Bool(Constant<Boolean> val) {
    this.boolMemo = Memo.from(val);
  }
  
  /**
   * Returns the evaluation of the function.
   *
   * @return A boolean, either <code>true</code> or <code>false</code>
   */
  @Override
  public boolean eval() {
    return this.boolMemo.get();
  }
  
  /** String representation of the operation. */
  @Override
  public String toString() {
    return this.boolMemo.toString().substring(0, 1);
  }
  
  /**
   * Do a negation to the value.
   *
   * @return a Condition that can be either <code>true</code>
   *     or <code>false</code> on evaluation.
   */
  @Override
  public Cond neg() {
    return new Not(this);
  }
}
