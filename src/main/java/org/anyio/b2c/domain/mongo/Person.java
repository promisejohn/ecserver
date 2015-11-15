package org.anyio.b2c.domain.mongo;

import org.springframework.data.annotation.Id;

public class Person {

	@Id
	private String id;
	
	private String name;
	
	private int age;

	public Person(String name, int age) {
		this.name = name;
		this.age = age;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	@Override
	public String toString() {
		return String.format("Person [id=%s, name=%s, age=%d]", id, name, age);
	}

}
