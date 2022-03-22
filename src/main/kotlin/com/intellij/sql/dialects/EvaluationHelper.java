package com.intellij.sql.dialects;

import com.intellij.lang.Language;
import com.intellij.lang.LanguageExtension;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Condition;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.SyntaxTraverser;
import com.intellij.util.containers.JBIterable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author Liudmila Kornilova
 */

public interface EvaluationHelper {
  LanguageExtension<EvaluationHelper> EP = new LanguageExtension<>("com.intellij.sql.evaluationHelper", new DummyEvaluationHelper());

  <V> Condition<V> isStatement(SyntaxTraverser.Api<V> api);

  <V> Condition<V> isFile(SyntaxTraverser.Api<V> api);

  <V> Condition<V> isBatchBlock(SyntaxTraverser.Api<V> api);

  <V> Condition<V> isWsOrComment(SyntaxTraverser.Api<V> api);

  <V> Condition<V> isStatementSeparator(SyntaxTraverser.Api<V> api, Language language);

  default <V> Condition<V> canContainStatements(SyntaxTraverser.Api<V> api) {
    return isFile(api);
  }

  <V> SyntaxTraverser<V> statements(final TextRange range, final Language language, final SyntaxTraverser<V> s);

  <V> JBIterable<V> parameters(final TextRange range, final Language language, final SyntaxTraverser<V> s);

  @NotNull
  <V> SyntaxTraverser<V> parse(@NotNull Project project,
                               @NotNull Language language,
                               @NotNull CharSequence documentText,
                               @Nullable Language hostLanguage);

  @NotNull
  default <V> SyntaxTraverser<V> getNotebookTraverser(@NotNull SyntaxTraverser<V> traverser) {
    return traverser;
  }
}
