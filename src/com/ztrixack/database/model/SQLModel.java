package com.ztrixack.database.model;

public interface SQLModel {

	static enum TYPE {
		NOUN, PRONOUN, VERB, ADVERB, CONJUNCTION, PREPOSITION, INTERJECTION, SPECIFIC
	};

	static final int WORDTYPE_NOUN = 0x01;
	static final int WORDTYPE_PRONOUN = 0x02;
	static final int WORDTYPE_VERB = 0x04;
	static final int WORDTYPE_ADVERB = 0x08;
	static final int WORDTYPE_CONJUNCTION = 0x10;
	static final int WORDTYPE_PREPOSITION = 0x20;
	static final int WORDTYPE_INTERJECTION = 0x40;
	static final int WORDTYPE_SPECIFIC = 0x80;
}
