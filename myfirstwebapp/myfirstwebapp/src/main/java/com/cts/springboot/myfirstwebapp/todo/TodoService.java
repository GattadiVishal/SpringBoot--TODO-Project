package com.cts.springboot.myfirstwebapp.todo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import org.springframework.stereotype.Service;

import jakarta.validation.Valid;

@Service
public class TodoService {
	
	private static List<Todo> todos = new ArrayList<>();
	
	private static int todosCount = 0;
	
	static {
		todos.add(new Todo(++todosCount, "Vishal", "Get AWS Certified 1", 
				LocalDate.now().plusYears(1), false));
		todos.add(new Todo(++todosCount, "Vishal", "Learn Azure 1", 
				LocalDate.now().plusYears(1), false));
		todos.add(new Todo(++todosCount, "Vishal", "Learn Springboot 1", 
				LocalDate.now().plusYears(1), false));
		
	}
	
	public List<Todo> findByUsername(String username){
		Predicate<? super Todo> predicate = todo -> todo.getUsername().equalsIgnoreCase(username);
		/*here equalsIgnoreCase() coz we are returning the username which is string and it 
		cannot be a == assignment operator.
		*/
		return todos.stream().filter(predicate).toList();
	}
	
	public void addTodo(String username, String description, LocalDate targetDate, boolean done)
	{
		//When we add a new todo the count will be incremented.++todosCount
		Todo todo = new Todo(++todosCount, username, description, targetDate, done); 
		todos.add(todo);
	}
	
	public void deleteById(int id) {
		//condition to be passed to delete todo.getId() == id
		//here we use lambda expression
		//todo -> todo.getId() == id
		Predicate<? super Todo> predicate = todo -> todo.getId() == id;
		todos.removeIf(predicate);
	}

	public Todo findById(int id) {
		// TODO Auto-generated method stub
		Predicate<? super Todo> predicate = todo -> todo.getId() == id;
		Todo todo = todos.stream().filter(predicate).findFirst().get();
		return todo;
	}

	public void updateTodo(@Valid Todo todo) {
		//here in order to update todo, first we are deleting the existing value of todo
		deleteById(todo.getId());
		//after deleting the value, we are updating as new todo by adding as new todo.
		todos.add(todo);
	}

}


/*if we want to initialize a static varibale we use static block.
whenever server is restarted, the static list is refreshed.
whenever the server is restarted, the newly added todos will be lost.

delete-todo = is used to delete todos
but whenever the server is restarted, the todo will be visible again due to static.
*/