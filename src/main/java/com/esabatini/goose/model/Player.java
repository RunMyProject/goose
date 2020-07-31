/**
 * 
 */
package com.esabatini.goose.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

/**
 * @author es
 *
 */
@Getter @Setter
@Builder
public class Player {

	private @NonNull Integer id;
	private @NonNull String name;
	private @NonNull Integer index;
	private @NonNull Integer bounce;
	private @NonNull Integer won;
	
	public Player(Integer id, String name, Integer index, Integer bounce, Integer won) {
		this.id = id;
		this.name = name;
		this.index = index;
		this.bounce = bounce;
		this.won = won;
	}
	
	public String toString() {
		return "The participant '" + name + "' is above the number box: " + index + "; games won: " + won;
	}
}
