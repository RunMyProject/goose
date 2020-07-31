/**
 * 
 */
package com.esabatini.goose.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.esabatini.goose.Setup;
import com.esabatini.goose.features.Game;
import com.esabatini.goose.features.GooseLogger;
import com.esabatini.goose.functional.GooseLambda;
/**
 * @author es
 *
 */
public enum InstructionState {

    add {
        @Override
        public Optional<String> nextInstruction() {
            return Optional.of("player");
        }
        
        @Override
        public Integer minArgs() {
            return 3;
        }
        
        @Override
        public void apply(Game game) {
        	
        	String name = game.getListArgs().get(2);
        	
    		if(game.getPlayers().values().stream().anyMatch((player) -> player.getName().equals(name))) {
        		GooseLogger.output(name + ": already existing player");
        		return;
    		}
        	
        	game.getPlayers().put(name, Player.builder().id(game.getPlayers().size()).name(name).index(0).won(0).build());
    		String players = game.getPlayers().values().stream().map(player -> player.getName()).collect(Collectors.joining(", "));
    		GooseLogger.output("players: " + players);
        }
    },
    move {
        @Override
        public Optional<String> nextInstruction() {
            return Optional.empty();
        }

        @Override
        public Integer minArgs() {
            return 2;
        }

        @Override
        public void apply(Game game) {

        	String name = game.getListArgs().get(1);

    		if(!game.getPlayers().values().stream().anyMatch((player) -> player.getName().equals(name))) {
        		GooseLogger.output(name + ": does not exist");
        		return;
    		}
    		
    		String dicesArgs=null;
    		
    		if(game.getListArgs().size()==4 && game.getListArgs().get(2).endsWith(Setup.SEP_DICE)) 
    			dicesArgs = game.getListArgs().get(2) + game.getListArgs().get(3);
    		if(game.getListArgs().size()==2) dicesArgs = "";
    		
    		if(dicesArgs==null) {
        		GooseLogger.output("move with manual dices: invalid number arguments");
        		return;
    		}

    		List<Integer> dices = null;
    		
    		if(!dicesArgs.isEmpty()) {
    			
    			List<Integer> dicesList = null;    			
    			try {
    				dicesList = game.splitDices(dicesArgs);
    			} catch(java.lang.NumberFormatException e) {
            		GooseLogger.output("move with manual dices: invalid number format arguments");
            		return;
    			}
    			
    			dices = dicesList.stream().filter(num -> num >= 1 && num <=6).collect(Collectors.toList());
    			
    			if(dices.size()<2) {
            		GooseLogger.output("move with manual dices: invalid number range arguments");
            		return;
    			}
    			
    			// if(game.isDebug()) dices.forEach(i -> System.out.println(i));
    			
    		} else {
	    			dices = new ArrayList<Integer>();
	    			dices.add(GooseLambda.throwsTheDice.apply(0));
	    			dices.add(GooseLambda.throwsTheDice.apply(1));
	    			
	    			// if(game.isDebug()) dices.forEach(i -> System.out.println(i));
    		}
    			       		                		
			Player player = game.getPlayers().get(name);
			
		    GooseLogger.output(game.funcPlayerPrintMessage.apply(player, dices));
        }
    },
    stop {
        @Override
        public Optional<String> nextInstruction() {
            return Optional.empty();
        } 
        
        @Override
        public Integer minArgs() {
            return 1;
        }
        
        @Override
        public void apply(Game game) {
    		game.getPlayers().clear();
    		GooseLogger.output("Goose Game stopped, no participant.");
        }
    },
    players {
        @Override
        public Optional<String> nextInstruction() {
            return Optional.empty();
        }
        
        @Override
        public Integer minArgs() {
            return 1;
        }
        
        @Override
        public void apply(Game game) {
        	if(game.getPlayers().isEmpty()) GooseLogger.output("no participant.");
        	else {
        		GooseLogger.output("Partecipants: " + game.getPlayers().size());
        		game.getPlayers().values().forEach(player -> System.out.println(player));
        	}
        }
    };
 
    public abstract Optional<String> nextInstruction(); 
    public abstract Integer minArgs();
    public abstract void apply(Game game);
}