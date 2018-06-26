/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * 
 * @author Salvatore
 * @param <T>
 */
public class Stream<T> implements it.saari.interfaces.Stream<T> {
	private List<T> list;

	private Stream(List<T> list) {
		this.list = list;
	}

	// ***************************************************************************
	// COSTRUCTORS
	// ***************************************************************************

	/**
	 * Crea uno stream o flusso di elementi.
	 * 
	 * @param arr
	 *            Elementi dell'array.
	 * @return Uno stream di elementi T.
	 */
	public static <T> it.saari.interfaces.Stream<T> of(T... arr) {
		List<T> tmp = new LinkedList<T>();
		tmp.addAll(Arrays.asList(arr));
		return new Stream<T>(tmp);
	}

	/**
	 * Crea uno stream o flusso di elementi.
	 * 
	 * @param string
	 *            La stringa che andra' a comporre lo stream.
	 * @return Uno stream di elementi String dove ogni elemento e' un carattere
	 *         della stringa iniziale.
	 */
	public static it.saari.interfaces.Stream<String> of(String string) {
		List<String> tmp = new LinkedList<String>();
		for (char c : string.toCharArray())
			tmp.add(String.valueOf(c));
		return new Stream<String>(tmp);
	}

	/**
	 * Crea uno stream o flusso di elementi.
	 * 
	 * @param arr
	 *            Lista di elementi.
	 * @return Uno stream di elementi T.
	 */
	public static <T> it.saari.interfaces.Stream<T> of(List<T> arr) {
		List<T> tmp = new LinkedList<T>();
		tmp.addAll(arr);
		return new Stream<T>(tmp);
	}

	/**
	 * Crea uno stream o flusso di elementi.
	 * 
	 * @param arr
	 *            Albero di elementi.
	 * @return Uno stream di elementi T.
	 */
	public static <T> it.saari.interfaces.Stream<T> of(Set<T> arr) {
		List<T> tmp = new LinkedList<T>();
		tmp.addAll(arr);
		return new Stream<T>(tmp);
	}

	/**
	 * Crea uno stream o flusso di elementi.
	 * 
	 * @param map
	 *            Mappa di elementi.
	 * @return Uno stream di elementi Entry<K, V>.
	 */
	public static <K, V> it.saari.interfaces.Stream<Entry<K, V>> of(
			Map<K, V> map) {
		List<Entry<K, V>> tmp = new LinkedList<Entry<K, V>>();
		tmp.addAll(map.entrySet());
		return new Stream<Entry<K, V>>(tmp);
	}

	/**
	 * Crea uno stream o flusso di elementi.
	 * 
	 * @param map
	 *            Mappa di elementi.
	 * @return Uno stream dei valori della mappa.
	 */
	public static <K, V> it.saari.interfaces.Stream<V> ofValues(Map<K, V> map) {
		List<V> tmp = new LinkedList<V>();
		tmp.addAll(map.values());
		return new Stream<V>(tmp);
	}

	/**
	 * Crea uno stream o flusso di elementi.
	 * 
	 * @param map
	 *            Mappa di elementi.
	 * @return Uno stream delle chiavi della mappa.
	 */
	public static <K, V> it.saari.interfaces.Stream<K> ofKeys(Map<K, V> map) {
		List<K> tmp = new LinkedList<K>();
		tmp.addAll(map.keySet());
		return new Stream<K>(tmp);
	}

	public it.saari.interfaces.Stream<T> sorted(Comparator<T> cmp) {
		Collections.sort(list, cmp);
		return this;
	}

	public it.saari.interfaces.Stream<T> reverse(Comparator<T> cmp) {
		Comparator<T> revert = Collections.reverseOrder(cmp);
		Collections.sort(list, revert);
		return this;
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
				res += Integer.parseInt(t.toString());
			} else {
				res = 0;
				break;
			}
		}
		return res;
	}

	public <R> it.saari.interfaces.Stream<R> map(
			Function<? super T, ? extends R> mapper) {
		return new StreamMapping<T, R>(list).mapTo(mapper);
	}

	public List<T> toList() {
		return list;
	}

	public <R> Map<R, T> toMap(List<R> keys) {
		return new StreamMapping<T, R>(list).toMap(keys);
	}

	public <R> Map<T, T> toMap() {
		return new StreamMapping<T, T>(list).toMap(list);
	}

	private class StreamMapping<W, R> {

		public List<W> list;

		public StreamMapping(List<W> list) {
			this.list = list;
		}

		public it.saari.interfaces.Stream<R> mapTo(
				Function<? super W, ? extends R> mapper) {
			List<R> tmp = new LinkedList<R>();
			for (int i = 0; i < list.size(); i++) {
				W t = list.get(i);
				R r = mapper.apply(t);
				tmp.add(r);
			}
			return new Stream<R>(tmp);
		}

		public Map<R, W> toMap(List<R> keys) {
			Map<R, W> map = new LinkedHashMap<R, W>();
			for (int i = 0; i < list.size(); i++) {
				if (keys != null && keys.get(i) != null) {
					map.put(keys.get(i), list.get(i));
				}
			}
			return map;
		}
	}

	public boolean anyMatch(Predicate<T> predicate) {
		return Stream.of(list).filter(predicate).toList().size() > 0;
	}

	public boolean allMatch(Predicate<T> predicate) {
		return Stream.of(list).filter(predicate).toList().size() == list.size();
	}

	public void forEach(Consumer<? super T> consumer) {
		for (T t : list) {
			consumer.accept(t);
		}
	}

	public void forEachOrder(Consumer<? super T> consumer, Comparator<T> cmp) {
		Stream.of(list).sorted(cmp).forEach(consumer);
	}

	public static class Result<W> {

		private W t;

		public Result(W t) {
			this.t = t;
		}

		public W get() {
			return t;
		}
	}

	public long count() {
		return list.size();
	}

	public Result<T> max(Comparator<T> comparator) {
		T res = list.get(0);
		for (T t : list) {
			int c = comparator.compare(res, t);
			if (c < 0) {
				res = t;
			}
		}
		return new Result<T>(res);
	}

	public Result<T> min(Comparator<T> comparator) {
		T res = list.get(0);
		for (T t : list) {
			int c = comparator.compare(res, t);
			if (c > 0) {
				res = t;
			}
		}
		return new Result<T>(res);
	}

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

	public it.saari.interfaces.Stream<T> split(int from, int to) {
		return Stream.of(list.subList(from, to));
	}

	public it.saari.interfaces.Stream<T> limit(int max) {
		return Stream.of(list).split(0, max);
	}

	public it.saari.interfaces.Stream<T> skip(int from) {
		return Stream.of(list).split(from, list.size());
	}

	public it.saari.interfaces.Stream<T> distinct() {
		final LinkedList<T> newList = new LinkedList<T>();
		Stream.of(list).forEach(new Consumer<T>() {
			public void accept(T t) {
				if (!newList.contains(t)) {
					newList.add(t);
				}
			}
		});
		return Stream.of(newList);
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

	public T reduce(T identity, BinaryOperator<T> accumulator) {
		if (list == null)
			return identity;
		T t = reduction(identity, (list.size() - 1), accumulator);
		return t;
	}

	private T reduction(T identity, int index, BinaryOperator<T> accumulator) {
		T r = null;
		if ((index - 1) >= 0) {
			r = reduction(identity, (index - 1), accumulator);
		}
		if (r == null)
			r = identity;
		r = accumulator.apply(r, list.get(index));
		return r;

	}

	public static void info() {
		Stream.of(
				"Questa piccola libreria nasce con l'idea di velocizzare le operazioni"
						+ " tipiche che si possono incontrare nel gestire collezioni di dati.\nIl pattern"
						+ " utilizzato e' quello delle Pipes & Filters con il quale e' possibile gestire un"
						+ " flusso o stream di dati.\nLa nomenclatura resta fedele a Java 8.\n"
						+ "La libreria può essere utilizzata da Java 5+.\nSpero possa servire come sta servendo"
						+ " me in alcuni progetti datati e che fanno uso di tecnologie come Java 5.\nPer scambi di idee"
						+ " collaborazioni o altro: salvatorearianna@gmail.com")
				.print();

	}

	public <R> it.saari.interfaces.Stream<R> flatMap(
			Function<? super T, ? extends it.saari.interfaces.Stream<? extends R>> mapper) {
		final List<R> res = new LinkedList<R>();
		for(T t : list) {
			it.saari.interfaces.Stream<? extends R> stream = mapper.apply(t);
			stream.forEach(new Consumer<R>() {
				public void accept(R t) {
					res.add(t);
				}
			});
		}
		return Stream.of(res);
	}

	

}
