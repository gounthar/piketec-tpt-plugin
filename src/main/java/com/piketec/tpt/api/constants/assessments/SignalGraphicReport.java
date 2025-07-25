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
 * Assessment Type 'report_signalgraphic'.
 * 
 * @author Copyright (c) 2014-2025 Synopsys Inc. - MIT License (MIT) - All rights reserved
 */
public interface SignalGraphicReport extends BasicAssessment, SignalFilter {

  /**
   * Possible options for the YAxis settings
   * 
   * @author Copyright (c) 2014-2025 Synopsys Inc. - MIT License (MIT) - All rights reserved
   */
  public interface YAxisOptions {

    public static final String FIXED = "FIXED";

    public static final String AUTOMATIC = "AUTOMATIC";

    public static final String STACKED = "STACKED";
  }

  public static final String ONE_GRAPHIC_PER_SIGNAL = "one-graphic-per-signal";

  public static final String HEIGHT = "height";

  public static final String WIDTH = "width";

  public static final String HEIGHT_BOOL = "height-bool";

  public static final String WIDTH_BOOL = "width-bool";

  public static final String INCLUDE_IN_TABLE_OF_FIGURES = "include-in-table-of-figures";

  public static final String XAXIS_VISIBLE = "xaxis-visible";

  public static final String XGRID_VISIBLE = "xgrid-visible";

  public static final String XAXIS_SCALING = "xaxis-scaling";

  public static final String XAXIS_INTERVALL_FIXED = "xaxis-intervall-fixed";

  public static final String YAXIS_VISIBLE = "yaxis-visible";

  public static final String YGRID_VISIBLE = "ygrid-visible";

  public static final String YAXIS_SCALING = "yaxis-scaling";

  /** Only the value in {@link YAxisOptions} are possible */
  public static final String YAXIS_SETTINGS = "yaxis-settings";

  public static final String XAXIS_INTERVALL_FROM = "xaxis-intervall-from";

  public static final String XAXIS_INTERVALL_TO = "xaxis-intervall-to";

  public static final String YAXIS_INTERVALL_FROM = "yaxis-intervall-from";

  public static final String YAXIS_INTERVALL_TO = "yaxis-intervall-to";

}
