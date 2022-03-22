package com.intellij.sql.database;

import com.intellij.database.script.ScriptModel;
import com.intellij.openapi.util.Condition;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiFile;
import com.intellij.psi.SyntaxTraverser;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

public interface SqlNotebookManager {
  @NotNull <E> Collection<Cell> getCells(@NotNull PsiFile file, @NotNull ScriptModel<E> model, @NotNull Condition<? super TextRange> filter, int start, int end);

  @Nullable <E> E getPrecedingComment(@Nullable E element, @NotNull SyntaxTraverser.Api<E> api);

  final class Cell {
    public static final int UNDEFINED = -1;

    public final TextRange range;
    public final List<TextRange> ranges;
    public final int lastStatementEnd;

    public Cell(TextRange range, List<TextRange> ranges, int end) {
      this.range = range;
      this.ranges = ranges;
      lastStatementEnd = end == UNDEFINED ? range.getEndOffset() : end;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      Cell cell = (Cell)o;
      return lastStatementEnd == cell.lastStatementEnd &&
             range.equals(cell.range) &&
             ranges.equals(cell.ranges);
    }

    @Override
    public int hashCode() {
      return Objects.hash(range, ranges, lastStatementEnd);
    }
  }
}
