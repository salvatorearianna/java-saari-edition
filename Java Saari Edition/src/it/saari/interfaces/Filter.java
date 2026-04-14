package it.saari.interfaces;

/**
 * Rappresenta una funzione di filtraggio (booleana) di un argomento.
 *
 * @param <T> il tipo dell'argomento del filtro.
 */
public interface Filter<T> {
	boolean test(T t);
}
