/*******************************************************************************
 * Copyright (c) 2016, 2018 QNX Software Systems and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors
 * 		Red Hat Inc. - modified for use with Meson build
 *******************************************************************************/
package org.eclipse.cdt.internal.meson.core;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.cdt.core.build.ICBuildConfiguration;
import org.eclipse.cdt.core.build.ICBuildConfigurationManager;
import org.eclipse.cdt.core.build.ICBuildConfigurationProvider;
import org.eclipse.cdt.core.build.IToolChain;
import org.eclipse.cdt.core.build.IToolChainManager;
import org.eclipse.cdt.meson.core.Activator;
import org.eclipse.cdt.meson.core.IMesonToolChainFile;
import org.eclipse.cdt.meson.core.IMesonToolChainManager;
import org.eclipse.core.resources.IBuildConfiguration;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Platform;

public class MesonBuildConfigurationProvider implements ICBuildConfigurationProvider {

	public static final String ID = "org.eclipse.cdt.meson.core.provider"; //$NON-NLS-1$

	private IMesonToolChainManager manager = Activator.getService(IMesonToolChainManager.class);
	private ICBuildConfigurationManager configManager = Activator.getService(ICBuildConfigurationManager.class);

	@Override
	public String getId() {
		return ID;
	}

	@Override
	public ICBuildConfiguration getCBuildConfiguration(IBuildConfiguration config, String name) throws CoreException {
		if (config.getName().equals(IBuildConfiguration.DEFAULT_CONFIG_NAME)) {
			IToolChain toolChain = null;

			// try the toolchain for the local target
			Map<String, String> properties = new HashMap<>();
			properties.put(IToolChain.ATTR_OS, Platform.getOS());
			properties.put(IToolChain.ATTR_ARCH, Platform.getOSArch());
			IToolChainManager toolChainManager = Activator.getService(IToolChainManager.class);
			for (IToolChain tc : toolChainManager.getToolChainsMatching(properties)) {
				toolChain = tc;
				break;
			}

			// local didn't work, try and find one that does
			if (toolChain == null) {
				for (IToolChain tc : toolChainManager.getToolChainsMatching(new HashMap<>())) {
					toolChain = tc;
					break;
				}
			}

			if (toolChain != null) {
				return new MesonBuildConfiguration(config, name, toolChain);
			} else {
				// No valid combinations
				return null;
			}
		} else {
			return new MesonBuildConfiguration(config, name);
		}
	}

	@Override
	public ICBuildConfiguration createBuildConfiguration(IProject project, IToolChain toolChain, String launchMode,
			IProgressMonitor monitor) throws CoreException {
		// get matching toolchain file if any
		Map<String, String> properties = new HashMap<>();
		String os = toolChain.getProperty(IToolChain.ATTR_OS);
		if (os != null && !os.isEmpty()) {
			properties.put(IToolChain.ATTR_OS, os);
		}
		String arch = toolChain.getProperty(IToolChain.ATTR_ARCH);
		if (arch != null && !arch.isEmpty()) {
			properties.put(IToolChain.ATTR_ARCH, arch);
		}
		IMesonToolChainFile file = manager.getToolChainFileFor(toolChain);
		if (file == null) {
			Collection<IMesonToolChainFile> files = manager.getToolChainFilesMatching(properties);
			if (!files.isEmpty()) {
				file = files.iterator().next();
			}
		}

		// create config
		StringBuilder configName = new StringBuilder("meson."); //$NON-NLS-1$
		configName.append(launchMode);
		if (os != null) {
			configName.append('.');
			configName.append(os);
		}
		if (arch != null && !arch.isEmpty()) {
			configName.append('.');
			configName.append(arch);
		}
		String name = configName.toString();
		int i = 0;
		while (configManager.hasConfiguration(this, project, name)) {
			name = configName.toString() + '.' + (++i);
		}

		IBuildConfiguration config = configManager.createBuildConfiguration(this, project, name, monitor);
		MesonBuildConfiguration mesonConfig = new MesonBuildConfiguration(config, name, toolChain, file,
				launchMode);
		configManager.addBuildConfiguration(config, mesonConfig);
		return mesonConfig;
	}

}