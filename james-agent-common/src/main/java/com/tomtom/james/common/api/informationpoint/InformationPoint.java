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

package com.tomtom.james.common.api.informationpoint;

import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class InformationPoint {

    protected String className;
    protected String methodName;
    protected String script;
    protected Integer sampleRate;

    public InformationPoint() {
    }

    public String getClassName() {
        return className;
    }

    public String getMethodName() {
        return methodName;
    }

    public Optional<String> getScript() {
        return Optional.ofNullable(script);
    }

    public int getSampleRate() {
        return Optional.ofNullable(sampleRate).orElse(100);
    }

    public List<String> splittedScriptLines() {
        if (script != null) {
            return Arrays.asList(script.split("\n"));
        } else {
            return Collections.emptyList();
        }
    }

    @Override
    public String toString() {
        return className + '#' + methodName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InformationPoint that = (InformationPoint) o;
        return Objects.equals(className, that.className) &&
                Objects.equals(methodName, that.methodName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(className, methodName);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private static final int CLASS_NAME_INDEX = 0;
        private static final int METHOD_NAME_INDEX = 1;

        private String className;
        private String methodName;
        private String script;
        private Integer sampleRate;

        private Builder() {
        }

        public Builder withClassName(String className) {
            this.className = className;
            return this;
        }

        public Builder withMethodName(String methodName) {
            this.methodName = methodName;
            return this;
        }

        public Builder withMethodReference(String encodedReference) {
            List<String> parts = Pattern.compile("#")
                    .splitAsStream(Objects.requireNonNull(encodedReference))
                    .filter(s -> !s.isEmpty())
                    .collect(Collectors.toList());
            className = parts.get(CLASS_NAME_INDEX);
            methodName = parts.get(METHOD_NAME_INDEX);
            return this;
        }

        public Builder withScript(String script) {
            this.script = script;
            return this;
        }

        public Builder withSampleRate(Integer sampleRate) {
            this.sampleRate = sampleRate;
            return this;
        }

        public Builder copyOf(InformationPoint copyFrom) {
            this.className = copyFrom.className;
            this.methodName = copyFrom.methodName;
            this.script = copyFrom.script;
            this.sampleRate = copyFrom.sampleRate;
            return this;
        }

        public InformationPoint build() {
            InformationPoint ip = new InformationPoint();
            ip.className = Objects.requireNonNull(className);
            ip.methodName = Objects.requireNonNull(methodName);
            ip.script = script;
            ip.sampleRate = sampleRate;
            return ip;
        }
    }
}