package com.kef.org.rest.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

@Entity
@Table(name = "volunteer_rating")
@NamedQueries({
@NamedQuery(name="VolunteerRating.getAvgRating",query="select avg(rating) from VolunteerRating where idvolunteer=?1 group by idvolunteer"),
@NamedQuery(name="VolunteerRating.getRatingByAdmin_id",query="select v from VolunteerRating v where v.idvolunteer=?1 and v.adminId=?2 order By ratedOn desc ")
})
public class VolunteerRating {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "RATING_ID")

	private Integer ratingId;

	@Column(name = "IDVOLUNTEER")
	private Integer idvolunteer;

	@Column(name = "ADMIN_ID")
	private Integer adminId;
	
	@Column(name="VOLUNTEER_NAME")
	private String volunteerName;
	
	@Column(name="ADMIN_NAME")
	private String adminName;
	
	@Column(name="RATING")
	private String rating;
	
	@Column(name = "CREATED_DATE",columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private LocalDateTime ratedOn;

	public Integer getRatingId() {
		return ratingId;
	}

	public void setRatingId(Integer ratingId) {
		this.ratingId = ratingId;
	}

	public Integer getIdvolunteer() {
		return idvolunteer;
	}

	public void setIdvolunteer(Integer idvolunteer) {
		this.idvolunteer = idvolunteer;
	}

	public Integer getAdminId() {
		return adminId;
	}

	public void setAdminId(Integer adminId) {
		this.adminId = adminId;
	}

	public String getVolunteerName() {
		return volunteerName;
	}

	public void setVolunteerName(String volunteerName) {
		this.volunteerName = volunteerName;
	}

	public String getAdminName() {
		return adminName;
	}

	public void setAdminName(String adminName) {
		this.adminName = adminName;
	}

	public String getRating() {
		return rating;
	}

	public void setRating(String rating) {
		this.rating = rating;
	}

	public LocalDateTime getRatedOn() {
		return ratedOn;
	}

	public void setRatedOn(LocalDateTime ratedOn) {
		this.ratedOn = ratedOn;
	}
	

}
