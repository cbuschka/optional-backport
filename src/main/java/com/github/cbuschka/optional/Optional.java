package com.github.cbuschka.optional;

import java.util.NoSuchElementException;
import java.util.Objects;

public final class Optional<T>
{
	private static final Optional<?> EMPTY = new Optional<>(null);

	private final T value;

	@SuppressWarnings("unchecked")
	public static <T> Optional<T> empty()
	{
		@SuppressWarnings("unchecked")
		Optional<T> t = (Optional<T>) EMPTY;
		return t;
	}

	public static <T> Optional<T> ofNullable(T value)
	{
		if (value == null)
		{
			return empty();
		}

		return new Optional<>(value);
	}

	public static <T> Optional<T> of(T value)
	{
		Objects.requireNonNull(value);

		return new Optional<>(value);
	}

	private Optional(T value)
	{
		this.value = value;
	}

	public boolean isPresent()
	{
		return this.value != null;
	}

	public T get()
	{
		if (value == null)
		{
			throw new NoSuchElementException("No value present");
		}

		return this.value;
	}

	public <R> Optional<R> map(Function<? super T, ? extends R> mapFunc)
	{
		Objects.requireNonNull(mapFunc);

		if (!isPresent())
		{
			return empty();
		}
		else
		{
			R result = mapFunc.apply(value);
			return Optional.ofNullable(result);
		}
	}

	public T orElseGet(Supplier<? extends T> supplier)
	{
		Objects.requireNonNull(supplier);

		return isPresent() ? value : supplier.get();
	}

	public boolean isEmpty()
	{
		return value == null;
	}

	public T orElse(T other)
	{
		if (isPresent())
		{
			return get();
		}

		return other;
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o) return true;

		if (o == null || getClass() != o.getClass()) return false;

		Optional<?> optional = (Optional<?>) o;
		return Objects.equals(value, optional.value);
	}

	@Override
	public int hashCode()
	{
		if (this.value == null)
		{
			return 0;
		}

		return this.value.hashCode();
	}

	@Override
	public String toString()
	{
		return value != null
				? String.format("Optional[%s]", value)
				: "Optional.empty";
	}
}
