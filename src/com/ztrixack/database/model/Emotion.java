package com.ztrixack.database.model;

public class Emotion {
	private int id;
	private String emotion;

	// constructors
	public Emotion() {

	}

	public Emotion(String emotion) {
		this.emotion = emotion;
	}

	public Emotion(int id, String emotion) {
		this.id = id;
		this.emotion = emotion;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEmotion() {
		return emotion;
	}

	public void setEmotion(String emotion) {
		this.emotion = emotion;
	}

}
