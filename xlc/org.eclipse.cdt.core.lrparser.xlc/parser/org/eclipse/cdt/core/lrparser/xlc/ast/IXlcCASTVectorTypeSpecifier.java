/*******************************************************************************
 * Copyright (c) 2009 IBM Corporation and others.
 *
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.cdt.core.lrparser.xlc.ast;

import org.eclipse.cdt.core.dom.ast.c.ICASTDeclSpecifier;

public interface IXlcCASTVectorTypeSpecifier extends ICASTDeclSpecifier {

	public boolean isPixel();

	public void setPixel(boolean isPixel);

	public boolean isBool();

	public void setBool(boolean isBool);

}
