/*******************************************************************************
 * Copyright (c) 2018 Red Hat Inc. and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * 		Red Hat Inc. - initial implementation
 *******************************************************************************/
package org.eclipse.cdt.meson.ui.properties;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

public class MesonPropertyCheckbox implements IMesonPropertyPageControl {
	
	private String name;
	private boolean initialValue;
	protected Button checkbox;

	public MesonPropertyCheckbox(Composite composite, String name, boolean initialValue, String tooltip) {
		this.name = name;
		this.initialValue = initialValue;
		checkbox = new Button(composite, SWT.CHECK);
		GridData data = new GridData(GridData.FILL, GridData.FILL, true, false);
		data.horizontalSpan = 2;
		checkbox.setText(name);
		checkbox.setLayoutData(data);
		checkbox.setSelection(initialValue);
		checkbox.setToolTipText(tooltip);
	}



	@Override
	public String getFieldValue() {
		return Boolean.toString(checkbox.getSelection());
	}

	@Override
	public String getFieldName() {
		return name;
	}

	@Override
	public boolean isValueChanged() {
		return checkbox.getSelection() != initialValue;
	}

	@Override
	public boolean isValid() {
		return true;
	}
	
	@Override
	public String getErrorMessage() {
		return null;
	}

}
