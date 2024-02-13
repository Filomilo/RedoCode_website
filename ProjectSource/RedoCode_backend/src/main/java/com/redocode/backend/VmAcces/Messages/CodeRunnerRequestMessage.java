package com.redocode.backend.VmAcces.Messages;

import com.redocode.backend.Auth.User;
import com.redocode.backend.VmAcces.CodeRunners.CodeRunner;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

@Slf4j
@Getter
@Setter
@Builder
public class CodeRunnerRequestMessage implements Comparable {

    private User userRequesting;
    private CodeRunner.CoderunnerTypes codeRunnerType;

    public CodeRunnerRequestMessage(User userRequesting, CodeRunner.CoderunnerTypes codeRunnerType) {
        this.userRequesting = userRequesting;
        this.codeRunnerType = codeRunnerType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CodeRunnerRequestMessage that = (CodeRunnerRequestMessage) o;
        return Objects.equals(userRequesting, that.userRequesting);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userRequesting);
    }

    @Override
    public int compareTo( Object o) {
        CodeRunnerRequestMessage crm=(CodeRunnerRequestMessage)o;
        return this.userRequesting.compareTo(crm.getUserRequesting());
    }
}
