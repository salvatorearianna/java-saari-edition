package it.saari.interfaces;

/**
 * Rappresenta un fornitore di risultati. Non accetta argomenti
 * e restituisce un valore di tipo T.
 *
 * @param <T> il tipo del risultato fornito.
 */
public interface Supplier<T> {
	T get();
}
