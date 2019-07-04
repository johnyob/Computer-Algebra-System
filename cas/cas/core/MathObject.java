package cas.core;

import cas.core.Types.*;


/**
 *
 * @author Alistair O'Brien
 * @author Nathan Shaw
 */
public abstract class MathObject {
  
  @Override
  public abstract boolean equals(Object other);
  
  public MathObject getNumerator() {
    return this;
  }
  
  public MathObject getDenominator() {
    return new Int(1);
  }
  
  @Override
  public abstract int hashCode();
}
