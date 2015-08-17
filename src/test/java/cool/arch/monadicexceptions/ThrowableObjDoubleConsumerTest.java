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
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.ObjDoubleConsumer;

import org.junit.Before;

import com.google.common.util.concurrent.AtomicDouble;

import cool.arch.monadicexceptions.ThrowableObjDoubleConsumer;

public class ThrowableObjDoubleConsumerTest extends

AbstractLambdaTest<ThrowableObjDoubleConsumer<String>, ObjDoubleConsumer<String>> {

	private static final AtomicReference<String> param0 = new AtomicReference<>();

	private static final AtomicDouble param1 = new AtomicDouble();

	@Before
	public void setup() {
		param0.set(null);
	}

	public ThrowableObjDoubleConsumerTest() {
		super(lambda -> {
			lambda.accept("1.5", 1.5D);
			return param0.get()
				.equals("1.5") && param1.get() == 1.5D;
		}, ThrowableObjDoubleConsumer::asObjDoubleConsumer, (o, d) -> {
			throw new IOException();
		}, (o, d) -> {
			param0.set(o);
			param1.set(d);
		});
	}
}
