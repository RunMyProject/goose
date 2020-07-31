/**
 * 
 */
package com.esabatini.goose;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.esabatini.goose.features.Game;

/**
 * @author es
 *
 */
@Component
public class GooseApplicationRunner implements CommandLineRunner {

	@Override
	public void run(String... args) throws Exception {
		new Game().run();
    }
}