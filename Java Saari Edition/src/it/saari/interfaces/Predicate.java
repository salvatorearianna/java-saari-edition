package it.saari.interfaces;

public interface Predicate<T> extends Filter<T> {
	boolean test(T t);
}
