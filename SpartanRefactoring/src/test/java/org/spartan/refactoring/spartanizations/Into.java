package org.spartan.refactoring.spartanizations;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.spartan.refactoring.spartanizations.TESTUtils.rewrite;
import static org.spartan.refactoring.wring.TrimmerTest.countOpportunities;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.ConditionalExpression;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.InfixExpression;
import org.eclipse.jdt.core.dom.PrefixExpression;
import org.eclipse.jdt.core.dom.Statement;
import org.eclipse.jface.text.Document;
import org.spartan.refactoring.utils.As;
import org.spartan.refactoring.wring.Trimmer;

/**
 * An empty <code><b>enum</b></code> for fluent programming. The name should say
 * it all: The name, followed by a dot, followed by a method name, should read
 * like a sentence phrase.
 *
 * @author Yossi Gil
 * @since 2015-07-16
 */
public enum Into {
  ;
  static final String WHITES = "(?m)\\s+";
  private static final String PRE_STATEMENT = //
  "package p;public class SpongeBob {\n" + //
      "public boolean squarePants(){\n" + //
      "";
  private static final String POST_STATEMENT = //
  "" + //
      "} // END OF METHO\n" + //
      "} // END OF PACKAGE\n" + //
      "";
  private static final String PRE_EXPRESSION = PRE_STATEMENT + "   return ";
  private static final String POST_EXPRESSION = ";\n" + POST_STATEMENT;
  /**
   * Convert a given {@link String} into an {@link ConditionalExpression}, or
   * fail the current test, if such a conversion is not possible
   *
   * @param conditionalExpression a {@link String} that represents a
   *          "conditional" (also known as "ternary") expression.
   * @return an {@link Statement} data structure representing the parameter.
   */
  public static ConditionalExpression c(final String conditionalExpression) {
    final Expression $ = e(conditionalExpression);
    assertThat(conditionalExpression, $, notNullValue());
    assertThat(conditionalExpression, $, instanceOf(ConditionalExpression.class));
    return (ConditionalExpression) $;
  }
  public static String compressSpaces(final String s) {
    String $ = s//
        .replaceAll("(?m)\\s+", " ") // Squeeze whites
        .replaceAll("^\\s", "") // Opening whites
        .replaceAll("\\s$", "") // Closing whites
        ;
    for (final String operator : new String[] { ":", ",", "\\{", "\\}", "=", ":", "\\?", ";", "\\+", ">", ">=", "!=", "==", "<", "<=", "-", "\\*", "\\|", "\\&", "%", "\\(", "\\)",
        "[\\^]" })
      $ = $ //
          .replaceAll(WHITES + operator, operator) // Preceding whites
          .replaceAll(operator + WHITES, operator) // Trailing whites
          ;
    return $;
  }
  /**
   * Convert a given {@link String} into an {@link Expression}, or fail the
   * current test, if such a conversion is not possible
   *
   * @param expression a {@link String} that represents a Java expression
   * @return an {@link Expression} data structure representing the parameter.
   */
  public static Expression e(final String expression) {
    return (Expression) As.EXPRESSION.ast(expression);
  }
  /**
   * Convert a given {@link String} into an {@link InfixExpression}, or fail the
   * current test, if such a conversion is not possible
   *
   * @param expression a {@link String} that represents a Java expression
   * @return an {@link InfixExpression} data structure representing the
   *         parameter.
   */
  public static InfixExpression i(final String expression) {
    return (InfixExpression) e(expression);
  }
  /**
   * Convert a given {@link String} into an {@link PrefixExpression}, or fail
   * the current test, if such a conversion is not possible
   *
   * @param expression a {@link String} that represents a Java expression
   * @return a {@link PrefixExpression} data structure representing the
   *         parameter.
   */
  public static PrefixExpression p(final String expression) {
    return (PrefixExpression) e(expression);
  }
  /**
   * Convert a given {@link String} into an {@link Statement}, or fail the
   * current test, if such a conversion is not possible
   *
   * @param statement a {@link String} that represents a Java statement
   * @return an {@link Statement} data structure representing the parameter.
   */
  public static Statement s(final String statement) {
    assertThat(statement, notNullValue());
    final ASTNode n = As.STATEMENTS.ast(statement);
    assertThat(statement, n, notNullValue());
    assertThat(statement, n, instanceOf(Statement.class));
    return (Statement) n;
  }
  static String apply(final Trimmer t, final String from) {
    final CompilationUnit u = (CompilationUnit) As.COMPILIATION_UNIT.ast(from);
    assertNotNull(u);
    final Document d = new Document(from);
    assertNotNull(d);
    return rewrite(t, u, d).get();
  }
  static void assertNoOpportunity(final Spartanization s, final String from) {
    final CompilationUnit u = (CompilationUnit) As.COMPILIATION_UNIT.ast(from);
    assertEquals(u.toString(), 0, countOpportunities(s, u));
  }
  static void assertNotEvenSimilar(final String expected, final String actual) {
    assertNotEquals(compressSpaces(expected), compressSpaces(actual));
  }
  static void assertOneOpportunity(final Spartanization s, final String from) {
    final CompilationUnit u = (CompilationUnit) As.COMPILIATION_UNIT.ast(from);
    assertEquals(u.toString(), 1, countOpportunities(s, u));
  }
}
