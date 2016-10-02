package il.org.spartan.spartanizer.ast;

import org.eclipse.jdt.core.dom.*;

import il.org.spartan.spartanizer.tippers.*;

/** An empty <code><b>enum</b></code> for fluent programming. The name should
 * say it all: The name, followed by a dot, followed by a method name, should
 * read like a sentence phrase. Generally here comes all the checks, and
 * coercions related to tips ordering and collisions.
 * @author Alex Kopzon
 * @since 2.5 */
public enum cantTip {
  ;
  public static boolean declarationInitializerStatementTerminatingScope(final ForStatement ¢) {
    VariableDeclarationFragment f = hop.prevToLastExpressionFragment(¢);
    if (f == null)
      return true;
    return new DeclarationInitializerStatementTerminatingScope().cantTip(f);
  }

  public static boolean declarationInitializerStatementTerminatingScope(final WhileStatement ¢) {
    VariableDeclarationFragment f = hop.prevToLastExpressionFragment(¢);
    if (f == null)
      return true;
    return new DeclarationInitializerStatementTerminatingScope().cantTip(f);
  }

  public static boolean declarationRedundantInitializer(final ForStatement ¢) {
    for (final VariableDeclarationFragment f : extract.fragments(step.body(¢)))
      if (new DeclarationRedundantInitializer().canTip(f))
        return false;
    return true;
  }

  public static boolean declarationRedundantInitializer(final WhileStatement ¢) {
    for (final VariableDeclarationFragment f : extract.fragments(step.body(¢)))
      if (new DeclarationRedundantInitializer().canTip(f))
        return false;
    return true;
  }

  public static boolean forRenameInitializerToCent(final ForStatement ¢) {
    VariableDeclarationExpression e = az.variableDeclarationExpression(¢);
    if (e == null)
      return true;
    return new ForRenameInitializerToCent().cantTip(e);
  }

  public static boolean remvoeRedundantIf(final ForStatement ¢) {
    for (final IfStatement s : extract.ifStatements(step.body(¢)))
      if (new RemoveRedundantIf().canTip(s))
        return false;
    return true;
  }

  public static boolean remvoeRedundantIf(final WhileStatement ¢) {
    for (final IfStatement s : extract.ifStatements(step.body(¢)))
      if (new RemoveRedundantIf().canTip(s))
        return false;
    return true;
  }
}