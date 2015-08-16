package cool.arch.monadic.exceptions.function;

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

import java.io.IOException;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

import org.junit.Before;

import cool.arch.monadic.exceptions.Wrap;

public class ThrowableConsumerTest extends AbstractLambdaTest<ThrowableConsumer<String>, Consumer<String>> {
	
	private static final AtomicReference<String> captured = new AtomicReference<>();

	@Before
	public void setup() {
		captured.set(null);
	}

	public ThrowableConsumerTest() {
		super(
			lambda -> {lambda.accept("foo"); return "foo".equals(captured.get()); },
		Wrap::asConsumer,
		s -> { throw new IOException(); },
		s -> captured.set(s));
	}
}
