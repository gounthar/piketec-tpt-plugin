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
package com.piketec.tpt.api.constants.assessments;

/**
 * For further information, please refer to the User Guide or Assessment Manual, section TPT
 * Assessment Type 'minmax'.
 * 
 * @author Copyright (c) 2014-2025 Synopsys Inc. - MIT License (MIT) - All rights reserved
 */
public interface MinMax extends BasicAssessment {

  public static final String EXPORT_DIFF = "export-range";

  public static final String EXPORT_MIN = "export-min";

  public static final String EXPORT_MAX = "export-max";

  public static final String RESULT_SUFFIX = "result-suffix";

  public static final String MIN_SUFFIX = "min-suffix";

  public static final String MAX_SUFFIX = "max-suffix";

  public static final String DISABLED = "disabled";

  public static final String CHANNEL = "channel";

  public static final String MIN_VAL = "min";

  public static final String MAX_VAL = "max";

  public static final String ROWS = "definitions";

  public static final String USE_PLATFORM_MAPPING_FOR_BOUNDS = "use-platform-mapping-for-bounds";

  public static final String MAPPING_FOR_BOUNDS = "mapping-for-bounds";

  public static final String ACCEPT_IF_SIGNAL_VALUES_EXACTLY_REACHED =
      "accept-if-signal-values-exactly-reached";

  public static final String COMPARE_ON_CHANGE = "compare-on-change";
}
