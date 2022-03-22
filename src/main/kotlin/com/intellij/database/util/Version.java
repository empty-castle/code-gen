package com.intellij.database.util;

import com.intellij.openapi.util.Comparing;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.util.ArrayUtil;
import com.intellij.util.MathUtil;
import com.intellij.util.containers.BidirectionalMap;
import com.intellij.util.containers.ContainerUtil;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public final class Version implements Comparable<Version>, Serializable {
  private static final BidirectionalMap<String, Integer> SPECIAL_VALUES = new BidirectionalMap<>();

  static {
    SPECIAL_VALUES.put("alpha", -30);
    SPECIAL_VALUES.put("a", -30);
    SPECIAL_VALUES.put("beta", -20);
    SPECIAL_VALUES.put("b", -20);
    SPECIAL_VALUES.put("rc", -10);
  }

  @NotNull
  private final int[] myElements;

  @Contract("!null->!null;null->null")
  public static Version of(@Nullable String string) {
    if (string == null) return null;
    List<Integer> res = new ArrayList<>(5);
    String[] elements = string.split("[.,\\-_ ]|(?<=\\d)(?!\\d)|(?<!\\d)(?=\\d)");
    for (String element : elements) {
      String e = StringUtil.toLowerCase(element.trim());
      if (e.isEmpty()) continue;
      Integer value = SPECIAL_VALUES.get(e);
      if (value == null) {
        try {
          value = Integer.parseInt(e);
        }
        catch (NumberFormatException nfe) {
          throw new IllegalArgumentException("Failed to parse version \"" + string + "\"", nfe);
        }
      }
      res.add(value);
    }
    if (res.isEmpty()) throw new IllegalArgumentException("Failed to parse version \""+string+"\"");
    return new Version(ArrayUtil.toIntArray(res));
  }

  @Nullable
  public static Version parse(@Nullable String string) {
    try {
      return string == null ? null : of(string);
    }
    catch (IllegalArgumentException e) {
      return null;
    }
  }

  public static Version of(@NotNull int... elements) {
    return new Version(elements);
  }

  private Version(@NotNull final int[] elements) {
    myElements = elements;
  }

  public int get(int index) {
    return index < myElements.length ? myElements[index] : 0;
  }

  public int size() {
    return myElements.length;
  }

  public boolean eqCoarse(@NotNull int... elements) {
    return truncate(elements.length).compareTo(elements) == 0;
  }

  public boolean eqCoarse(@NotNull Version v) {
    return eqCoarse(v.myElements);
  }

  @NotNull
  public Version truncate(int n) {
    if (myElements.length <= n) return this;
    if (n < 0) throw new IllegalArgumentException("Negative desired size: " + n);
    int[] newElements = Arrays.copyOf(myElements, n);
    return new Version(newElements);
  }

  @NotNull
  public Version truncateNegatives() {
    for (int k = 0, n = myElements.length; k < n; k++) {
      if (myElements[k] < 0) return truncate(k);
    }
    return this;
  }

  @NotNull
  public Version decStep() {
    int len = myElements.length;
    int[] newElements = new int[len + 1];
    System.arraycopy(myElements, 0, newElements, 0, len);
    newElements[len] = Integer.MIN_VALUE;
    return of(newElements);
  }

  @NotNull
  public Version incStep() {
    int len = myElements.length;
    int[] newElements = new int[len + 4];
    System.arraycopy(myElements, 0, newElements, 0, len);
    newElements[len] = 0;
    newElements[len + 1] = 0;
    newElements[len + 2] = 0;
    newElements[len + 3] = 1;
    return of(newElements);
  }

  @Override
  public int compareTo(@NotNull Version that) {
    if (this == that) return 0;
    return compareTo(that.myElements);
  }

  public int compareTo(int... that) {
    int res = 0;
    for (int i = 0, tLen = that.length, e = Math.max(size(), tLen); i < e && res == 0; i++) {
      res = Comparing.compare(get(i), i >= tLen ? 0 : that[i]);
    }
    return res;
  }

  public boolean isOrGreater(int... than) {
    return compareTo(than) >= 0;
  }
  public boolean isOrGreater(@NotNull Version than) {
    return compareTo(than) >= 0;
  }
  public boolean less(int... than) {
    return compareTo(than) < 0;
  }
  public boolean isOrLess(int... than) {
    return compareTo(than) <= 0;
  }
  public boolean less(@NotNull Version than) {
    return compareTo(than) < 0;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Version ov = (Version)o;
    for (int i = 0, e = Math.max(size(), ov.size()); i < e; ++i) {
      if (get(i) != ov.get(i)) return false;
    }
    return true;
  }

  @Override
  public int hashCode() {
    int hc = 0; //ignores trailing 0's
    for (int i = myElements.length - 1; i >= 0; --i) {
      hc = myElements[i] + 31 * hc;
    }
    return hc;
  }

  @NotNull
  public String toString() {
    if (this == INFINITY) return "\u221e";
    return toString(2,100);
  }

  @NotNull
  public String toString(int from, int to) {
    int n = MathUtil.clamp(myElements.length, from, to);
    StringBuilder b = new StringBuilder();
    char delimiter = '\0';
    for (int i = 0; i < n; i++) {
      int item = get(i);
      String special = ContainerUtil.getFirstItem(SPECIAL_VALUES.getKeysByValue(item));
      if (delimiter != '\0') b.append(special != null ? ' ' : delimiter);
      if (special != null) {
        b.append(special);
        delimiter = ' ';
      }
      else {
        b.append(item);
        delimiter = '.';
      }
    }
    return b.toString();
  }

  public static final Version ZERO = of(0);
  public static final Version INFINITY = of(Integer.MAX_VALUE);
}
