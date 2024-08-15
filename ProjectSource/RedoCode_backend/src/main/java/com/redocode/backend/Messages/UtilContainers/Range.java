package com.redocode.backend.Messages.UtilContainers;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import org.hibernate.dialect.PostgreSQLCastingInetJdbcType;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.usertype.UserType;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Value
@SuperBuilder
@Jacksonized
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class Range implements Serializable{ //todo: change to postgres database  type mappign
    @NotNull
    Float min;
    @NotNull
    Float max;

    public Range(Integer min, Integer max) {
       this.min=Float.valueOf (min);
        this.max=Float.valueOf (max);
    }
}
