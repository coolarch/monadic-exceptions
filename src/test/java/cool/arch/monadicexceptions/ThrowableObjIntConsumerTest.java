package cool.arch.monadicexceptions;

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
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.ObjIntConsumer;

import org.junit.Before;

import cool.arch.monadicexceptions.ThrowableObjIntConsumer;

public class ThrowableObjIntConsumerTest extends
	AbstractLambdaTest<ThrowableObjIntConsumer<String>, ObjIntConsumer<String>> {

	private static final AtomicReference<String> param0 = new AtomicReference<>();

	private static final AtomicInteger param1 = new AtomicInteger();

	@Before
	public void setup() {
		param0.set(null);
		param1.set(0);
	}

	public ThrowableObjIntConsumerTest() {
		super(lambda -> {
			lambda.accept("123", 123);
			return param0.get()
				.equals("123") && param1.get() == 123;
		}, ThrowableObjIntConsumer::asObjIntConsumer, (o, i) -> {
			throw new IOException();
		}, (o, i) -> {
			param0.set(o);
			param1.set(i);
		});
	}
}
