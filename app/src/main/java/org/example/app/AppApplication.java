package org.example.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AppApplication {

	public static void main(String[] args) {
		SpringApplication.run(AppApplication.class, args);
	}

//	@RestController
//	public static class HelloController {
//		@GetMapping("/")
//		public String greeting() {
//			return "Hello World!";
//		}
//	}

}
