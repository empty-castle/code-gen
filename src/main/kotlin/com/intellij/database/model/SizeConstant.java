package com.intellij.database.model;

import org.intellij.lang.annotations.MagicConstant;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static com.intellij.database.model.DataType.*;

@Retention(RetentionPolicy.SOURCE)
@Target({
  ElementType.FIELD, ElementType.PARAMETER, ElementType.LOCAL_VARIABLE,
  ElementType.ANNOTATION_TYPE,
  ElementType.METHOD
})
@MagicConstant(intValues = {STAR_SIZE, MAX_SIZE, NO_SIZE})
public @interface SizeConstant {
}
