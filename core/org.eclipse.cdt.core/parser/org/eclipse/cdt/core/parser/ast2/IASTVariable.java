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
 * Represents a variable definition.
 * 
 * @author Doug Schaefer
 */
public interface IASTVariable {

	/**
	 * @return the declarations that introduce this variable into a scope
	 */
	public int numDeclarations();
	public IASTVariableDeclaration getDeclaration(int i);

	/**
	 * @return the references to this variable in this AST
	 */
	public int numReferences();
	public IASTVariableReference getReference(int i);
	
	/**
	 * @return the expression that initializes the variable when it is
	 * instantiated.
	 */
	public IASTExpression getInitialization();
	
	public void setInitialization(IASTExpression initialization);

}
