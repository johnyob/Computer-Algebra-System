/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cas.core;

import cas.core.Operators.*;
import helpers.List;

import java.lang.Math;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;


/**
 *
 * @author johny
 */
public class Types {

  public static class Bool extends MathObject {

    private boolean value;

    public Bool(boolean value) {
      this.value = value;
    }

    public boolean getValue() {
      return value;
    }

    @Override
    public boolean equals(Object other) {
      return value == ((Bool) other).getValue();
    }

    @Override
    public int hashCode() {
      return Boolean.hashCode(value);
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

  }

  public static abstract class Number extends MathObject {

    /**
     *
     * @return
     */
    public abstract Decimal toDecimal();
  }

  public static class Int extends Number {

    private long value;

    public Int(long value) {
      this.value = value;
    }

    public Int(int value) {
      this.value = value;
    }

    public long getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    @Override
    public boolean equals(Object other) {
      return value == ((Int) other).getValue();
    }

    @Override
    public int hashCode() {
      return Long.hashCode(value);
    }

    @Override
    public Decimal toDecimal() {
      return new Decimal((double) value);
    }

  }

  public static class Decimal extends Number {

    private double tolerance = 0;
    private double value;

    public Decimal(double value) {
      this.value = value;
    }

    public double getValue() {
      return value;
    }

    public double getTolerance() {
      return tolerance;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    @Override
    public boolean equals(Object other) {
      if (!(other instanceof Decimal)) return false;

      if (tolerance != 0) return Math.abs(value - ((Decimal) other).getValue()) < tolerance;

      return value == ((Decimal) other).getValue();
    }

    @Override
    public int hashCode() {
      return Double.hashCode(value);
    }

    @Override
    public Decimal toDecimal() {
      return this;
    }
  }


  public static class Fraction extends Number {

    private Int numerator;
    private Int denominator;

    public Fraction(Int numerator, Int denominator) {
      this.numerator = numerator;
      this.denominator = denominator;
    }

    @Override
    public Int getNumerator() {
      return numerator;
    }

    @Override
    public Int getDenominator() {
      return denominator;
    }

    @Override
    public String toString() {
      return numerator.toString() + " / " + denominator.toString();
    }

    @Override
    public boolean equals(Object other) {
      return numerator.equals(((Fraction) other).getNumerator()) && denominator.equals(((Fraction) other).getDenominator());
    }

    @Override
    public Decimal toDecimal() {
      return new Decimal((double) numerator.getValue() / (double) denominator.getValue());
    }

    @Override
    public int hashCode() {
      return Arrays.hashCode(new Int[]{numerator, denominator});
    }

  }


  public static class Undefined extends MathObject {

    @Override
    public boolean equals(Object other) {
      return other instanceof Undefined;
    }

    @Override
    public int hashCode() {
      return -1;
    }

  }

  public static class Rational {

    public static long divide(long a, long b) {
      return a / b;
    }

    public static long remainder(long a, long b) {
      return a % b;
    }

    public static long gcd(long a, long b) {
      long remainder;
      while (b != 0) {
        remainder = remainder(a, b);
        a = b;
        b = remainder;
      }
      return Math.abs(a);
    }

    public static MathObject simplifyRationalNumber(MathObject n) {
      if (n instanceof Int) return (Int) n;

      if (n instanceof Fraction) {
        Fraction n_ = (Fraction) n;
        long numerator = n_.getNumerator().getValue();
        long denominator = n_.getDenominator().getValue();

        if (remainder(numerator, denominator) == 0) return new Int(divide(numerator, denominator));

        long divisor = gcd(numerator, denominator);

        long sign = denominator * Math.abs(denominator); //Use long instead of int to prevent lossy conversion
        numerator *= sign;
        denominator *= sign;

        return new Fraction(new Int(Rational.divide(numerator, divisor)), new Int(Rational.divide(denominator, divisor)));
      }

      return null;
    }

    
    //Possibly scrap methods, don't really work
    public static Int numerator(MathObject n) {
      if (n instanceof Int) return (Int) n;

      if (n instanceof Fraction) return numerator(((Fraction) n).getNumerator());

      return null;
    }

    public static Int denominator(MathObject n) {
      if (n instanceof Int) return new Int(1);

      if (n instanceof Fraction) return denominator(((Fraction) n).getDenominator());

      return null;
    }

    public static Fraction sum(MathObject a, MathObject b) {
      return new Fraction(
        new Int(numerator(a).getValue() * denominator(b).getValue() 
                + numerator(b).getValue() * denominator(a).getValue()),
        new Int(denominator(a).getValue() * denominator(b).getValue())
      );
    }
    
    public static Fraction difference(MathObject a, MathObject b) {
      return new Fraction(
        new Int(numerator(a).getValue() * denominator(b).getValue() 
                - numerator(b).getValue() * denominator(a).getValue()),
        new Int(denominator(a).getValue() * denominator(b).getValue())
      );
    }
    
    public static Fraction product(MathObject a, MathObject b) {
      return new Fraction(
        new Int(numerator(a).getValue() * numerator(b).getValue()),
        new Int(denominator(a).getValue() * denominator(b).getValue())
      );
    }
    
    public static MathObject quotient(MathObject a, MathObject b) {
      
      if (numerator(b).getValue() == 0) return new Undefined();
      
      return new Fraction(
        new Int(numerator(a).getValue() * denominator(b).getValue()),
        new Int(denominator(a).getValue() * numerator(b).getValue())
      );
    }
    
    public static MathObject power(MathObject a, long n) {
      if (numerator(a).getValue() != 0) {
        if (n > 0) return product(power(a, n - 1), a);
        if (n == 0) return new Int(1);
        
        Fraction multiplicativeInverse = new Fraction(denominator(a), numerator(a));
        if (n == -1) return multiplicativeInverse;
        if (n < -1) return power(multiplicativeInverse, -n);
      }
      
      if (n >= 1) return new Int(0);
      if (n <= 0) return new Undefined();
      
      return null;
    }
    
    //TODO: recursive implementation of rational simplify
    private static MathObject simplifyRecursive(MathObject n) {
      return null;
    }
    
    public static MathObject simplify(MathObject n) {
      MathObject simplifiedExpression = simplifyRecursive(n);
      
      if (simplifiedExpression instanceof Undefined) return simplifiedExpression;
      return simplifyRationalNumber(simplifiedExpression);
    }
    
    
    
  }
  
  public static class Symbol extends MathObject {
    private String identifier;
    
    public Symbol(String identifier) {
      this.identifier = identifier;
    }
    
    public String getIdentifier() {
      return identifier;
    }
    
    @Override
    public String toString() {
      return identifier;
    }
    
    @Override
    public int hashCode() {
      return identifier.hashCode();
    }
    
    @Override
    public boolean equals(Object other) {
      if (!(other instanceof Symbol)) return false;
      
      return identifier.equals(((Symbol) other).getIdentifier());
    }
  }

  public static abstract class Function extends MathObject {
    
    public abstract MathObject process(MathObject[] parameters);
    
    private String identifier;
    private List<MathObject> arguments;
    
    public Function(String identifier, List<MathObject> arguments) {
      this.identifier = identifier;
      this.arguments = arguments;
    }
    
    public String getIdentifier() {
      return identifier;
    }
    
    public List<MathObject> getArguments() {
      return arguments;
    }
    
    public MathObject simplify() {
      return process(arguments.toArray());
    }
    
    @Override
    public boolean equals(Object other) {
      if (!(other instanceof Function)) return false;
      
      return identifier.equals(((Function) other).getIdentifier()) 
             && arguments.equals(((Function) other).getArguments());
    }

    @Override
    public int hashCode() {
      int hash = 7;
      hash = 37 * hash + Objects.hashCode(this.identifier);
      hash = 37 * hash + Objects.hashCode(this.arguments);
      return hash;
    }

  }
}
