/*
 * Copyright 2000-2017 JetBrains s.r.o.
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
package com.intellij.database.model;

import com.intellij.openapi.util.text.StringUtilRt;
import com.intellij.util.containers.ContainerUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.EnumSet;
import java.util.Set;

/**
 * Event a trigger is fired on.
 *
 * @author Leonid Bushuev
 */
public enum TrigEvent {
  SELECT('S'),
  INSERT('I'),
  UPDATE('U'),
  DELETE('D'),
  TRUNCATE('T');

  public final char code;

  TrigEvent(char code) {
    this.code = code;
  }

  @Nullable
  public static TrigEvent of(char code) {
    return ContainerUtil.find(values(), v -> StringUtilRt.charsEqualIgnoreCase(code, v.code));
  }

  public static @NotNull Set<TrigEvent> of(@Nullable String codes) {
    if (codes == null || codes.isEmpty()) return Collections.emptySet();
    EnumSet<TrigEvent> events = EnumSet.noneOf(TrigEvent.class);
    for (int i = 0, n = codes.length(); i < n; i++) {
      TrigEvent event = of(codes.charAt(i));
      if (event != null) events.add(event);
    }
    return events;
  }

}
