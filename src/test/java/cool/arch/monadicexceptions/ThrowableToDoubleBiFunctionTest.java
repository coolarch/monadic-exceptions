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
import java.util.function.ToDoubleBiFunction;

import cool.arch.monadicexceptions.ThrowableToDoubleBiFunction;

public class ThrowableToDoubleBiFunctionTest extends
	AbstractLambdaTest<ThrowableToDoubleBiFunction<String, String>, ToDoubleBiFunction<String, String>> {

	@SuppressWarnings("boxing")
	public ThrowableToDoubleBiFunctionTest() {
		super(lambda -> lambda.applyAsDouble("1.5", "2.75") == (1.5 + 2.75),
			ThrowableToDoubleBiFunction::asToDoubleBiFunction, (t, u) -> {
				throw new IOException();
			}, (t, u) -> Double.valueOf(t) + Double.valueOf(u));
	}
}
