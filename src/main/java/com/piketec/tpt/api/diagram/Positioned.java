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
package com.piketec.tpt.api.diagram;

import java.awt.Point;
import java.rmi.RemoteException;

import com.piketec.tpt.api.TptRemote;

/**
 * The class represents an object with a position (x- and y- coordinate) in the diagram like a state
 * or a junction.
 * 
 * @author Copyright (c) 2014-2025 Synopsys Inc. - MIT License (MIT) - All rights reserved
 */
public interface Positioned extends TptRemote {

  /**
   * Return the current position of the object as a two-dimensional point.
   * 
   * @return A point representing x-and y- coordinate of this object.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public Point getPosition() throws RemoteException;

  /**
   * Set the position of a object.
   * 
   * @param p
   *          A {@link Point} representing the desired position of the object in the two-dimensional
   *          drawing area.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public void setPosition(Point p) throws RemoteException;

  /**
   * Set the position of a object.
   * 
   * @param x
   *          the x value of the desired position of the object in the two-dimensional drawing area.
   * @param y
   *          the y value of the desired position of the object in the two-dimensional drawing area.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public void setPosition(int x, int y) throws RemoteException;

}
