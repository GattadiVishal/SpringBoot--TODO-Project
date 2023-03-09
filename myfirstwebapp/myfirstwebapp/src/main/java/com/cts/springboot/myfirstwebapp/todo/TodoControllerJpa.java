package com.cts.springboot.myfirstwebapp.todo;

import java.time.LocalDate;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import jakarta.validation.Valid;

@Controller
@SessionAttributes("name")
public class TodoControllerJpa {

	
	private TodoRepository todoRepository;

	public TodoControllerJpa( TodoRepository todoRepository) {
		super();
		this.todoRepository = todoRepository;
	}

	// url for todo list is : /list-todos
	@RequestMapping("list-todos")
	public String listAllTodos(ModelMap model) {
		String username = getLoggedInUsername(model);
		List<Todo> todos = todoRepository.findByUsername(username);
		model.addAttribute("todos", todos);
		return "listTodos";

	}

	// This handles GET, POST requests
	@RequestMapping(value = "add-todo", method = RequestMethod.GET)
	public String showNewTodoPage(ModelMap model) {
		String username = getLoggedInUsername(model);
		Todo todo = new Todo(0, username, "", LocalDate.now().plusYears(1), false);
		model.put("todo", todo);
		return "todo";
	}

	@RequestMapping(value = "add-todo", method = RequestMethod.POST)
	public String addNewTodoPage(ModelMap model, @Valid Todo todo, BindingResult result) {
		if (result.hasErrors()) {
			return "todo";
		}

		String username = getLoggedInUsername(model);
		todo.setUsername(username);
		//todorepository doesn't have any method  which would all the attributes.
		//save method accept todo as input.
		todoRepository.save(todo);
		//todoService.addTodo(username, todo.getDescription(), todo.getTargetDate(), todo.isDone());
		return "redirect:list-todos";

	}

	@RequestMapping("delete-todo")
	public String deleteTodo(@RequestParam int id) {
		todoRepository.deleteById(id);
		// after delete, it return to list of todos
		//todoService.deleteById(id);
		return "redirect:list-todos";

	}
	
	@RequestMapping(value="update-todo", method = RequestMethod.GET)//when we want to get updates for to-do we use get method.
	public String showUpdateTodoPage(@RequestParam int id, ModelMap model) {
		Todo todo = todoRepository.findById(id).get();//findById returns optional from that get returns the method.
		model.addAttribute("todo", todo);
		return "todo";

	}
	
	@RequestMapping(value = "update-todo", method = RequestMethod.POST)//when we want to submit updates for to-do we use get method.
	public String updateTodoPage(ModelMap model, @Valid Todo todo, BindingResult result) {
		if (result.hasErrors()) {
			return "todo";
		}

		String username = getLoggedInUsername(model);
		todo.setUsername(username);
		todoRepository.save(todo);
		//todoService.updateTodo(todo);
		return "redirect:list-todos";

	}
	
	private String getLoggedInUsername(ModelMap model) {
		Authentication authentication = 
				SecurityContextHolder.getContext().getAuthentication();
		//here we get user from authentication
		return authentication.getName();
	}
}

//User is entering a description and the website has to capture it, so we use
//@RequestParam 

//Here instead of using @RequestParam we use Todo bean to bind the elements.
//@Valid is used to validate the description size.

//delete-todo = is used to delete todos
//but whenever the server is restarted, the todo will be visible again due to static.