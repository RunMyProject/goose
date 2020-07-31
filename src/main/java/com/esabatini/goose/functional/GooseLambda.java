/**
 * 
 */
package com.esabatini.goose.functional;

import java.util.List;
import java.util.Random;
import java.util.function.BiFunction;
import java.util.function.Function;

import com.esabatini.goose.Setup;
import com.esabatini.goose.model.Player;

/**
 * @author es
 *
 */
public class GooseLambda {

	public static BiFunction<Integer, Integer, Integer> normalMoves = (a, b) -> (a + b) > Setup.FINAL_STAGE ? Setup.BOUNCE_STAGE : (a+b);

	public static BiFunction<Integer, Integer, Integer> bounceMoves = (a, b) -> 2*Setup.FINAL_STAGE - a - b;
	
	public static FuncPlayerMessage<Player, List<Integer>> funcPlayerFirstMessage = (p, d) -> {
		int total = p.getIndex() + d.get(0) + d.get(1);
		return p.getName() 
			+ " rolls " + d.get(0) + ", " + d.get(1) + ". " + p.getName() 
			+ " moves from " + (p.getIndex()==0 ? "Start" : p.getIndex()) 
			+ " to "
			+ (total > Setup.FINAL_STAGE 
					? Setup.FINAL_STAGE 
					: (total == Setup.BRIDGE ? "The Bridge. " + p.getName() + " jumps to " + Setup.BRIDGE_VAL : total));
	};
    
	public static Function<Integer, Integer> throwsTheDice = unused -> new Random().ints(1, Setup.FACES + 1).limit(1).findFirst().getAsInt();
}
