package it.saari.abstracts;

import it.saari.interfaces.BiFunction;

/**
 * Rappresenta un'operazione binaria su due operandi dello stesso tipo,
 * producendo un risultato dello stesso tipo.
 *
 * @param <T> il tipo degli operandi e del risultato dell'operatore.
 */
public abstract class BinaryOperator<T> implements BiFunction<T, T, T> {
}
