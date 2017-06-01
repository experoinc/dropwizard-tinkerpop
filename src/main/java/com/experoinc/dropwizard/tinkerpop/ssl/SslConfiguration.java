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

package com.experoinc.dropwizard.tinkerpop.ssl;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Chris Pounds
 */
public class SslConfiguration {

    private boolean enabled = false;

    private String trustCertChainFile = null;

    private String keyCertChainFile = null;

    private String keyFile = null;

    private String keyPassword = null;

    @JsonProperty
    public boolean isEnabled() {
        return enabled;
    }

    @JsonProperty
    public void setEnabled(boolean enabled) {
        this.enabled= enabled;
    }

    @JsonProperty
    public String getTrustCertChainFile() {
        return trustCertChainFile;
    }

    @JsonProperty
    public void setTrustCertChainFile(String trustCertChainFile) {
        this.trustCertChainFile = trustCertChainFile;
    }

    @JsonProperty
    public String getKeyCertChainFile() {
        return keyCertChainFile;
    }

    @JsonProperty
    public void setKeyCertChainFile(String keyCertChainFile) {
        this.keyCertChainFile = keyCertChainFile;
    }

    @JsonProperty
    public String getKeyFile() {
        return keyFile;
    }

    @JsonProperty
    public void setKeyFile(String keyFile) {
        this.keyFile = keyFile;
    }

    @JsonProperty
    public String getKeyPassword() {
        return keyPassword;
    }

    @JsonProperty
    public void setKeyPassword(String keyPassword) {
        this.keyPassword = keyPassword;
    }
}
