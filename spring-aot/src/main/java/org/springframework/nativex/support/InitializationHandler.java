/*
 * Copyright 2019-2021 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.nativex.support;

import java.util.Objects;
import java.util.Set;

import org.springframework.nativex.domain.init.InitializationDescriptor;

/**
 * 
 * @author Andy Clement
 */
public class InitializationHandler extends Handler {
	

	InitializationHandler(ConfigurationCollector collector) {
		super(collector);
	}
	

	public void registerInitializationDescriptor(InitializationDescriptor initializationDescriptor) {
		Set<String> buildtimeClasses = initializationDescriptor.getBuildtimeClasses();
		if (buildtimeClasses.size()!=0) {
			buildtimeClasses.stream()
			.map(c -> ts.resolveDotted(c, true)/*cl.findClassByName(c, false)*/).filter(Objects::nonNull)
			.forEach(collector::initializeAtBuildTime);
		}
		Set<String> runtimeClasses = initializationDescriptor.getRuntimeClasses();
		if (runtimeClasses.size()!=0) {
			runtimeClasses.stream()
			.map(c -> ts.resolveDotted(c,true)/*cl.findClassByName(c, false)*/).filter(Objects::nonNull)
			.forEach(collector::initializeAtRunTime);
		}
		Set<String> buildtimePackages = initializationDescriptor.getBuildtimePackages();
		if (buildtimePackages.size()!=0) {
			collector.initializeAtBuildTimePackages(buildtimePackages.toArray(new String[0]));
		}
		Set<String> runtimePackages = initializationDescriptor.getRuntimePackages();
		if (runtimePackages.size()!=0) {
			collector.initializeAtRunTimePackages(runtimePackages.toArray(new String[0]));
		}
	}
	
}
