/*
 * Copyright 2011 Sonian Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.sonian.elasticsearch.plugin.jetty;

import com.sonian.elasticsearch.http.jetty.JettyHttpServerModule;

import java.util.Collection;

import org.elasticsearch.common.collect.Lists;
import org.elasticsearch.common.component.LifecycleComponent;
import org.elasticsearch.common.inject.Module;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.http.HttpServer;
import org.elasticsearch.plugins.AbstractPlugin;

/**
 * @author imotov
 */
public class JettyPlugin extends AbstractPlugin {

    private final Settings settings;
    private boolean enabledByDefault = true;

    public JettyPlugin(Settings settings) {
        this.settings = settings;
    }

    @Override
    public String name() {
        return "jetty";
    }

    @Override
    public String description() {
        return "Jetty Plugin Version: " + Version.number() + " (" + Version.date() + ")";
    }
    
    @Override
    public Collection<Class<? extends Module>> modules() {
        final Collection<Class<? extends Module>> modules = Lists.newArrayList();
        if (null != settings.get("sonian.elasticsearch.http.type", null)) {
            Class<? extends Module> httpServerModule = settings.getAsClass("sonian.elasticsearch.http.type", JettyHttpServerModule.class);
            modules.add(httpServerModule);
        }
        return modules;
    }
    
    @Override
    public Collection<Class<? extends LifecycleComponent>> services() {
        final Collection<Class<? extends LifecycleComponent>> services = Lists.newArrayList();
        if (null != settings.get("sonian.elasticsearch.http.type", null)) {
            services.add(HttpServer.class);
        }
        return services;
    }
    
    @Override
    public Settings additionalSettings() {
        if (null != settings.get("sonian.elasticsearch.http.type", null)) {
            return ImmutableSettings.settingsBuilder().put("http.enabled", false).build();
        }
        return ImmutableSettings.Builder.EMPTY_SETTINGS;
    }
}
