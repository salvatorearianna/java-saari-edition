package it.saari.classes;

import it.saari.abstracts.BinaryOperator;
import it.saari.interfaces.Consumer;
import it.saari.interfaces.Filter;
import it.saari.interfaces.Function;
import it.saari.interfaces.Predicate;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * Implementazione concreta di {@link it.saari.interfaces.Stream}.
 * Fornisce operazioni di tipo Pipes &amp; Filters su collezioni di dati,
 * compatibili con Java 5+.
 *
 * @author Salvatore
 * @param <T> il tipo degli elementi dello stream.
 */
public class Stream<T> implements it.saari.interfaces.Stream<T> {

	private final List<T> list;

	private Stream(List<T> list) {
		this.list = list;
	}

	// =========================================================================
	// Factory Methods
	// =========================================================================

	/**
	 * Crea uno stream a partire da un array di elementi (varargs).
	 *
	 * @param <T> il tipo degli elementi.
	 * @param arr Elementi dell'array.
	 * @return Uno stream di elementi T.
	 */
	public static <T> it.saari.interfaces.Stream<T> of(T... arr) {
		List<T> tmp = new LinkedList<T>();
		tmp.addAll(Arrays.asList(arr));
		return new Stream<T>(tmp);
	}

	/**
	 * Crea uno stream di caratteri a partire da una stringa.
	 *
	 * @param string La stringa da scomporre in caratteri.
	 * @return Uno stream dove ogni elemento e' un singolo carattere della stringa.
	 */
	public static it.saari.interfaces.Stream<String> of(String string) {
		List<String> tmp = new LinkedList<String>();
		for (char c : string.toCharArray()) {
			tmp.add(String.valueOf(c));
		}
		return new Stream<String>(tmp);
	}

	/**
	 * Crea uno stream a partire da una lista.
	 *
	 * @param <T> il tipo degli elementi.
	 * @param arr Lista di elementi.
	 * @return Uno stream di elementi T.
	 */
	public static <T> it.saari.interfaces.Stream<T> of(List<T> arr) {
		List<T> tmp = new LinkedList<T>();
		tmp.addAll(arr);
		return new Stream<T>(tmp);
	}

	/**
	 * Crea uno stream a partire da un Set.
	 *
	 * @param <T> il tipo degli elementi.
	 * @param arr Set di elementi.
	 * @return Uno stream di elementi T.
	 */
	public static <T> it.saari.interfaces.Stream<T> of(Set<T> arr) {
		List<T> tmp = new LinkedList<T>();
		tmp.addAll(arr);
		return new Stream<T>(tmp);
	}

	/**
	 * Crea uno stream di entry a partire da una mappa.
	 *
	 * @param <K> il tipo delle chiavi.
	 * @param <V> il tipo dei valori.
	 * @param map Mappa di elementi.
	 * @return Uno stream di {@link Entry}.
	 */
	public static <K, V> it.saari.interfaces.Stream<Entry<K, V>> of(Map<K, V> map) {
		List<Entry<K, V>> tmp = new LinkedList<Entry<K, V>>();
		tmp.addAll(map.entrySet());
		return new Stream<Entry<K, V>>(tmp);
	}

	/**
	 * Crea uno stream dei valori di una mappa.
	 *
	 * @param <K> il tipo delle chiavi.
	 * @param <V> il tipo dei valori.
	 * @param map Mappa di elementi.
	 * @return Uno stream dei valori della mappa.
	 */
	public static <K, V> it.saari.interfaces.Stream<V> ofValues(Map<K, V> map) {
		List<V> tmp = new LinkedList<V>();
		tmp.addAll(map.values());
		return new Stream<V>(tmp);
	}

	/**
	 * Crea uno stream delle chiavi di una mappa.
	 *
	 * @param <K> il tipo delle chiavi.
	 * @param <V> il tipo dei valori.
	 * @param map Mappa di elementi.
	 * @return Uno stream delle chiavi della mappa.
	 */
	public static <K, V> it.saari.interfaces.Stream<K> ofKeys(Map<K, V> map) {
		List<K> tmp = new LinkedList<K>();
		tmp.addAll(map.keySet());
		return new Stream<K>(tmp);
	}

	// =========================================================================
	// Stream Operations
	// =========================================================================

	public it.saari.interfaces.Stream<T> sorted(Comparator<T> cmp) {
		List<T> sorted = new LinkedList<T>();
		sorted.addAll(list);
		Collections.sort(sorted, cmp);
		return new Stream<T>(sorted);
	}

	public it.saari.interfaces.Stream<T> reverse(Comparator<T> cmp) {
		Comparator<T> revert = Collections.reverseOrder(cmp);
		List<T> reversed = new LinkedList<T>();
		reversed.addAll(list);
		Collections.sort(reversed, revert);
		return new Stream<T>(reversed);
	}

	public it.saari.interfaces.Stream<T> filter(Filter<? super T> filter) {
		List<T> newList = new LinkedList<T>();
		for (T t : list) {
			if (filter.test(t)) {
				newList.add(t);
			}
		}
		return new Stream<T>(newList);
	}

	public int sum() {
		int res = 0;
		for (T t : list) {
			if (t instanceof Integer) {
				res += ((Integer) t).intValue();
			} else {
				throw new IllegalStateException(
						"sum() e' applicabile solo a stream di Integer, trovato: "
								+ (t == null ? "null" : t.getClass().getName()));
			}
		}
		return res;
	}

	public <R> it.saari.interfaces.Stream<R> map(
			Function<? super T, ? extends R> mapper) {
		List<R> tmp = new LinkedList<R>();
		for (T t : list) {
			tmp.add(mapper.apply(t));
		}
		return new Stream<R>(tmp);
	}

	public <R> it.saari.interfaces.Stream<R> flatMap(
			Function<? super T, ? extends it.saari.interfaces.Stream<? extends R>> mapper) {
		final List<R> res = new LinkedList<R>();
		for (T t : list) {
			it.saari.interfaces.Stream<? extends R> stream = mapper.apply(t);
			stream.forEach(new Consumer<R>() {
				public void accept(R r) {
					res.add(r);
				}
			});
		}
		return new Stream<R>(res);
	}

	// =========================================================================
	// Terminal Operations
	// =========================================================================

	public List<T> toList() {
		return new LinkedList<T>(list);
	}

	public <R> Map<R, T> toMap(List<R> keys) {
		Map<R, T> map = new LinkedHashMap<R, T>();
		int size = Math.min(list.size(), keys.size());
		for (int i = 0; i < size; i++) {
			if (keys.get(i) != null) {
				map.put(keys.get(i), list.get(i));
			}
		}
		return map;
	}

	public Map<T, T> toMap() {
		Map<T, T> map = new LinkedHashMap<T, T>();
		for (T t : list) {
			map.put(t, t);
		}
		return map;
	}

	public it.saari.interfaces.Stream<T> toStack() {
		List<T> tmp = new LinkedList<T>();
		tmp.addAll(list);
		Collections.reverse(tmp);
		return new Stream<T>(tmp);
	}

	public T get(int index) {
		return list.get(index);
	}

	public boolean anyMatch(Predicate<T> predicate) {
		for (T t : list) {
			if (predicate.test(t)) {
				return true;
			}
		}
		return false;
	}

	public boolean allMatch(Predicate<T> predicate) {
		for (T t : list) {
			if (!predicate.test(t)) {
				return false;
			}
		}
		return true;
	}

	public void forEach(Consumer<? super T> consumer) {
		for (T t : list) {
			consumer.accept(t);
		}
	}

	public void forEachOrder(Consumer<? super T> consumer, Comparator<T> cmp) {
		sorted(cmp).forEach(consumer);
	}

	public long count() {
		return list.size();
	}

	public Result<T> max(Comparator<T> comparator) {
		if (list.isEmpty()) {
			return null;
		}
		T res = list.get(0);
		for (int i = 1; i < list.size(); i++) {
			T t = list.get(i);
			if (comparator.compare(res, t) < 0) {
				res = t;
			}
		}
		return new Result<T>(res);
	}

	public Result<T> min(Comparator<T> comparator) {
		if (list.isEmpty()) {
			return null;
		}
		T res = list.get(0);
		for (int i = 1; i < list.size(); i++) {
			T t = list.get(i);
			if (comparator.compare(res, t) > 0) {
				res = t;
			}
		}
		return new Result<T>(res);
	}

	public T reduce(T identity, BinaryOperator<T> accumulator) {
		T result = identity;
		for (T t : list) {
			result = accumulator.apply(result, t);
		}
		return result;
	}

	// =========================================================================
	// Slicing Operations
	// =========================================================================

	public it.saari.interfaces.Stream<T> split(int from, int to) {
		return new Stream<T>(new LinkedList<T>(list.subList(from, to)));
	}

	public it.saari.interfaces.Stream<T> limit(int max) {
		return split(0, Math.min(max, list.size()));
	}

	public it.saari.interfaces.Stream<T> skip(int from) {
		return split(Math.min(from, list.size()), list.size());
	}

	public it.saari.interfaces.Stream<T> distinct() {
		LinkedHashSet<T> seen = new LinkedHashSet<T>();
		seen.addAll(list);
		return new Stream<T>(new LinkedList<T>(seen));
	}

	// =========================================================================
	// Print Operations
	// =========================================================================

	public void println() {
		for (T t : list) {
			write("", t, "\n");
		}
	}

	public void print() {
		for (T t : list) {
			write("", t, "");
		}
	}

	public void println(String prefix, String suffix) {
		for (T t : list) {
			write(prefix, t, suffix + "\n");
		}
	}

	public void print(String prefix, String suffix) {
		for (T t : list) {
			write(prefix, t, suffix);
		}
	}

	public void prependln(String prefix) {
		for (T t : list) {
			write(prefix, t, "\n");
		}
	}

	public void prepend(String prefix) {
		for (T t : list) {
			write(prefix, t, "");
		}
	}

	public void appendln(String suffix) {
		for (T t : list) {
			write("", t, suffix + "\n");
		}
	}

	public void append(String suffix) {
		for (T t : list) {
			write("", t, suffix);
		}
	}

	private void write(String prefix, T t, String suffix) {
		System.out.print(prefix + t + suffix);
	}

	// =========================================================================
	// Utility
	// =========================================================================

	/**
	 * Wrapper per il risultato di operazioni terminali come {@code max} e {@code min}.
	 *
	 * @param <W> il tipo del risultato.
	 */
	public static class Result<W> {

		private final W t;

		public Result(W t) {
			this.t = t;
		}

		public W get() {
			return t;
		}
	}

	/**
	 * Stampa le informazioni sulla libreria.
	 */
	public static void info() {
		Stream.of(
				"Questa piccola libreria nasce con l'idea di velocizzare le operazioni"
						+ " tipiche che si possono incontrare nel gestire collezioni di dati.\n"
						+ "Il pattern utilizzato e' quello delle Pipes & Filters con il quale e'"
						+ " possibile gestire un flusso o stream di dati.\n"
						+ "La nomenclatura resta fedele a Java 8.\n"
						+ "La libreria puo' essere utilizzata da Java 5+.\n"
						+ "Spero possa servire come sta servendo me in alcuni progetti datati"
						+ " e che fanno uso di tecnologie come Java 5.\n"
						+ "Per scambi di idee collaborazioni o altro: salvatorearianna@gmail.com")
				.print();
	}

}
