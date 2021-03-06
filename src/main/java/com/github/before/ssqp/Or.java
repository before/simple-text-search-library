package com.github.before.ssqp;

import org.immutables.value.Value.Immutable;
import org.immutables.value.Value.Parameter;

@Immutable
abstract class Or extends Matcher {

  @Override
  Matcher append(Matcher other) {
    if (left().isEmpty()) {
      return withLeft(other);
    }
    if (right().isEmpty()) {
      return withRight(other);
    }
    return withRight(or(right(), other));
  }

  @Parameter(order = 0)
  abstract Matcher left();

  @Override
  public
  boolean matches(String text) {
    return left().matches(text) || right().matches(text);
  }

  @Override
  Matcher normalize() {
    if (left().isEmpty()) {
      return right().normalize();
    }
    if (right().isEmpty()) {
      return left().normalize();
    }
    return or(left().normalize(), right().normalize());
  }

  @Parameter(order = 2)
  abstract Matcher right();

  @Override
  public String toString() {
    StringBuilder b = new StringBuilder();
    if (left() instanceof And) {
      b.append('(');
      b.append(left());
      b.append(')');
    } else {
      b.append(left());
    }
    b.append(", ");
    if (right() instanceof And) {
      b.append('(');
      b.append(right());
      b.append(')');
    } else {
      b.append(right());
    }
    return b.toString();
  }

  abstract Or withLeft(Matcher expression);

  abstract Or withRight(Matcher expression);
}
