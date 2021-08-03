package com.github.cbuschka.optional;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.nio.charset.StandardCharsets;
import java.util.NoSuchElementException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class OptionalTest
{
	@Rule
	public ExpectedException expectedException = ExpectedException.none();

	@Test
	public void emptyOptionalIsEmpty()
	{
		assertTrue(Optional.empty().isEmpty());
	}

	@Test
	public void emptyOptionalIsNotPresent()
	{
		assertFalse(Optional.empty().isPresent());
	}

	@Test
	public void optionalOfNullIsEmpty()
	{
		assertTrue(Optional.ofNullable(null).isEmpty());
	}

	@Test
	public void optionalOfNullIsNotPresent()
	{
		assertFalse(Optional.ofNullable(null).isPresent());
	}

	@Test
	public void optionalOfNonNullIsNotEmpty()
	{
		assertFalse(Optional.ofNullable("").isEmpty());
	}

	@Test
	public void optionalOfNonNullIsPresent()
	{
		assertTrue(Optional.ofNullable("").isPresent());
	}

	@Test
	public void hashCodeOfEmptyIsZero()
	{
		assertEquals(0, Optional.empty().hashCode());
	}

	@Test
	public void hashCodeOfNonEmptyIsHashCodeOfValue()
	{
		String value = "";

		assertEquals(value.hashCode(), Optional.of(value).hashCode());
	}

	@Test
	public void getReturnsValue()
	{
		String value = "";

		assertEquals(value, Optional.of(value).get());
	}

	@Test
	public void getFailsWhenEmpty()
	{
		expectedException.expect(NoSuchElementException.class);

		Optional.empty().get();
	}

	@Test
	public void orElseReturnsValueIfOptionalNotEmpty()
	{
		String value = "value";

		assertEquals(value, Optional.of(value).orElse("other"));
	}

	@Test
	public void orElseReturnsFallbackIfOptionalEmpty()
	{
		String other = "other";

		assertEquals(other, Optional.ofNullable(null).orElse(other));
	}

	@Test
	public void emptyOptionalsAreEqual()
	{
		assertEquals(Optional.ofNullable(null), Optional.empty());
	}

	@Test
	public void optionalsWithEqualValueAreEqual()
	{
		String value = "value";
		String value2 = new String(value.getBytes(StandardCharsets.UTF_8));

		assertEquals(Optional.of(value), Optional.of(value2));
	}

	@Test
	public void mapOfEmptyGivesEmpty()
	{
		Optional<Object> given = Optional.empty();

		Optional<Object> result = given.map(new Function<Object, Object>()
		{
			@Override
			public Object apply(Object o)
			{
				fail("Should not be called.");
				return null;
			}
		});

		assertTrue(result.isEmpty());
	}

	@Test
	public void mapShouldCallMappingFunc()
	{
		Optional<String> given = Optional.of("foo");

		String result = given.map(new Function<String, String>()
		{
			@Override
			public String apply(String s)
			{
				return "bar";
			}
		}).get();

		assertEquals("bar", result);
	}

	@Test
	public void orElseGetOfNonEmptyGivesValue()
	{
		Optional<String> given = Optional.of("foo");

		String result = given.orElseGet(new Supplier<String>()
		{
			@Override
			public String get()
			{
				fail("Should not be called.");
				return null;
			}
		});

		assertEquals(result, "foo");
	}

	@Test
	public void orElseGetOfEmptyShouldGetFromSupplier()
	{
		Optional<String> given = Optional.empty();

		String result = given.orElseGet(new Supplier<String>()
		{
			@Override
			public String get()
			{
				return "foo";
			}
		});

		assertEquals("foo", result);
	}
}
