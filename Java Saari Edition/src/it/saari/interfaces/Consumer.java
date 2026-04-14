package it.saari.interfaces;

/**
 * Rappresenta un'operazione che accetta un singolo argomento e non restituisce risultato.
 *
 * @param <T> il tipo dell'argomento dell'operazione.
 */
public interface Consumer<T> {

	void accept(T t);
}
