package cool.arch.monadicexceptions;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.HashSet;

/*
 * #%L cool.arch.monadicexceptions:monadic-exceptions %% Copyright (C) 2015 CoolArch %%
 * Licensed to the Apache Software Foundation (ASF) under one or more contributor license
 * agreements. See the NOTICE file distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except in compliance with the
 * License. You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software distributed under the
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions
 * and limitations under the License. #L%
 */

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

		final Set<String> expectedFunctionalInterfaceNames =
			new HashSet<>(asList("BiConsumer", "BiFunction", "BinaryOperator", "BiPredicate", "BooleanSupplier",
				"Consumer", "DoubleBinaryOperator", "DoubleConsumer", "DoubleFunction", "DoublePredicate",
				"DoubleSupplier", "DoubleToIntFunction", "DoubleToLongFunction", "DoubleUnaryOperator", "Function",
				"IntBinaryOperator", "IntConsumer", "IntFunction", "IntPredicate", "IntSupplier",
				"IntToDoubleFunction", "IntToLongFunction", "IntUnaryOperator", "LongBinaryOperator", "LongConsumer",
				"LongFunction", "LongPredicate", "LongSupplier", "LongToDoubleFunction", "LongToIntFunction",
				"LongUnaryOperator", "ObjDoubleConsumer", "ObjIntConsumer", "ObjLongConsumer", "Predicate", "Supplier",
				"ToDoubleBiFunction", "ToDoubleFunction", "ToIntBiFunction", "ToIntFunction", "ToLongBiFunction",
				"ToLongFunction", "UnaryOperator"));

		//		expectedFunctionalInterfaceNames.forEach(System.out::println);

		final Set<String> ourInterfacesAndTestNames =
			new Reflections(new ConfigurationBuilder().setUrls(
				ClasspathHelper.forPackage(THROWABLE_FUNCTIONAL_INTERFACE_PACKAGE))
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
