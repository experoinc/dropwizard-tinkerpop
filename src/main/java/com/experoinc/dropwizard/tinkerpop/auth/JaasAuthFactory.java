/*
 * Copyright 2017 Expero, Inc.
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

package com.experoinc.dropwizard.tinkerpop.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import org.apache.tinkerpop.gremlin.driver.AuthProperties;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * @author Chris Pounds
 */
@JsonTypeName("jaas")
public class JaasAuthFactory implements AuthFactory {

    @NotEmpty
    private String jaasEntry;

    @NotEmpty
    private String jaasProtocol;

    @JsonProperty
    public String getJaasEntry() {
        return jaasEntry;
    }

    @JsonProperty
    public void setJaasEntry(String jaasEntry) {
        this.jaasEntry = jaasEntry;
    }

    @JsonProperty
    public String getJaasProtocol() {
        return jaasProtocol;
    }

    @JsonProperty
    public void setJaasProtocol(String jaasProtocol) {
        this.jaasProtocol = jaasProtocol;
    }

    @Override
    public AuthProperties build() {
        return new AuthProperties()
                .with(AuthProperties.Property.JAAS_ENTRY, jaasEntry)
                .with(AuthProperties.Property.PROTOCOL, jaasProtocol);
    }
}
