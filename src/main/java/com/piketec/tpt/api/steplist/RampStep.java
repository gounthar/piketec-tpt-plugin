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
package com.piketec.tpt.api.steplist;

import java.rmi.RemoteException;

/**
 * A step which provides the possibility to ramp the value of a channel.
 * 
 * @author Copyright (c) 2014-2025 Synopsys Inc. - MIT License (MIT) - All rights reserved
 */
public interface RampStep extends Step {

  /**
   * @return the declaration to be ramped.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public String getDeclaration() throws RemoteException;

  /**
   * Sets the declaration to be ramped.
   * 
   * @param declaration
   *          channel/paramter to ramp
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public void setDeclaration(String declaration) throws RemoteException;

  /**
   * @return the target value.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public String getTarget() throws RemoteException;

  /**
   * Sets the target value.
   * 
   * @param value
   *          target value
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public void setTarget(String value) throws RemoteException;

  /**
   * @return the gradient per second.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public String getGradient() throws RemoteException;

  /**
   * Sets the gradient per second.
   * 
   * @param grad
   *          gradient
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public void setGradient(String grad) throws RemoteException;
}
