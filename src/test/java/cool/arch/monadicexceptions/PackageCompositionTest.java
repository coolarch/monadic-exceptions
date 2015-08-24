package cool.arch.monadicexceptions;

/*
 * @formatter:off
 * cool.arch.monadicexceptions:monadic-exceptions
 * %%
 * Copyright (C) 2015 CoolArch
 * %%
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 * @formatter:on
 */

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.HashSet;

import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.junit.Test;
import org.reflections.Reflections;
import org.reflections.scanners.ResourcesScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

import cool.arch.monadicexceptions.ThrowableFunction;

public class PackageCompositionTest {

	private static final String THROWABLE_FUNCTIONAL_INTERFACE_PACKAGE = ThrowableFunction.class.getPackage()
		.getName();

	@Test
	public final void test() {
		//		assertEquals("java.util.function", FUNCTIONAL_INTERFACE_PACKAGE);
		assertEquals("cool.arch.monadicexceptions", THROWABLE_FUNCTIONAL_INTERFACE_PACKAGE);

		final Set<String> expectedFunctionalInterfaceNames = new HashSet<>(asList("BiConsumer", "BiFunction", "BinaryOperator", "BiPredicate", "BooleanSupplier", "Consumer", "DoubleBinaryOperator",
			"DoubleConsumer", "DoubleFunction", "DoublePredicate", "DoubleSupplier", "DoubleToIntFunction", "DoubleToLongFunction", "DoubleUnaryOperator", "Function", "IntBinaryOperator",
			"IntConsumer", "IntFunction", "IntPredicate", "IntSupplier", "IntToDoubleFunction", "IntToLongFunction", "IntUnaryOperator", "LongBinaryOperator", "LongConsumer", "LongFunction",
			"LongPredicate", "LongSupplier", "LongToDoubleFunction", "LongToIntFunction", "LongUnaryOperator", "ObjDoubleConsumer", "ObjIntConsumer", "ObjLongConsumer", "Predicate", "Supplier",
			"ToDoubleBiFunction", "ToDoubleFunction", "ToIntBiFunction", "ToIntFunction", "ToLongBiFunction", "ToLongFunction", "UnaryOperator"));

		//		expectedFunctionalInterfaceNames.forEach(System.out::println);

		final Set<String> ourInterfacesAndTestNames = new Reflections(new ConfigurationBuilder().setUrls(ClasspathHelper.forPackage(THROWABLE_FUNCTIONAL_INTERFACE_PACKAGE))
			.setScanners(new ResourcesScanner())).getResources(Pattern.compile(".*\\.class"))
				.stream()
				.filter(s -> s.startsWith("cool/arch/monadicexceptions/Throwable"))
				.map(s -> s.replaceAll("^cool\\/arch\\/monadicexceptions\\/", ""))
				.map(s -> s.replaceAll("\\.class$", ""))
				.distinct()
				.collect(Collectors.toSet());

		final Set<String> interfaceNames = ourInterfacesAndTestNames.stream()
			.filter(s -> !s.endsWith("Test"))
			.collect(Collectors.toSet());

		final Set<String> testNames = ourInterfacesAndTestNames.stream()
			.filter(s -> s.endsWith("Test"))
			.collect(Collectors.toSet());

		assertFalse(interfaceNames.isEmpty());
		assertFalse(testNames.isEmpty());

		final long interfacesWithoutTestsCount = interfaceNames.stream()
			.map(s -> s + "Test")
			.filter(s -> !testNames.contains(s))
			.peek(System.out::println)
			.count();

		assertEquals(0, interfacesWithoutTestsCount);

		long functionalInterfacesWithoutThrowableCount = expectedFunctionalInterfaceNames.stream()
			.map(s -> "Throwable" + s)
			.filter(s -> !interfaceNames.contains(s))
			.peek(System.out::println)
			.count();

		assertEquals(0, functionalInterfacesWithoutThrowableCount);
		assertFalse(expectedFunctionalInterfaceNames.isEmpty());
		assertFalse(ourInterfacesAndTestNames.isEmpty());
	}
}
