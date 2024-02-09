/**
 * by Jakub Wawak
 * all rights reserved
 * kubawawak@gmail.com
 */
package com.jakubwawak.md;

import com.vaadin.flow.spring.annotation.EnableVaadin;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Aplication for editing markdown files
 */
@EnableVaadin({"com.jakubwawak"})
@SpringBootApplication
public class MdApplication {

	public static String version = "v1.0.0";
	public static String build = "md080224REV01";

	/**
	 * Main function
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println(version+"/"+build);
		SpringApplication.run(MdApplication.class, args);
	}

}
