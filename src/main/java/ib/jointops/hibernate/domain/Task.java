package ib.jointops.hibernate.domain;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class Task {
	
	private Long id;
	private Set<Users> who = new HashSet<Users>();
	private String description;
	private Date dueDate;
	private Categories category;
	
	public Task() {}
	
	public Long getId() {
		return id;
	}
	@SuppressWarnings("unused")
	private void setId(Long id) {
		this.id = id;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Categories getCategory() {
		return category;
	}
	public void setCategory(Categories category) {
		this.category = category;
	}
	public Date getDueDate() {
		return dueDate;
	}
	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}
	
	public Set<Users> getWho() {
		return who;
	}
	protected void setWho(Set<Users> who) {
		this.who = who;
	}
	public void addUserToTask(Users user) {
		this.getWho().add(user);
		user.getTasks().add(this);
	}
	public void removeUserFromTask(Users user) {
		this.getWho().remove(user);
		user.getTasks().remove(this);
	}
	
	@Override
	public boolean equals(Object other) {
		if (this == other) return true;
		if (!(other instanceof Task)) return false;
		
		final Task aTask = (Task) other;
		
		if (!(aTask.getId().equals(this.getId()))) return false;
		
		return true;
	}
	

}
