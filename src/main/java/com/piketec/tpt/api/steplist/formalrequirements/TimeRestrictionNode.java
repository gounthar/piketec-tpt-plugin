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
package com.piketec.tpt.api.steplist.formalrequirements;

import java.rmi.RemoteException;

import com.piketec.tpt.api.ApiException;

/**
 * This {@link ConditionTreeNode} applies the given {@link TimeRestrictionType} to the previously
 * defined intervals.
 * 
 * @author Copyright (c) 2014-2025 Synopsys Inc. - MIT License (MIT) - All rights reserved
 * 
 */
public interface TimeRestrictionNode extends ConditionTreeNode {

  /**
   * The possible when types.
   */
  enum TimeRestrictionType {
    IGNORE_FIRST, IGNORE_LAST, USE_FIRST, USE_LAST;
  }

  /**
   * Returns the type of time restriction.
   * 
   * @return the time restriction type
   * @throws RemoteException
   *           remote communication problem
   */
  public TimeRestrictionType getTimeRestriction() throws RemoteException;

  /**
   * Sets the type of time restriction.
   * 
   * @param timeRestriction
   *          the type of time restriction
   * @throws RemoteException
   *           remote communication problem
   * @throws ApiException
   *           if timeRestriction is {@code null}
   */
  public void setTimeRestriction(TimeRestrictionType timeRestriction)
      throws RemoteException, ApiException;

  /**
   * The time component which belongs to the type of the time restriction.
   * 
   * @return The time component which belongs to the type of the time restriction.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public String getTime() throws RemoteException;

  /**
   * Sets the time component which belongs to the type of the time restriction.
   * 
   * @param time
   *          the time
   * 
   * @throws RemoteException
   *           remote communication problem
   * @throws ApiException
   *           if the time is {@code null}
   */
  public void setTime(String time) throws RemoteException, ApiException;

}
