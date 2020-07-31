/**
 * 
 */
package com.esabatini.goose.functional;

/**
 * @author es
 *
 */
@FunctionalInterface
public interface FuncPlayerMessage<Player, List> {
	public String apply(Player player, List dices);
}
