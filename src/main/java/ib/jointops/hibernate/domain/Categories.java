package ib.jointops.hibernate.domain;

import java.util.HashSet;
import java.util.Set;

public class Categories {
	private Long id;
	private Set<Task> tasks = new HashSet<Task>();
	private String categoryName;
	
	public Categories() {}
	
	public Categories(String name) {
		this.categoryName = name;
	}
	
	@SuppressWarnings("unused")
	private void setId(Long id) {
		this.id = id;
	}
	public Long getId() {
		return id;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public Set<Task> getTasks() {
		return tasks;
	}
	public void setTasks(Set<Task> tasks) {
		this.tasks = tasks;
	}
	
	

}
