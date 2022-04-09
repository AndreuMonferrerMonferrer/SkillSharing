package es.uji.ei1027.SkillSharing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

import java.util.logging.Logger;

@SpringBootApplication
public class SKillSharingApplication {


	private static final Logger log = Logger.getLogger(SKillSharingApplication.class.getName());

	public static void main(String[] args) {
		new SpringApplicationBuilder(SKillSharingApplication.class).run(args);
	}

}
