/*
 * Copyright 2000-2015 JetBrains s.r.o.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.intellij.sql.formatter.settings;

import org.intellij.lang.annotations.MagicConstant;
import org.jetbrains.annotations.NonNls;

/**
 * @author Leonid Bushuev
 */
public interface SqlCodeStyleConst {

  /**
   * Name of the code style preview file — for files with this name formatter doesn't select a style related to data sources.
   */
  @NonNls
  String PREVIEW_FILE_NAME = "preview\u22C8style.sql";


  // @formatter:off

  /// COMMON STUFF \\\

  int COMMONLY  = -1;
  int AS_IS     = 0;

  int ADD       = 1;
  int REMOVE    = 2;

  int[] VALUES_AS_IS_ADD_REMOVE = {AS_IS, ADD, REMOVE};

  @MagicConstant(intValues = {AS_IS, ADD, REMOVE})
  @interface AddRemoveAsIsMagicValues {}


  /// CASING \\\

  int DO_NOT_CHANGE  = 0; // the same as AS_IS
  int TO_LOWER       = 1;
  int TO_UPPER       = 2;
  int AS_KEYWORDS    = 3;
  int AS_IDENTIFIERS = 4;
  int TO_TITLE       = 5;

  int[] TOKEN_CASE =     {DO_NOT_CHANGE, TO_LOWER, TO_UPPER, TO_TITLE};
  int[] TOKEN_CASE_BLT = {DO_NOT_CHANGE, AS_KEYWORDS, AS_IDENTIFIERS, TO_LOWER, TO_UPPER};
  int[] TOKEN_CASE_ALL = {DO_NOT_CHANGE, AS_KEYWORDS, AS_IDENTIFIERS, TO_LOWER, TO_UPPER, TO_TITLE};
  int[] ALIAS_CASE_EXT = {DO_NOT_CHANGE, AS_IDENTIFIERS, TO_LOWER, TO_UPPER, TO_TITLE};

  @MagicConstant(intValues = {DO_NOT_CHANGE, TO_LOWER, TO_UPPER, TO_TITLE, AS_KEYWORDS, AS_IDENTIFIERS})
  @interface IdentifierCase {}


  /// QUOTATION \\\

  int UNQUOTE = 1;
  int QUOTE   = 2;

  int QUOTE_AUTO              = 0;
  int QUOTE_DOUBLE_QUOTE_AUTO = 10;
  int QUOTE_DOUBLE_QUOTE_ONLY = 11;
  int QUOTE_BRACKET_AUTO      = 20;
  int QUOTE_BRACKET_ONLY      = 21;
  int QUOTE_GRAVE_ACCENT_AUTO = 30;
  int QUOTE_GRAVE_ACCENT_ONLY = 31;

  int[] QUOTE_UNQUOTE_OPTIONS = {DO_NOT_CHANGE, UNQUOTE, QUOTE};
  int[] QUOTE_TYPE_ALL_OPTIONS = {QUOTE_AUTO, QUOTE_DOUBLE_QUOTE_AUTO, QUOTE_DOUBLE_QUOTE_ONLY, QUOTE_BRACKET_AUTO, QUOTE_BRACKET_ONLY, QUOTE_GRAVE_ACCENT_AUTO, QUOTE_GRAVE_ACCENT_ONLY};
  int[] QUOTE_TYPE_BR_OPTIONS = {QUOTE_AUTO, QUOTE_DOUBLE_QUOTE_AUTO, QUOTE_DOUBLE_QUOTE_ONLY, QUOTE_BRACKET_AUTO, QUOTE_BRACKET_ONLY};
  int[] QUOTE_TYPE_GA_OPTIONS = {QUOTE_AUTO, QUOTE_DOUBLE_QUOTE_AUTO, QUOTE_DOUBLE_QUOTE_ONLY, QUOTE_GRAVE_ACCENT_AUTO, QUOTE_GRAVE_ACCENT_ONLY};


  // OPENING PARENTHESIS

  int OPENING_SAME       = 1;
  int OPENING_UNINDENT   = 2;
  int OPENING_ALIGN      = 3;
  int OPENING_INDENT     = 4;

  @MagicConstant(intValues = {AS_IS, OPENING_SAME, OPENING_UNINDENT, OPENING_ALIGN, OPENING_INDENT})
  @interface OpeningMagicValues {}

  int[] OPENING_VALUES_CRT = {AS_IS, OPENING_SAME, OPENING_ALIGN, OPENING_INDENT};

  // CONTENT INDENTATION

  int CONTENT_SAME_ALIGNED     = 1;
  int CONTENT_WRAPPED_EGYPT    = 2;
  int CONTENT_WRAPPED_ALIGNED  = 3;
  int CONTENT_WRAPPED_INDENTED = 4;

  @MagicConstant(intValues = {AS_IS, CONTENT_SAME_ALIGNED, CONTENT_WRAPPED_EGYPT, CONTENT_WRAPPED_ALIGNED, CONTENT_WRAPPED_INDENTED})
  @interface ContentMagicValues {}

  int[]    CONTENT_VALUES       = {AS_IS, CONTENT_SAME_ALIGNED, CONTENT_WRAPPED_EGYPT, CONTENT_WRAPPED_ALIGNED, CONTENT_WRAPPED_INDENTED};

  // CLOSING PARENTHESIS

  int CLOSING_SAME            = 1;
  int CLOSING_EGYPT           = 2;
  int CLOSING_UNDER_BEGIN     = 3;
  int CLOSING_UNDER_OPENING   = 4;
  int CLOSING_UNDER_OPENING_R = 5;
  int CLOSING_UNDER_ELEMENT   = 6;

  @MagicConstant(intValues = {AS_IS, CLOSING_SAME, CLOSING_EGYPT, CLOSING_UNDER_BEGIN, CLOSING_UNDER_OPENING, CLOSING_UNDER_OPENING_R, CLOSING_UNDER_ELEMENT})
  @interface ClosingMagicValues {}

  int[]    CLOSING_VALUES_FROM_BEGIN = {AS_IS,           CLOSING_SAME,                CLOSING_UNDER_BEGIN, CLOSING_UNDER_OPENING, /*CLOSING_UNDER_OPENING_R,*/ CLOSING_UNDER_ELEMENT};
  int[]    CLOSING_VALUES_EGYPT      = {AS_IS,           CLOSING_SAME, CLOSING_EGYPT, CLOSING_UNDER_BEGIN, CLOSING_UNDER_OPENING, /*CLOSING_UNDER_OPENING_R,*/ CLOSING_UNDER_ELEMENT};

  // ELEMENTS COMMA/SEPARATOR

  int EL_COMMA_1ST    = 1;
  int EL_COMMA_LAST   = 2;
  int EL_COMMA_EGYPT  = 4;  // is used with multi-row VALUES

  @MagicConstant(intValues = {COMMONLY, AS_IS, EL_COMMA_1ST, EL_COMMA_LAST})
  @interface CommaMagicValues {}

  @MagicConstant(intValues = {COMMONLY, AS_IS, EL_COMMA_1ST, EL_COMMA_LAST, EL_COMMA_EGYPT})
  @interface CommaPlusMagicValues {}


  int[] EL_COMMA_VALUES   = {AS_IS, EL_COMMA_1ST, EL_COMMA_LAST};
  int[] EL_COMMA_VALUES_C = {COMMONLY, AS_IS, EL_COMMA_1ST, EL_COMMA_LAST};
  int[] EL_COMMA_VALUES_M = {COMMONLY, AS_IS, EL_COMMA_1ST, EL_COMMA_EGYPT, EL_COMMA_LAST};

  // ELEMENTS WRAP

  int EL_CHOP      = 1;  // chop always
  int EL_CHOP_LONG = 2;  // chop if long
  int EL_WRAP      = 3;  // wrap

  @MagicConstant(intValues = {AS_IS, EL_CHOP, EL_CHOP_LONG, EL_WRAP})
  @interface WrapMagicValues {}

  @MagicConstant(intValues = {AS_IS, EL_CHOP, EL_CHOP_LONG, EL_WRAP})
  @interface Wrap2MagicValues {}

  int[]    EL_WRAP_VALUES   = {AS_IS, EL_CHOP, EL_CHOP_LONG, EL_WRAP};
  int[]    EL_WRAP_VALUES_2 = {AS_IS, EL_CHOP, EL_CHOP_LONG};


  // SECTION FIRST WORD

  int QUERY_SECTION_1ST_WORD_ALIGN_LEFT        = 1;
  int QUERY_SECTION_1ST_WORD_ALIGN_LEFT_INDENT = 2;
  int QUERY_SECTION_1ST_WORD_ALIGN_RIGHT       = 8;

  int[] QUERY_SECTION_1ST_WORD_ALIGN_VALUES = {AS_IS, QUERY_SECTION_1ST_WORD_ALIGN_LEFT, QUERY_SECTION_1ST_WORD_ALIGN_LEFT_INDENT, QUERY_SECTION_1ST_WORD_ALIGN_RIGHT};


  // ELEMENTS LINE

  int EL_SAME      = 1;    // on the same line
  int EL_INDENT    = 101;  // indent on the new line
  int EL_INDENT_2  = 102;  // indent on the new line if 2 or more

  int[] EL_COMMON_LINE_VALUES  = {AS_IS, EL_SAME, EL_INDENT};
  int[] EL_SECTION_LINE_VALUES = {COMMONLY, AS_IS, EL_SAME, EL_INDENT};

  @MagicConstant(intValues = {COMMONLY, AS_IS, EL_SAME, EL_INDENT})
  @interface ElementsLineMagicValues {}



  /// QUERY ITSELF \\

  int QUERY_IN_ONE_STRING_NO         = 1;
  int QUERY_IN_ONE_STRING_INNER_ONLY = 2;
  int QUERY_IN_ONE_STRING_YES        = 3;

  int[] QUERY_IN_ONE_STRING_VALUES = { AS_IS, QUERY_IN_ONE_STRING_NO, QUERY_IN_ONE_STRING_INNER_ONLY, QUERY_IN_ONE_STRING_YES };


  /// SUBQUERY \\\

  @Deprecated int SUBQUERY_R_PAR_ALIGN_TO_L_PAR   = 1;



  /// SECTION SELECT \\\

  int[] SELECT_KEEP_N_ITEMS_IN_LINE_VALUES = {0, 1, 2, 3, 4, 5, 6, 7};
  String[] SELECT_KEEP_N_ITEMS_IN_LINE_NAMES = {"0", "1", "2", "3", "4", "5", "6", "7"};

  int[] SELECT_USE_AS_VALUES = {AS_IS, ADD, REMOVE};


  /// SECTION FROM \\\

  int FROM_ONLY_JOIN_INDENT_AS_USUAL        = -1;
  int FROM_ONLY_JOIN_INDENT_UNINDENT_LITE   = 1;
  int FROM_ONLY_JOIN_INDENT_UNINDENT_STRONG = 2;

  @MagicConstant(intValues = {FROM_ONLY_JOIN_INDENT_AS_USUAL, FROM_ONLY_JOIN_INDENT_UNINDENT_LITE, FROM_ONLY_JOIN_INDENT_UNINDENT_STRONG})
  @interface FromOnlyJoinIndentValues {}

  int[] FROM_ONLY_JOIN_INDENT_VALUES = {FROM_ONLY_JOIN_INDENT_AS_USUAL, FROM_ONLY_JOIN_INDENT_UNINDENT_LITE, FROM_ONLY_JOIN_INDENT_UNINDENT_STRONG};


  int FROM_PLACE_ON_JOIN             = 10;
  int FROM_PLACE_ON_JOIN_INDENT      = 11;
  int FROM_PLACE_ON_JOIN_INDENT_CONT = 12;
  int FROM_PLACE_ON_TABLE            = 20;
  int FROM_PLACE_ON_TABLE_INDENT     = 21;

  @MagicConstant(intValues = {FROM_PLACE_ON_JOIN, FROM_PLACE_ON_JOIN_INDENT, FROM_PLACE_ON_JOIN_INDENT_CONT, FROM_PLACE_ON_TABLE, FROM_PLACE_ON_TABLE_INDENT})
  @interface FromPlaceOnValues {}

  int[] FROM_PLACE_ON_VALUES = {FROM_PLACE_ON_JOIN, FROM_PLACE_ON_JOIN_INDENT, FROM_PLACE_ON_JOIN_INDENT_CONT, FROM_PLACE_ON_TABLE, FROM_PLACE_ON_TABLE_INDENT};


  /// EXPRESSIONS \\\

  int CORTEGE_CLOSING_UNDER_OPENING = 1;
  int CORTEGE_CLOSING_UNDER_ITEM    = 2;
  int CORTEGE_CLOSING_AT_THE_END    = 9;

  int EXPR_CASE_END_ALIGN_CASE = 1;
  int EXPR_CASE_END_ALIGN_WHEN = 2;
  int EXPR_CASE_END_TO_THE_END = 9;

  int[] EXPR_CASE_END_ALIGN_VALUES = {AS_IS, EXPR_CASE_END_ALIGN_CASE, EXPR_CASE_END_ALIGN_WHEN, EXPR_CASE_END_TO_THE_END};


  /// DEPRECATED \\\

  @Deprecated int NEVER            = 2;

  // @formatter:on

}
