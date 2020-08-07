/**
 * 
 */
package com.esabatini.goose.features;

import java.io.Console;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.esabatini.goose.Setup;
import com.esabatini.goose.functional.FuncPlayerMessage;
import com.esabatini.goose.functional.GooseLambda;
import com.esabatini.goose.model.InstructionState;
import com.esabatini.goose.model.Player;

import lombok.Getter;

/**
 * @author es
 *
 */
@Getter
public class Game {
	        	
	private String message;
	public FuncPlayerMessage<Player, List<Integer>> funcPlayerPrintMessage = (player, dices) -> {

		message = GooseLambda.funcPlayerFirstMessage.apply(player, dices);

		int total = dices.get(0) + dices.get(1);
		
		if(total + player.getIndex() > Setup.FINAL_STAGE) player.setBounce(GooseLambda.bounceMoves.apply(player.getIndex(), total));
		if(total + player.getIndex() == Setup.BRIDGE) player.setIndex(Setup.BRIDGE_VAL);
		
		player.setIndex(GooseLambda.normalMoves.apply(player.getIndex(), total));
		
		boolean infinityGooses = true;
		
		while(infinityGooses) {
			
			infinityGooses = false;
        	
			getPlayers().values().forEach(p -> {
        		if(p.getIndex()==player.getIndex() && p.getId()!=player.getId()) {
        			message+= ". On " + player.getIndex() + " there is " + p.getName() 
        							  + ", who returns to " + (player.getIndex() - total);
        			p.setIndex(GooseLambda.normalMoves.apply(p.getIndex(), -total));
        		}
        	});
			
			message+= switch (player.getIndex()) {
				case  5, 9, 14, 18, 23, 27 -> ", The Goose. " + player.getName() + " moves again and goes to " + (player.getIndex() + total);
				case 63 -> ". " + player.getName() + " Wins!!";
	            case 64 -> ". " + player.getName() + " bounces! " + player.getName() + " returns to " + player.getBounce();
	            default -> "";
	        };
	      
	        switch(player.getIndex()) {
	        	case  5, 9, 14, 18, 23, 27 -> {
	        		player.setIndex(GooseLambda.normalMoves.apply(player.getIndex(), total));
	        		infinityGooses = true;
	        	}
	        	case 63 -> {
	            	getPlayers().values().forEach(p -> p.setIndex(0));
	            	player.setWon(player.getWon() + 1);
	        	}
	        	case 64 -> player.setIndex(player.getBounce());
	        }
		}
	
        return message;
	};
	
	// private boolean isDebug = false;
		
	private Map<String, Player> players;
	private List<String> listArgs;
	
	public Game() {
		players = new HashMap<String, Player>();
		/*
		if(debug) {
			players.put("test", Player.builder().id(getPlayers().size()).index(17).won(0).name("test").build());
			players.put("Pippo", Player.builder().id(getPlayers().size()).index(15).won(0).name("Pippo").build());
			players.put("Pluto", Player.builder().id(getPlayers().size()).index(17).won(0).name("Pluto").build());
		}
		*/
	}
	
	public List<String> splitLine(String line) {
		return Stream.of(line.split(Setup.SEP_INSTR))
			.map(token -> new String(token))
		    .collect(Collectors.toList());
	}

	public List<Integer> splitDices(String line) {
		return Stream.of(line.split(Setup.SEP_DICE))
			.map(token -> Integer.valueOf(token))
		    .collect(Collectors.toList());
	}

	public Optional<InstructionState> getInstructionState(List<String> list) {
		
		listArgs = list;
		
		String first = list.stream().findFirst().get();
		
		boolean checkFirstInstruction = Arrays
			.stream(InstructionState.values())
			.anyMatch((instruction) -> instruction.name().equals(first));

		if(checkFirstInstruction) {
			
			InstructionState instructionState = InstructionState.valueOf(first);
			
			if(list.size() < instructionState.minArgs()) {
				GooseLogger.output("Mininum number of arguments required: " + instructionState.minArgs());
				return Optional.empty();
			}
			
			if(instructionState.nextInstruction().isEmpty()) return Optional.of(instructionState);
			
			if(instructionState.nextInstruction()
				.filter(instruction -> instruction.equals(list.get(1)))
				.isPresent()) return Optional.of(instructionState);
		}
		
		return Optional.empty();
	}
	
	public void run() {
		
      GooseLogger.output("\n--------------------\n* Welcome to Goose *\n--------------------");
      GooseLogger.output(Setup.HELP);

      Console console = System.console();

      boolean infinity = true;      
      while(infinity) {
          
          String line = console.readLine(GooseLogger.PROMPT);
          if(line.trim().length()>0) switch(line) {

	          case "help" -> GooseLogger.output(Setup.HELP);
	          case "quit" -> {
	        	  GooseLogger.output("Application Closed");
	              infinity = false;
	          }
	          default -> {
	        	  
	        	  // if(isDebug) GooseLogger.output(line);
	        	  
	        	  Optional<InstructionState> instruction = getInstructionState(splitLine(line));
	        	  
	        	  if(instruction.isEmpty()) GooseLogger.output("Instruction not recognized.\n" + Setup.HELP);
	        	  else instruction.get().apply(this);
	          }
	      }
      }
      
      GooseLogger.output("End Goose Game!");
	}	
}