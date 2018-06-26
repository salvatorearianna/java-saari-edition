package it.saari.interfaces;

import it.saari.abstracts.BinaryOperator;
import it.saari.classes.Stream.Result;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

public interface Stream<T> {
	/**
	 * Ordina gli elementi in base al comparatore.
	 * 
	 * @param cmp
	 *            Comparatore
	 * @return Ritorna uno stream di elementi ordinati.
	 */
	public Stream<T> sorted(Comparator<T> cmp);

	/**
	 * Ordina gli elementi invertendo l'ordine del comparatore.
	 * 
	 * @param cmp
	 *            Comparatore
	 * @return Ritorna uno stream di elementi ordinati al contrario.
	 */
	public Stream<T> reverse(Comparator<T> cmp);

	/**
	 * Filtra gli elementi in base al tipo di filtro.
	 * 
	 * @param filter
	 *            Filtro
	 * @return Ritorna uno stream di elementi filtrati.
	 */
	public Stream<T> filter(Filter<? super T> filter);

	/**
	 * Seleziona l'elemento massimo di uno stream in base al comparatore.
	 * 
	 * @param comparator
	 *            Comparatore
	 * @return Ritorna uno Result<T>.
	 */
	public Result<T> max(Comparator<T> comparator);

	/**
	 * Seleziona l'elemento minimo di uno stream in base al comparatore.
	 * 
	 * @param comparator
	 *            Comparatore
	 * @return Ritorna uno Result<T>.
	 */
	public Result<T> min(Comparator<T> comparator);

	/**
	 * Mappa gli elementi di uno stream.
	 * 
	 * @param mapper
	 *            Mapping
	 * @return Ritorna uno stream di elementi mappati.
	 */
	public <R> Stream<R> map(Function<? super T, ? extends R> mapper);

	/**
	 * Converge gli elementi in una List<T>.
	 * 
	 * @return Ritorna una Collection di tipo List<T>.
	 */
	public List<T> toList();

	/**
	 * Converge gli elementi in una Map<R, T>.
	 * 
	 * @param keys
	 *            Elementi che faranno da chiave.
	 * @return Ritorna una Collection di tipo Map<R, T>.
	 */
	public <R> Map<R, T> toMap(List<R> keys);

	/**
	 * Converge gli elementi in una Map<R, T>.
	 * 
	 * @return Ritorna una Collection di tipo Map<R, T>.
	 */
	public <R> Map<T, T> toMap();

	/**
	 * Converge gli elementi in uno Stream-Stack(LIFO).
	 * 
	 * @return Ritorna uNO stream.
	 */
	public Stream<T> toStack();

	/**
	 * Seleziona l'elemento i-esimo dello stream.
	 * 
	 * @param index
	 *            Indice dell'elemento.
	 * @return Ritorna l'elemento selezionato.
	 */
	public T get(int index);

	/**
	 * Verifica se vi è nello stream almeno un elemento che verifica la funzione
	 * di predicate.
	 * 
	 * @param predicate
	 *            Funzione di verifica.
	 * @return true nel caso l'elemento è presente nello stream, false se non è
	 *         presente nessuno elemento che rispetti il predicate.
	 */
	public boolean anyMatch(Predicate<T> predicate);

	/**
	 * Verifica se tutti gli elementi dello stream verificano la funzione di
	 * predicate.
	 * 
	 * @param predicate
	 *            Funzione di verifica.
	 * @return true nel caso tutti gli elementi siano presenti nello stream,
	 *         false se non è presente nessuno elemento che rispetti il
	 *         predicate.
	 */
	public boolean allMatch(Predicate<T> predicate);

	/**
	 * Per ogni elemento dello stream viene eseguito un consumatore.
	 * 
	 * @param consumer
	 *            Funzione che consuma l'elemento.
	 */
	public void forEach(Consumer<? super T> consumer);

	/**
	 * Per ogni elemento dello stream in ordine di comparatore viene eseguito un
	 * consumatore.
	 * 
	 * @param consumer
	 *            Funzione che consuma l'elemento.
	 * @param cmp
	 *            Comparatore
	 */
	public void forEachOrder(Consumer<? super T> consumer, Comparator<T> cmp);

	/**
	 * Stampa ogni elemento dello stream terminando la stampa con \n.
	 */
	public void println();

	/**
	 * Stampa ogni elemento dello stream.
	 */
	public void print();

	/**
	 * Stampa ogni elemento dello stream terminando la stampa con \n. E'
	 * possibile aggiungere un prefisso e/o un suffisso all'elemento.
	 * 
	 * @param prefix
	 *            Stringa prefissata all'elemento.
	 * @param suffix
	 *            Stringa suffissa all'elemento.
	 */
	public void println(String prefix, String suffix);

	/**
	 * Stampa ogni elemento dello stream.. E' possibile aggiungere un prefisso
	 * e/o un suffisso all'elemento.
	 * 
	 * @param prefix
	 *            Stringa prefissata all'elemento.
	 * @param suffix
	 *            Stringa suffissa all'elemento.
	 */
	public void print(String prefix, String suffix);

	public void prependln(String prefix);

	public void prepend(String prefix);

	public void appendln(String suffix);

	public void append(String suffix);

	/**
	 * Indica il numero di elementi presenti nello stream.
	 * 
	 * @return Le dimensioni dello stream.
	 */
	public long count();

	/**
	 * Divide lo stream 'from' un indice 'to' un altro indice.
	 * 
	 * @param from
	 *            Indice di partenza.
	 * @param to
	 *            Indice di arrivo.
	 * @return Uno stream di elementi splittati.
	 */
	public Stream<T> split(int from, int to);

	/**
	 * Splitta lo stream indicando soltanto fin dove arrivare.
	 * 
	 * @param max
	 *            Fin dove deve essere tagliato lo stream.
	 * @return Uno stream di elementi splittati.
	 */
	public Stream<T> limit(int max);

	/**
	 * Splitta lo stream indicando da dove si deve partire.
	 * 
	 * @param from
	 *            Da dove si inizia a tagliare lo stream.
	 * @return Uno stream di elementi splittati.
	 */
	public Stream<T> skip(int from);

	/**
	 * Annulla gli elementi che si ripetono all'interno di uno stream.
	 * L'uguaglianza viene stabilita tramite equals.
	 * 
	 * @return Uno stream di elementi distinti.
	 */
	public Stream<T> distinct();

	/**
	 * Tramite un'operazione di accumulazione si riesce a ridurre gli elementi
	 * dello stream all'elemento risultate dall'operazione indicata
	 * nell'accumulatore.
	 * 
	 * @param identity
	 *            Il caso DEFAULT o 0 degli elementi dello stream.
	 * @param accumulator
	 *            Accumulatore di elementi.
	 * @return Ritorna il risultato dell'operazione di accumulazione.
	 */
	public T reduce(T identity, BinaryOperator<T> accumulator);
	
	public <R> Stream<R> flatMap(Function<? super T, ? extends Stream<? extends R>> mapper);

}
