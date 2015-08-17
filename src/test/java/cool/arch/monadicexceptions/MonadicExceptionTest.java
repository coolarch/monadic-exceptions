/**
 * 
 */
package cool.arch.monadicexceptions;

import static org.junit.Assert.assertFalse;

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

import org.junit.Test;

/**
 * 
 */
public class MonadicExceptionTest {

	/**
	 * Test method for {@link cool.arch.monadicexceptions.MonadicException#when(java.lang.Class)}.
	 * @throws IOException 
	 */
	@Test
	public final void testWhen() throws IOException {
		new MonadicException(new IOException()).when(IOException.class)
			.unwrapAs(IOException.class)
			.thenThrow();
	}

	/**
	 * Test method for {@link cool.arch.monadicexceptions.MonadicException.AbstractMonad#unwrapAs(java.lang.Class)}.
	 */
	@Test
	public void testUnwrapAs_nullCause() throws Exception {
		final boolean result = new MonadicException(null).when(IOException.class)
			.unwrapAs(IOException.class)
			.isPresent();

		assertFalse(result);
	}

	/**
	 * Test method for {@link cool.arch.monadicexceptions.MonadicException.AbstractMonad#unwrapAs(java.lang.Class)}.
	 */
	@Test
	public void testUnwrapAs_nullExceptionClass() throws Exception {
		final boolean result = new MonadicException(new IOException()).when(IOException.class)
			.unwrapAs(null)
			.isPresent();

		assertFalse(result);
	}
}
