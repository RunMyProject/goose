/**
 * 
 */
package com.esabatini.goose;

import static org.junit.Assert.assertEquals;

import java.util.Optional;

import com.esabatini.goose.features.Game;
import com.esabatini.goose.features.GooseLogger;
import com.esabatini.goose.model.InstructionState;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

/**
 * @author es
 *
 */

/*
 * ********************* *
 * BUG: It doesn't work! *
 * io.cucumber.java8
 * ********************* *
public StepDefinitions() {
    Given("I have {int} cukes in my belly", (Integer cukes) -> {
        System.out.println("Cukes: %n\n" + cukes);
    });
}
*/

public class StepDefinitions {

//    throw new io.cucumber.java.PendingException();

	private Game game;
		
	@When("add player {word}")
    public void add_player(String name) {
		if(game==null) game = new Game();
    	String line = "add player " + name;
    	Optional<InstructionState> instruction = game.getInstructionState(game.splitLine(line));
    	instruction.get().apply(game);
    }
	
	@Given("I have player {word}")
    public void I_have_player(String name) {
		game = new Game();		
    	String line = "add player " + name;
    	Optional<InstructionState> instruction = game.getInstructionState(game.splitLine(line));
    	instruction.get().apply(game);
    	assertEquals("players: " + name, GooseLogger.lastGivenLine);
    }
	
	@Then("I have players {word} {word}")
	public void I_have_players(String name1, String name2) {
    	assertEquals("players: " + name1 + ", " + name2, GooseLogger.lastGivenLine);
	}

	@Then("{word} already existing player")
	public void  already_existing_player(String name) {
    	assertEquals(name + ": already existing player", GooseLogger.lastGivenLine);
	}
	
	@Given("If there are two participants {word} and {word} on space Start")
	public void If_there_are_two_participants_on_Start(String name1, String name2) {
		game = new Game();
    	String line = "add player " + name1;
    	Optional<InstructionState> instruction = game.getInstructionState(game.splitLine(line));
    	instruction.get().apply(game);
    	assertEquals("players: " + name1, GooseLogger.lastGivenLine);
    	line = "add player " + name2;
    	instruction = game.getInstructionState(game.splitLine(line));
    	instruction.get().apply(game);
    	assertEquals("players: " + name1 + ", " + name2, GooseLogger.lastGivenLine);
	}
	
	@When("move {word} {int}, {int}")
	public void move_name_d1_d2(String name, int d1, int d2) {
		String line = "move " + name + " " + d1 + ", " + d2;
    	Optional<InstructionState> instruction = game.getInstructionState(game.splitLine(line));
    	instruction.get().apply(game);
	}
	
	@Then("{word} rolls {int}, {int}. {word} moves from Start to {int}")
	public void name_rolls_d1_d2_name_moves_from_start_to_arrive(String name, int d1, int d2, String name1, int arrive) {
    	assertEquals(name + " rolls " + d1 + ", " + d2 + ". " + name1 + " moves from Start to " + arrive, GooseLogger.lastGivenLine);
	}

	@Then("{word} rolls {int}, {int}. {word} moves from {int} to {int}")
	public void name_rolls_d1_d2_name_moves_from_position_to_arrive(String name, int d1, int d2, String name1, int position, int arrive) {
    	assertEquals(name + " rolls " + d1 + ", " + d2 + ". " + name1 + " moves from " + position + " to " + arrive, GooseLogger.lastGivenLine);
	}

	@Given("If there is one participant {word} on space {int}")
	public void If_there_is_one_participant_name_on_space_position(String name, int position) {
		add_player(name);
		game.getPlayers().get(name).setIndex(position);
	}

	@Then("{word} rolls {int}, {int}. {word} moves from {int} to {int}. {word} Wins!!")
	public void name_rolls_d1_d2_name_moves_from_position_to_arrive_win(String name, int d1, int d2, String name1, int position, int arrive, String name2) {
    	assertEquals(name + " rolls " + d1 + ", " + d2 + ". " + name1 + " moves from " + position + " to " + arrive + ". " + name2 + " Wins!!", GooseLogger.lastGivenLine);
	}

	@Then("{word} rolls {int}, {int}. {word} moves from {int} to {int}. {word} bounces! {word} returns to {int}")
	public void name_rolls_d1_d2_name_moves_from_position_to_arrive_bounces(String name, int d1, int d2, String name1, int position, int arrive, String name2, String name3, int come_back) {
    	assertEquals(name + " rolls " + d1 + ", " + d2 + ". " + name1 + " moves from " + position + " to " + arrive + ". " + name2 + " bounces! " + name3 + " returns to " + come_back, GooseLogger.lastGivenLine);
	}

	@Then("{word} rolls {int}, {int}. {word} moves from {int} to The Bridge. {word} jumps to {int}, The Goose. {word} moves again and goes to {int}")
	public void name_rolls_d1_d2_name_moves_from_position_to_bridge(String name, int d1, int d2, String name1, int position, String name2, int bridge, String name3, int arrive) {
    	assertEquals(name + " rolls " + d1 + ", " + d2 + ". " + name1 + " moves from " + position + " to The Bridge. " + name2 + " jumps to " + bridge + ", The Goose. " + name3 + " moves again and goes to " + arrive, GooseLogger.lastGivenLine);
	}
	
	@Given("If there are two participants {word} and {word} respectively on spaces {int} and {int}")
	public void If_there_are_two_participants(String name1, String name2, int a, int b) {
		add_player(name1);
		add_player(name2);
		game.getPlayers().get(name1).setIndex(a);
		game.getPlayers().get(name2).setIndex(b);
	}
	
	@Then("{word} rolls {int}, {int}. {word} moves from {int} to {int}. On {int} there is {word}, who returns to {int}")
	public void name_rolls_d1_d2_name_moves_from_position_to_arrive_on_busy_position(
			String name, int d1, int d2, String name1, int position, int arrive, int busy_position, String name2, int come_back) {
    		assertEquals(name + " rolls " + d1 + ", " + d2 + ". " + name1 + " moves from " + position + " to " + arrive 
    				+ ". On " + busy_position + " there is " + name2 + ", who returns to " + come_back, GooseLogger.lastGivenLine);
	}
}
