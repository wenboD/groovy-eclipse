/*******************************************************************************
 * Copyright (c) 2007, 2009 Codehaus.org, SpringSource, and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Unattributed        - Initial API and implementation
 *     Andrew Eisenberg - modified for Groovy Eclipse 2.0
 *******************************************************************************/
package org.codehaus.groovy.eclipse.core.types;

public class Parameter extends Type {
	Method method;
	
	public Parameter(String signature, String name) {
		super(signature, 0, name);
	}

	public int getType() {
		return Type.PARAMETER;
	}
	
	public Method getMethod() {
		return method;
	}
}
