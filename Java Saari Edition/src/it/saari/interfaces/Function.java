package it.saari.interfaces;

/**
 * Rappresenta una funzione che accetta un argomento e produce un risultato.
 *
 * @param <T> il tipo dell'argomento della funzione.
 * @param <R> il tipo del risultato della funzione.
 */
public interface Function<T, R> {
	R apply(T t);
}
