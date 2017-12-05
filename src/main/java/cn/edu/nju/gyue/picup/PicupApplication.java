package cn.edu.nju.gyue.picup;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("cn.edu.nju.gyue")
public class PicupApplication {

	public static void main(String[] args) {
		SpringApplication.run(PicupApplication.class, args);
	}
}
