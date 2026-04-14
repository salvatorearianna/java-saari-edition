package it.saari.interfaces;

/**
 * Rappresenta una funzione che accetta due argomenti e produce un risultato.
 *
 * @param <T> il tipo del primo argomento.
 * @param <U> il tipo del secondo argomento.
 * @param <R> il tipo del risultato.
 */
public interface BiFunction<T, U, R> {
	R apply(T t, U u);
}
