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
package com.piketec.tpt.api;

/**
 * This exception indicates a misuse of the TPT API.
 * 
 * @author Copyright (c) 2014-2025 Synopsys Inc. - MIT License (MIT) - All rights reserved
 */
public class ApiException extends RuntimeException {

  private static final long serialVersionUID = -8020652982219750761L;

  /**
   * Creates a new <code>ApiException</code> with the given message.
   * 
   * @param message
   *          The message of the exception
   * 
   * @see Throwable#Throwable(String)
   */
  public ApiException(String message) {
    super(message);
  }

  /**
   * Only use with inner exceptions that can be searialized <br>
   * Creates a new <code>ApiException</code> with the given cause.
   * 
   * @param cause
   *          the cause
   * 
   * @see Throwable#Throwable(Throwable)
   */
  public ApiException(Throwable cause) {
    super(cause);
  }
}
