/*
 * Copyright 2017 TomTom International B.V.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.tomtom.james.controller.webservice;

import com.tomtom.james.common.api.configuration.JamesControllerConfiguration;
import com.tomtom.james.common.api.configuration.StructuredConfiguration;

class WebserviceControllerConfiguration {

    private final StructuredConfiguration configuration;

    WebserviceControllerConfiguration(JamesControllerConfiguration jamesControllerConfiguration) {
        configuration = jamesControllerConfiguration.getProperties()
                .orElseGet(StructuredConfiguration.Empty::new);
    }

    int getPort() {
        return configuration
                .get("port")
                .map(StructuredConfiguration::asInteger)
                .orElse(7007);
    }

    int getMinThreads() {
        return configuration
                .get("minThreads")
                .map(StructuredConfiguration::asInteger)
                .orElse(1);
    }

    int getMaxThreads() {
        return configuration
                .get("maxThreads")
                .map(StructuredConfiguration::asInteger)
                .orElse(4);
    }
}
