/* 
 * Copyright (C) 2008, 2009 Martin Kempf, Reto Kleeb, Michael Klenk
 *
 * IFS Institute for Software, HSR Rapperswil, Switzerland
 * http://ifs.hsr.ch/
 *
 */
package org.codehaus.groovy.eclipse.refactoring.core;

import java.util.HashMap;
import java.util.Map;

import org.codehaus.groovy.eclipse.refactoring.core.documentProvider.IGroovyDocumentProvider;
import org.codehaus.groovy.eclipse.refactoring.core.documentProvider.WorkspaceDocumentProvider;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.CompositeChange;
import org.eclipse.ltk.core.refactoring.TextFileChange;
import org.eclipse.text.edits.MalformedTreeException;
import org.eclipse.text.edits.MultiTextEdit;

/**
 * 
 * @author martin
 *
 */

public class GroovyChange {
	
	private Map<IGroovyDocumentProvider, MultiTextEdit> edits = new HashMap<IGroovyDocumentProvider, MultiTextEdit>();
	private String name;
	private CompositeChange javaChange = new CompositeChange("Java changes");
	
	public GroovyChange(String name) {
		this.name = name;
	}
	
	/**
	 * Creates the whole change from the map. This method can only be called from the Eclipse and not from
	 * the testenvironment
	 * @return Changeobject that contains the edits
	 */
	public Change createChange() {
		CompositeChange change = new CompositeChange(name);
		for (IGroovyDocumentProvider docProvider : edits.keySet()) {
			WorkspaceDocumentProvider workspaceProvider = (WorkspaceDocumentProvider)docProvider;
			MultiTextEdit multiEdit = edits.get(workspaceProvider);
			if (multiEdit.hasChildren()) {
				TextFileChange textFileChange = new TextFileChange(workspaceProvider.getName(),workspaceProvider.getFile());
				textFileChange.setEdit(multiEdit);
				change.add(textFileChange);
			}
		}
		//change.add(javaChange);
		return change;
	}
	
	public void addChange(Change change) {
		this.javaChange.add(change);
	}
	
	public void addEdit(IGroovyDocumentProvider docProvider, MultiTextEdit edit) {
		edits.put(docProvider, edit);
	}
	
	public void performChanges() throws MalformedTreeException, BadLocationException {
		for (IGroovyDocumentProvider docProvider : edits.keySet()) {
			MultiTextEdit multiEdit = edits.get(docProvider);
			multiEdit.apply(docProvider.getDocument());
		}
	}

	public void addEdit(IMultiEditProvider textEdit) {
		edits.put(textEdit.getDocProvider(),textEdit.getMultiTextEdit());
	}

}
