/*
 * The MIT License (MIT)
 * 
 * Copyright (c) 2014-2025 Synopsys Inc.
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute,
 * sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT
 * NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package com.piketec.tpt.api.properties;

import java.io.Serializable;
import java.util.Objects;

/**
 * A String property value.
 * 
 * @author Copyright (c) 2014-2025 Synopsys Inc. - MIT License (MIT) - All rights reserved
 */
public class PropertyString implements Property, Serializable {

  static final long serialVersionUID = 1L;

  private final String value;

  /**
   * @param value
   *          the actual String value represented by this property
   */
  public PropertyString(String value) {
    this.value = value;
  }

  /**
   * @return the actual integer value represented by this property
   */
  public String getValue() {
    return value;
  }

  @Override
  public String toString() {
    if (value == null) {
      return "null";
    }
    return value;
  }

  @Override
  public void toString(StringBuffer buffer, String indentation) {
    buffer.append(indentation);
    buffer.append(toString().replaceAll("\\r\\n|\\n", "\n" + indentation));
  }

  @Override
  public int hashCode() {
    return Objects.hash(value);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    PropertyString other = (PropertyString)obj;
    if (!Objects.equals(value, other.value)) {
      return false;
    }
    return true;
  }
}
