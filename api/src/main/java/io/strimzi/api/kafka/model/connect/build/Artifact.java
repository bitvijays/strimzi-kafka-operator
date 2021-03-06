/*
 * Copyright Strimzi authors.
 * License: Apache License 2.0 (see the file LICENSE or http://apache.org/licenses/LICENSE-2.0.html).
 */
package io.strimzi.api.kafka.model.connect.build;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.strimzi.api.kafka.model.UnknownPropertyPreserving;
import io.strimzi.crdgenerator.annotations.Description;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Abstract baseclass for different representations of connector artifacts, discriminated by {@link #getType() type}.
 */
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.EXISTING_PROPERTY,
        property = "type"
)
@JsonSubTypes(
        {
            @JsonSubTypes.Type(value = JarArtifact.class, name = Artifact.TYPE_JAR),
            @JsonSubTypes.Type(value = TgzArtifact.class, name = Artifact.TYPE_TGZ)
        }
)
@JsonInclude(JsonInclude.Include.NON_NULL)
@EqualsAndHashCode
public abstract class Artifact implements UnknownPropertyPreserving, Serializable {
    private static final long serialVersionUID = 1L;

    public static final String TYPE_JAR = "jar";
    public static final String TYPE_TGZ = "tgz";

    private Map<String, Object> additionalProperties = new HashMap<>(0);

    @Description("Artifact type. " +
            "Currently, the supported artifact types are `tgz` and `jar`.")
    public abstract String getType();

    @Override
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @Override
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }
}
