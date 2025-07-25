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

import java.rmi.RemoteException;

/**
 * The object represents a list of {@link Scenario} and <code>ScenarioGroup</code> objects. Together
 * with the sub-groups it represents a tree.
 * <p>
 * In TPT, it represents both the variant groups as well as the test case groups. Create new
 * <code>ScenarioGroups</code> via {@link Testlet#createVariantGroup(String, ScenarioGroup)}.
 * </p>
 * 
 * @see Project#getTopLevelTestlet()
 *
 * @author Copyright (c) 2014-2025 Synopsys Inc. - MIT License (MIT) - All rights reserved
 */
public interface ScenarioGroup extends ScenarioOrGroup, AccessList<ScenarioOrGroup> {

  /**
   * Returns the <u>CONTENTS</u> of the top level {@link ScenarioGroup} of this <code>Testlet</code>
   * as list of {@link ScenarioOrGroup}.
   * 
   * @return The list of {@link ScenarioOrGroup} that are contained in the top level group.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public RemoteList<ScenarioOrGroup> getScenariosOrGroups() throws RemoteException;

}
