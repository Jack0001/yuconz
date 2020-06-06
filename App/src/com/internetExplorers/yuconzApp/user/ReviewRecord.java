package com.internetExplorers.yuconzApp.user;

public class ReviewRecord {

	private int rID;
	private String username;
	private String reviewer;
	private String secondReviewer;
	private String section;
	private String jobTitle;
	private String summary;
	private String recommendation;
	private String comments;
	private String date_created;
	private String last_edited;
	private int signedReviewee;
	private int signedReviewer;
	private int signedReviewer2;
	private int approved;
	
	/**
	 * Constructor for a review record object built with the results from the SQL
	 */
	public ReviewRecord(int rID, String username, String reviewer, String secondReviewer, String section,
			String jobTitle, String summary, String recommendation, String comments, String date_created,
			String last_edited, int signedReviewee, int signedReviewer, int signedReviewer2, int approved) {
		this.rID = rID;
		this.username = username;
		this.reviewer = reviewer;
		this.secondReviewer = secondReviewer;
		this.section = section;
		this.jobTitle = jobTitle;
		this.summary = summary;
		this.recommendation = recommendation;
		this.comments = comments;
		this.date_created = date_created;
		this.last_edited = last_edited; 
		this.signedReviewee = signedReviewee;
		this.signedReviewer = signedReviewer;
		this.signedReviewer2 = signedReviewer2;
		this.approved = approved;
	}
	
	public int getrID() {
		return rID;
	}

	public String getUsername() {
		return username;
	}

	public String getReviewer() {
		return reviewer;
	}

	public String getSecondReviewer() {
		return secondReviewer;
	}

	public String getSection() {
		return section;
	}

	public String getJobTitle() {
		return jobTitle;
	}

	public String getSummary() {
		return summary;
	}

	public String getRecommendation() {
		return recommendation;
	}

	public String getComments() {
		return comments;
	}

	public String getDate_created() {
		return date_created;
	}

	public String getLast_edited() {
		return last_edited;
	}

	public int getSignedReviewee() {
		return signedReviewee;
	}

	public int getSignedReviewer() {
		return signedReviewer;
	}

	public int getSignedReviewer2() {
		return signedReviewer2;
	}

	public int getApproved() {
		return approved;
	}

}
