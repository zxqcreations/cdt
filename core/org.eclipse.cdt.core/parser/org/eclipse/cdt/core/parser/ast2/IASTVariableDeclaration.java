/**********************************************************************
 * Copyright (c) 2004 IBM Corporation and others.
 * All rights reserved.   This program and the accompanying materials
 * are made available under the terms of the Common Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/cpl-v10.html
 * 
 * Contributors: 
 * IBM - Initial API and implementation
 **********************************************************************/
package org.eclipse.cdt.core.parser.ast2;

/**
 * Introduces a variable into scope.
 * 
 * @author Doug Schaefer
 */
public interface IASTVariableDeclaration extends IASTDeclaration {

	/**
	 * @return the type of the variable
	 */
	public IASTType getType();
	
	public void setType(IASTType type);
	
	/**
	 * @return the variable being introduced into scope
	 */
	public IASTVariable getVariable();
	
	public void setVariable(IASTVariable variable);
	
}
