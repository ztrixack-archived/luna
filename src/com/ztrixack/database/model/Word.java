package com.ztrixack.database.model;

public class Word implements SQLModel {

	private int id;
	private String word;
	private int type;
	private int status;
	private String created_at;
	private long counter = 0;

	// constructors
	public Word() {
	}

	public Word(String word, int status) {
		this.word = word;
		this.status = status;
	}

	public Word(int id, String word, int status) {
		this.id = id;
		this.word = word;
		this.status = status;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getWord() {
		setCounter(getCounter() + 1);
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getCreatedAt() {
		return created_at;
	}

	public void setCreatedAt(String createdAt) {
		this.created_at = createdAt;
	}

	public long getCounter() {
		return counter;
	}

	public void setCounter(long counter) {
		this.counter = counter;
	}

}
