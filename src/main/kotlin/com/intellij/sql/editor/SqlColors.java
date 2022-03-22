package com.intellij.sql.editor;

import com.intellij.openapi.editor.DefaultLanguageHighlighterColors;
import com.intellij.openapi.editor.HighlighterColors;
import com.intellij.openapi.editor.colors.EditorColors;
import com.intellij.openapi.editor.colors.TextAttributesKey;

import static com.intellij.openapi.editor.colors.TextAttributesKey.createTextAttributesKey;

/**
 * @author gregsh
 */
public interface SqlColors {
  TextAttributesKey SQL_BAD_CHARACTER   = createTextAttributesKey("SQL_BAD_CHARACTER", HighlighterColors.BAD_CHARACTER);
  TextAttributesKey SQL_COMMENT         = createTextAttributesKey("SQL_COMMENT", DefaultLanguageHighlighterColors.LINE_COMMENT);
  TextAttributesKey SQL_IDENT_DELIMITED = createTextAttributesKey("SQL_IDENT_DELIMITED", HighlighterColors.TEXT);
  TextAttributesKey SQL_IDENT           = createTextAttributesKey("SQL_IDENT", HighlighterColors.TEXT);
  TextAttributesKey SQL_SEMICOLON       = createTextAttributesKey("SQL_SEMICOLON", DefaultLanguageHighlighterColors.SEMICOLON);
  TextAttributesKey SQL_COMMA           = createTextAttributesKey("SQL_COMMA", DefaultLanguageHighlighterColors.COMMA);
  TextAttributesKey SQL_DOT             = createTextAttributesKey("SQL_DOT", DefaultLanguageHighlighterColors.DOT);
  TextAttributesKey SQL_STRING          = createTextAttributesKey("SQL_STRING", DefaultLanguageHighlighterColors.STRING);
  TextAttributesKey SQL_PARENS          = createTextAttributesKey("SQL_PARENS", DefaultLanguageHighlighterColors.PARENTHESES);
  TextAttributesKey SQL_BRACKETS        = createTextAttributesKey("SQL_BRACKETS", DefaultLanguageHighlighterColors.BRACKETS);
  TextAttributesKey SQL_BRACES          = createTextAttributesKey("SQL_BRACES", DefaultLanguageHighlighterColors.BRACES);
  TextAttributesKey SQL_NUMBER          = createTextAttributesKey("SQL_NUMBER", DefaultLanguageHighlighterColors.NUMBER);
  TextAttributesKey SQL_KEYWORD         = createTextAttributesKey("SQL_KEYWORD", DefaultLanguageHighlighterColors.KEYWORD);
  TextAttributesKey SQL_TYPE            = createTextAttributesKey("SQL_TYPE", SQL_KEYWORD);
  TextAttributesKey SQL_PROCEDURE       = createTextAttributesKey("SQL_PROCEDURE", DefaultLanguageHighlighterColors.STATIC_METHOD);
  TextAttributesKey SQL_PARAMETER       = createTextAttributesKey("SQL_PARAMETER", DefaultLanguageHighlighterColors.PARAMETER);
  TextAttributesKey SQL_VARIABLE        = createTextAttributesKey("SQL_VARIABLE", DefaultLanguageHighlighterColors.GLOBAL_VARIABLE);
  TextAttributesKey SQL_LABEL           = createTextAttributesKey("SQL_LABEL", DefaultLanguageHighlighterColors.LABEL);
  TextAttributesKey SQL_LOCAL_ALIAS     = createTextAttributesKey("SQL_LOCAL_ALIAS", DefaultLanguageHighlighterColors.LOCAL_VARIABLE);
  TextAttributesKey SQL_TABLE           = createTextAttributesKey("SQL_TABLE", DefaultLanguageHighlighterColors.CLASS_NAME);
  TextAttributesKey SQL_COLUMN          = createTextAttributesKey("SQL_COLUMN", DefaultLanguageHighlighterColors.INSTANCE_FIELD);
  TextAttributesKey SQL_SCHEMA          = createTextAttributesKey("SQL_SCHEMA", DefaultLanguageHighlighterColors.CLASS_NAME);
  TextAttributesKey SQL_DATABASE_OBJECT = createTextAttributesKey("SQL_DATABASE_OBJECT", DefaultLanguageHighlighterColors.CLASS_NAME);
  TextAttributesKey SQL_SYNTHETIC_ENTITY = createTextAttributesKey("SQL_SYNTHETIC_ENTITY", DefaultLanguageHighlighterColors.PREDEFINED_SYMBOL);
  TextAttributesKey SQL_EXTERNAL_TOOL    = createTextAttributesKey("SQL_EXTERNAL_TOOL", EditorColors.INJECTED_LANGUAGE_FRAGMENT);
  TextAttributesKey SQL_OUTER_QUERY_COLUMN = createTextAttributesKey("SQL_OUTER_QUERY_COLUMN", SQL_COLUMN);
}
