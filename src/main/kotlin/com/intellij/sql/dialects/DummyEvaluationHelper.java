package com.intellij.sql.dialects;

import com.intellij.lang.Language;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Condition;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.SyntaxTraverser;
import com.intellij.util.containers.JBIterable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class DummyEvaluationHelper implements EvaluationHelper {

  @Override
  public <V> Condition<V> isStatement(SyntaxTraverser.Api<V> api) {
    return elemenet -> false;
  }

  @Override
  public <V> Condition<V> isFile(SyntaxTraverser.Api<V> api) {
    return element -> false;
  }

  @Override
  public <V> Condition<V> isWsOrComment(SyntaxTraverser.Api<V> api) {
    return element -> false;
  }

  @Override
  public <V> Condition<V> isBatchBlock(SyntaxTraverser.Api<V> api) { return element -> false; }

  @Override
  public <V> Condition<V> isStatementSeparator(SyntaxTraverser.Api<V> api, Language language) { return element -> false; }

  @Override
  public <V> SyntaxTraverser<V> statements(final TextRange range, final Language language, final SyntaxTraverser<V> s) {
    return SyntaxTraverser.syntaxTraverser(s.api);
  }

  @Override
  public <V> JBIterable<V> parameters(final TextRange range, final Language language, final SyntaxTraverser<V> s) {
    return JBIterable.empty();
  }

  @Override
  @NotNull
  public <V> SyntaxTraverser<V> parse(@NotNull Project project,
                                      @NotNull Language dialect,
                                      @NotNull CharSequence documentText,
                                      @Nullable Language hostLanguage) {
    //noinspection unchecked
    return (SyntaxTraverser<V>)SyntaxTraverser.psiTraverser();
  }
}
