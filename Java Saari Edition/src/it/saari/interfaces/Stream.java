package it.saari.interfaces;

import it.saari.abstracts.BinaryOperator;
import it.saari.classes.Stream.Result;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

/**
 * Rappresenta una sequenza di elementi che supporta operazioni di tipo
 * Pipes &amp; Filters (Stream) compatibili con Java 5+.
 *
 * @param <T> il tipo degli elementi dello stream.
 */
public interface Stream<T> {

	/**
	 * Ordina gli elementi in base al comparatore.
	 *
	 * @param cmp Comparatore per l'ordinamento.
	 * @return Uno stream di elementi ordinati.
	 */
	Stream<T> sorted(Comparator<T> cmp);

	/**
	 * Ordina gli elementi invertendo l'ordine del comparatore.
	 *
	 * @param cmp Comparatore per l'ordinamento inverso.
	 * @return Uno stream di elementi ordinati al contrario.
	 */
	Stream<T> reverse(Comparator<T> cmp);

	/**
	 * Filtra gli elementi in base al tipo di filtro.
	 *
	 * @param filter Filtro da applicare.
	 * @return Uno stream di elementi filtrati.
	 */
	Stream<T> filter(Filter<? super T> filter);

	/**
	 * Seleziona l'elemento massimo di uno stream in base al comparatore.
	 *
	 * @param comparator Comparatore per la selezione.
	 * @return Un {@link Result} contenente l'elemento massimo,
	 *         oppure {@code null} se lo stream e' vuoto.
	 */
	Result<T> max(Comparator<T> comparator);

	/**
	 * Seleziona l'elemento minimo di uno stream in base al comparatore.
	 *
	 * @param comparator Comparatore per la selezione.
	 * @return Un {@link Result} contenente l'elemento minimo,
	 *         oppure {@code null} se lo stream e' vuoto.
	 */
	Result<T> min(Comparator<T> comparator);

	/**
	 * Mappa gli elementi di uno stream applicando una funzione di trasformazione.
	 *
	 * @param <R>    il tipo degli elementi dello stream risultante.
	 * @param mapper Funzione di mapping.
	 * @return Uno stream di elementi mappati.
	 */
	<R> Stream<R> map(Function<? super T, ? extends R> mapper);

	/**
	 * Mappa ogni elemento dello stream in uno stream e concatena i risultati.
	 *
	 * @param <R>    il tipo degli elementi dello stream risultante.
	 * @param mapper Funzione che produce uno stream per ogni elemento.
	 * @return Uno stream risultante dalla concatenazione degli stream prodotti.
	 */
	<R> Stream<R> flatMap(Function<? super T, ? extends Stream<? extends R>> mapper);

	/**
	 * Converge gli elementi in una {@link List}.
	 *
	 * @return Una lista contenente gli elementi dello stream.
	 */
	List<T> toList();

	/**
	 * Converge gli elementi in una {@link Map} utilizzando le chiavi fornite.
	 *
	 * @param <R>  il tipo delle chiavi della mappa.
	 * @param keys Lista di chiavi da associare agli elementi.
	 * @return Una mappa con le chiavi fornite e i valori dello stream.
	 */
	<R> Map<R, T> toMap(List<R> keys);

	/**
	 * Converge gli elementi in una {@link Map} usando gli elementi stessi come chiavi e valori.
	 *
	 * @return Una mappa con gli elementi come chiavi e valori.
	 */
	Map<T, T> toMap();

	/**
	 * Converge gli elementi in uno Stream con ordine invertito (LIFO).
	 *
	 * @return Uno stream con gli elementi in ordine inverso.
	 */
	Stream<T> toStack();

	/**
	 * Seleziona l'elemento i-esimo dello stream.
	 *
	 * @param index Indice dell'elemento.
	 * @return L'elemento alla posizione indicata.
	 */
	T get(int index);

	/**
	 * Verifica se nello stream esiste almeno un elemento che soddisfa
	 * la funzione di predicate.
	 *
	 * @param predicate Funzione di verifica.
	 * @return {@code true} se almeno un elemento soddisfa il predicato,
	 *         {@code false} altrimenti.
	 */
	boolean anyMatch(Predicate<T> predicate);

	/**
	 * Verifica se tutti gli elementi dello stream soddisfano la funzione
	 * di predicate.
	 *
	 * @param predicate Funzione di verifica.
	 * @return {@code true} se tutti gli elementi soddisfano il predicato,
	 *         {@code false} altrimenti.
	 */
	boolean allMatch(Predicate<T> predicate);

	/**
	 * Per ogni elemento dello stream viene eseguito un consumatore.
	 *
	 * @param consumer Funzione che consuma l'elemento.
	 */
	void forEach(Consumer<? super T> consumer);

	/**
	 * Per ogni elemento dello stream, ordinato secondo il comparatore,
	 * viene eseguito un consumatore.
	 *
	 * @param consumer Funzione che consuma l'elemento.
	 * @param cmp      Comparatore per l'ordinamento.
	 */
	void forEachOrder(Consumer<? super T> consumer, Comparator<T> cmp);

	/**
	 * Stampa ogni elemento dello stream terminando con una nuova riga.
	 */
	void println();

	/**
	 * Stampa ogni elemento dello stream.
	 */
	void print();

	/**
	 * Stampa ogni elemento dello stream terminando con una nuova riga.
	 * E' possibile aggiungere un prefisso e/o un suffisso all'elemento.
	 *
	 * @param prefix Stringa prefissata all'elemento.
	 * @param suffix Stringa suffissa all'elemento.
	 */
	void println(String prefix, String suffix);

	/**
	 * Stampa ogni elemento dello stream. E' possibile aggiungere un prefisso
	 * e/o un suffisso all'elemento.
	 *
	 * @param prefix Stringa prefissata all'elemento.
	 * @param suffix Stringa suffissa all'elemento.
	 */
	void print(String prefix, String suffix);

	/**
	 * Stampa ogni elemento dello stream con un prefisso, terminando con una nuova riga.
	 *
	 * @param prefix Stringa prefissata all'elemento.
	 */
	void prependln(String prefix);

	/**
	 * Stampa ogni elemento dello stream con un prefisso.
	 *
	 * @param prefix Stringa prefissata all'elemento.
	 */
	void prepend(String prefix);

	/**
	 * Stampa ogni elemento dello stream con un suffisso, terminando con una nuova riga.
	 *
	 * @param suffix Stringa suffissa all'elemento.
	 */
	void appendln(String suffix);

	/**
	 * Stampa ogni elemento dello stream con un suffisso.
	 *
	 * @param suffix Stringa suffissa all'elemento.
	 */
	void append(String suffix);

	/**
	 * Indica il numero di elementi presenti nello stream.
	 *
	 * @return Il numero di elementi nello stream.
	 */
	long count();

	/**
	 * Calcola la somma degli elementi dello stream.
	 * Questo metodo e' applicabile solo a stream di tipo {@code Integer}.
	 *
	 * @return La somma degli elementi.
	 * @throws IllegalStateException se lo stream contiene elementi non di tipo Integer.
	 */
	int sum();

	/**
	 * Divide lo stream da un indice di partenza a un indice di arrivo.
	 *
	 * @param from Indice di partenza (incluso).
	 * @param to   Indice di arrivo (escluso).
	 * @return Uno stream contenente gli elementi nel range specificato.
	 */
	Stream<T> split(int from, int to);

	/**
	 * Limita lo stream ai primi {@code max} elementi.
	 *
	 * @param max Numero massimo di elementi.
	 * @return Uno stream limitato.
	 */
	Stream<T> limit(int max);

	/**
	 * Salta i primi {@code from} elementi dello stream.
	 *
	 * @param from Numero di elementi da saltare.
	 * @return Uno stream senza i primi elementi.
	 */
	Stream<T> skip(int from);

	/**
	 * Rimuove gli elementi duplicati dallo stream.
	 * L'uguaglianza viene stabilita tramite {@code equals}.
	 *
	 * @return Uno stream di elementi distinti.
	 */
	Stream<T> distinct();

	/**
	 * Riduce gli elementi dello stream a un singolo valore tramite
	 * un'operazione di accumulazione.
	 *
	 * @param identity    Il valore iniziale (identita') dell'accumulazione.
	 * @param accumulator Operatore binario di accumulazione.
	 * @return Il risultato dell'operazione di accumulazione.
	 */
	T reduce(T identity, BinaryOperator<T> accumulator);

}
