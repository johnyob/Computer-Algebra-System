package cas.core;

import cas.core.Types.*;

/**
 *
 * @author Alistair O'Brien
 * @author Nathan Shaw
 */
public class Operators {
  public static MathObject add(MathObject a, int b) {return add(a, new Int(b));} 
  public static MathObject add(int a, MathObject b) {return add(new Int(a), b);} 
  public static MathObject add(MathObject a, long b) {return add(a, new Int(b));}
  public static MathObject add(long a, MathObject b) {return add(new Int(a), b);}
  public static MathObject add(MathObject a, double b) {return add(a, new Decimal(b));}
  public static MathObject add(double a, MathObject b) {return add(new Decimal(a), b);}

  public static MathObject difference(MathObject a, int b) {return difference(a, new Int(b));}
  public static MathObject difference(int a, MathObject b) {return difference(new Int(a), b);}
  public static MathObject difference(MathObject a, long b) {return difference(a, new Int(b));}
  public static MathObject difference(long a, MathObject b) {return difference(new Int(a), b);}
  public static MathObject difference(MathObject a, double b) {return difference(a, new Decimal(b));}
  public static MathObject difference(double a, MathObject b) {return difference(new Decimal(a), b);}

  public static MathObject multiply(MathObject a, int b) {return multiply(a, new Int(b));}
  public static MathObject multiply(int a, MathObject b) {return multiply(new Int(a), b);}
  public static MathObject multiply(MathObject a, long b) {return multiply(a, new Int(b));}
  public static MathObject multiply(long a, MathObject b) {return multiply(new Int(a), b);}
  public static MathObject multiply(MathObject a, double b) {return multiply(a, new Decimal(b));}
  public static MathObject multiply(double a, MathObject b) {return multiply(new Decimal(a), b);}

  public static MathObject divide(MathObject a, int b) {return divide(a, new Int(b));}
  public static MathObject divide(int a, MathObject b) {return divide(new Int(a), b);}
  public static MathObject divide(MathObject a, long b) {return divide(a, new Int(b));}
  public static MathObject divide(long a, MathObject b) {return divide(new Int(a), b);}
  public static MathObject divide(MathObject a, double b) {return divide(a, new Decimal(b));}
  public static MathObject divide(double a, MathObject b) {return divide(new Decimal(a), b);}

  public static MathObject power(MathObject a, int b) {return power(a, new Int(b));}
  public static MathObject power(int a, MathObject b) {return power(new Int(a), b);}
  public static MathObject power(MathObject a, long b) {return power(a, new Int(b));}
  public static MathObject power(long a, MathObject b) {return power(new Int(a), b);}
  public static MathObject power(MathObject a, double b) {return power(a, new Decimal(b));}
  public static MathObject power(double a, MathObject b) {return power(new Decimal(a), b);}

  public static MathObject add(MathObject a, MathObject b) {return new Sum(a, b).simplify();}
  public static MathObject difference(MathObject a, MathObject b) {return new Difference(a, b).simplify();}
  public static MathObject multiply(MathObject a, MathObject b) {return new Product(a, b).simplify();}
  public static MathObject divide(MathObject a, MathObject b) {return new Quoitent(a, b).simplify();}
  public static MathObject power(MathObject a, MathObject b) {return new Power(a, b).simplify();}
}
