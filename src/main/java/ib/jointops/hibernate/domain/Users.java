package ib.jointops.hibernate.domain;

import java.util.Set;
import java.util.HashSet;

public class Users {
	
	private Long id;
	private Set<Task> tasks = new HashSet<Task>();
	private String name;
	
	public Users() {}
	
	public Long getId() {
		return id;
	}
	@SuppressWarnings("unused")
	private void setId(Long id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	protected Set<Task> getTasks() {
		return tasks;
	}
	protected void setTasks(Set<Task> tasks) {
		this.tasks = tasks;
	}
	public void addTaskToUser(Task task) {
		task.getWho().add(this);
		this.getTasks().add(task);
	}
	public void removeTaskFromUser(Task task) {
		task.getWho().remove(this);
		this.getTasks().remove(task);
	}
	@Override
	public boolean equals(Object other) {
		if (this == other) return true;
		if (!(other instanceof Users)) return false;
		
		final Users anUser = (Users) other;
		
		if (!(anUser.getId().equals(this.getId()))) return false;
		
		return true;
	}

}
