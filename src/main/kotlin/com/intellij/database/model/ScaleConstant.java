package com.intellij.database.model;

import org.intellij.lang.annotations.MagicConstant;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static com.intellij.database.model.DataType.NO_SCALE;

@Retention(RetentionPolicy.SOURCE)
@Target({
  ElementType.FIELD, ElementType.PARAMETER, ElementType.LOCAL_VARIABLE,
  ElementType.ANNOTATION_TYPE,
  ElementType.METHOD
})
@MagicConstant(intValues = NO_SCALE)
public @interface ScaleConstant {
}
