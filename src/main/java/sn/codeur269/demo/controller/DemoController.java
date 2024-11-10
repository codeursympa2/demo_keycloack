package sn.codeur269.demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class DemoController {
	
	@GetMapping("/home")
	public ResponseEntity<String> home(){
		return ResponseEntity.ok("Hello world");
	}
	
	@GetMapping("/admin")
	public ResponseEntity<String> admin() {
		return ResponseEntity.ok("Hello Admin");
	}
	
	@GetMapping("/user")
	public ResponseEntity<String> user() {
		return ResponseEntity.ok("Hello user");
	}
	
	@GetMapping("/user-admin")
	public ResponseEntity<String> user_admin() {
		return ResponseEntity.ok("Hello user & admin");
	}
}
