package com.redocode.backend.database;

import com.redocode.backend.Messages.ExerciseListRequestMessage;
import lombok.extern.java.Log;
import org.hibernate.query.Page;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ContextConfiguration;
import com.redocode.backend.database.Excersize;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@ContextConfiguration
@Log
class ExerciseRepositoryTest {
    @Autowired
    private ExerciseRepository exerciseRepository;
    @Test
    void getSimpleExcersizeList() {
        List<ExcersizeListEntry> list=exerciseRepository.getSimpleExcersizeList();
        log.info("list size: "+ Arrays.toString(list.toArray()));
    }

    @Test
    void getExeciseListFromQuery() {

        ExerciseListRequestMessage req= ExerciseListRequestMessage.
                builder().page(1).rowsPerPage(10).sortBy("name").sortDirection(false).build();


        org.springframework.data.domain.Page
                <com.redocode.backend.database.Excersize> list=

        exerciseRepository.findAll(PageRequest.of(
                req.getPage(),
                req.getRowsPerPage(),
                req.isSortDirection()? Sort.Direction.ASC: Sort.Direction.DESC,
                "id"
        ));
        log.info("list  "+ list.toString() );
    }
}