package com.intellij.sql.editor;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.SettingsCategory;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.sql.SqlBundle;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

@State(
  name = "SqlEditorOptions",
  storages = @Storage("editor.xml"),
  category = SettingsCategory.CODE
)
public class SqlEditorOptions implements PersistentStateComponent<SqlEditorOptions> {
  private boolean myConcatenateStringsOnEnter = true;
  private boolean myCloseBlocksOnEnter = true;
  private QualificationType myQualificationType = QualificationType.SMART;
  private QualificationType myTableQualification = QualificationType.SMART;
  private QualificationType myAliasQualification = QualificationType.SMART;
  private QualificationType myJoinConditionQualification = QualificationType.ALWAYS;

  private SqlEditorOptions load(SqlEditorOptions options) {
    myConcatenateStringsOnEnter = options.myConcatenateStringsOnEnter;
    myCloseBlocksOnEnter = options.myCloseBlocksOnEnter;
    myQualificationType = options.myQualificationType;
    myTableQualification = options.myTableQualification;
    myAliasQualification = options.myAliasQualification;
    myJoinConditionQualification = options.myJoinConditionQualification;
    return this;
  }

  @Nullable
  @Override
  public SqlEditorOptions getState() {
    return new SqlEditorOptions().load(this);
  }

  @Override
  public void loadState(@NotNull SqlEditorOptions state) {
    if (state != null) this.load(state);
  }

  public boolean isConcatenateStringsOnEnter() {
    return myConcatenateStringsOnEnter;
  }

  public void setConcatenateStringsOnEnter(boolean concatenateStringsOnEnter) {
    myConcatenateStringsOnEnter = concatenateStringsOnEnter;
  }

  public boolean isCloseBlocksOnEnter() {
    return myCloseBlocksOnEnter;
  }

  public void setCloseBlocksOnEnter(boolean closeBlocksOnEnter) {
    myCloseBlocksOnEnter = closeBlocksOnEnter;
  }

  @NotNull
  public QualificationType getNamespaceQualification() {
    return myQualificationType;
  }

  public void setNamespaceQualification(QualificationType type) {
    myQualificationType = type;
  }

  @NotNull
  public QualificationType getTableQualification() {
    return myTableQualification;
  }

  public void setTableQualification(QualificationType type) {
    myTableQualification = type;
  }

  @NotNull
  public QualificationType getAliasQualification() {
    return myAliasQualification;
  }

  public void setAliasQualification(QualificationType type) {
    myAliasQualification = type;
  }

  public QualificationType getJoinConditionQualification() {
    return myJoinConditionQualification;
  }

  public void setJoinConditionQualification(QualificationType joinConditionQualification) {
    myJoinConditionQualification = joinConditionQualification;
  }

  @NotNull
  public static SqlEditorOptions getInstance() {
    return ApplicationManager.getApplication().getService(SqlEditorOptions.class);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    SqlEditorOptions options = (SqlEditorOptions)o;
    return myConcatenateStringsOnEnter == options.myConcatenateStringsOnEnter &&
           myCloseBlocksOnEnter == options.myCloseBlocksOnEnter &&
           myQualificationType == options.myQualificationType &&
           myTableQualification == options.myTableQualification &&
           myAliasQualification == options.myAliasQualification &&
           myJoinConditionQualification == options.myJoinConditionQualification;
  }

  @Override
  public int hashCode() {
    return Objects.hash(myConcatenateStringsOnEnter,
                        myCloseBlocksOnEnter,
                        myQualificationType,
                        myTableQualification,
                        myAliasQualification,
                        myJoinConditionQualification);
  }

  public enum QualificationType {
    ALWAYS {
      @Override
      public boolean shouldQualify(int conflictedObjectsCount) {
        return true;
      }

      @Override
      public String toString() {
        return SqlBundle.message("settings.smart.keys.always");
      }
    },
    SMART {
      @Override
      public boolean shouldQualify(int conflictedObjectsCount) {
        return conflictedObjectsCount > 1;
      }

      @Override
      public String toString() {
        return SqlBundle.message("settings.smart.keys.on.collisions");
      }
    },
    NOT_QUALIFY {
      @Override
      public boolean shouldQualify(int conflictedObjectsCount) {
        return false;
      }

      @Override
      public String toString() {
        return SqlBundle.message("settings.smart.keys.never");
      }
    };

    public abstract boolean shouldQualify(int conflictedObjectsCount);
  }
}
