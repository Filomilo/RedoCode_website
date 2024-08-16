package com.redocode.backend.database;
import com.redocode.backend.VmAcces.CodeRunners.CODE_RUNNER_TYPE;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProgrammingLanguageRepository extends JpaRepository<ProgrammingLanguage, Long>{

    public ProgrammingLanguage findByName(String name);
}
