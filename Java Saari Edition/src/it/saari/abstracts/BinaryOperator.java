package it.saari.abstracts;

import it.saari.interfaces.BiFunction;

public abstract class BinaryOperator<T> implements BiFunction<T, T, T> {
	public abstract T apply(T t, T u);
}
