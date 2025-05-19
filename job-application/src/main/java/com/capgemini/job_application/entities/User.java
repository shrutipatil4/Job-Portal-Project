package com.capgemini.job_application.entities;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "user")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	private Long userId;

	@Column(name = "user_name")
	@NotBlank(message = "UserName must not be blank.")
	private String userName;

	@Column(name = "user_email")
	@NotBlank(message = "Email must not be blank.")
	@Email(message = "Provided mail is not a valid email")
	private String userEmail;

	@Column(name = "phone")
	@NotBlank(message = "Contact number must not be blank.")
	private String phone;

	@Column(name = "password")
	@NotNull
	private String password;

	@Column(name = "address")
	@NotBlank(message = "Address must not be blank.")
	private String address;

	@Column(name = "user_type")
	@NotNull(message = "User Type must not be blank.")
	private String userType;

	@Column(name = "age")
	@Min(value = 18, message = "Age must be at least 18")
	private Integer age;

	@NotNull(message = "Gender must not be blank.")
	@Column(name = "gender")
	private String gender;


	@OneToMany(mappedBy = "user", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
	@JsonManagedReference(value = "user-experience")
	private List<Experience> experiences;

	@OneToMany(mappedBy = "user", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
	@JsonManagedReference(value = "user-qualification")
	private List<Qualification> qualifications;

	@OneToMany(mappedBy = "user", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
	@JsonManagedReference(value = "user-application")
	private List<Application> applications;

	@OneToOne(mappedBy = "user", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
	@JsonManagedReference(value = "user-company")
	private Company company;


	@ManyToMany
	@JoinTable(name = "user_skills", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "skill_id"))
	private List<Skill> skills;


	public User(Long userId, String userName, String userEmail, String phone, String password,
            String address, String userType, Integer age, String gender) {
    this.userId = userId;
    this.userName = userName;
    this.userEmail = userEmail;
    this.phone = phone;
    this.password = password;
    this.address = address;
    this.userType = userType;
    this.age = age;
    this.gender = gender;
}

	

	public User(
		    Long userId,
		    @NotBlank(message = "UserName must not be blank.") String userName,
		    @NotBlank(message = "Email must not be blank.")
		    @Email(message = "{validatedValue} is not a valid email") String userEmail,
		    @NotBlank(message = "Contact number must not be blank.") String phone,
		    @NotNull(message = "Password must not be null.") String password,
		    @NotBlank(message = "Address must not be blank.") String address,
		    @NotNull(message = "User Type must not be blank.") String userType,
		    @Min(value = 18, message = "Age must be at least 18") Integer age,
		    @NotNull(message = "Gender must not be blank.") String gender,
		    List<Qualification> qualifications,
		    List<Experience> experiences,
		    List<Application> applications,
		    Company company,
		    List<Skill> skills
		) {
		    this.userId = userId;
		    this.userName = userName;
		    this.userEmail = userEmail;
		    this.phone = phone;
		    this.password = password;
		    this.address = address;
		    this.userType = userType;
		    this.age = age;
		    this.gender = gender;
		    this.qualifications = qualifications != null ? qualifications : new ArrayList<>();
		    this.experiences = experiences != null ? experiences : new ArrayList<>();
		    this.applications = applications != null ? applications : new ArrayList<>();
		    this.company = company;
		    this.skills = skills != null ? skills : new ArrayList<>();
		}


	public User() {
		super();
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}
	
	public List<Skill> getSkills() {
		return skills;
	}

	public void setSkills(List<Skill> skills) {
		this.skills = skills;
	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", userName=" + userName + ", userEmail=" + userEmail + ", phone=" + phone
				+ ", password=" + password + ", address=" + address + ", userType=" + userType + ", age=" + age
				+ ", gender=" + gender;
	}
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		User user = (User) o;
		return Objects.equals(userId, user.userId);
	}

	@Override
	public int hashCode() {
		return Objects.hash(userId);
	}

}
