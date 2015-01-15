package org.kemricdc.entities;

/**
 * Model of a Person Identifier
 * @author Stanslaus Odhiambo
 *
 */
public class PersonIdentifier {
	private String identifier;
	private IdentifierType identifierType;

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public IdentifierType getIdentifierType() {
		return identifierType;
	}

	public void setIdentifierType(IdentifierType identifierType) {
		this.identifierType = identifierType;
	}

}
