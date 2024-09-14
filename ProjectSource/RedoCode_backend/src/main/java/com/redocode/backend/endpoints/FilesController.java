package com.redocode.backend.endpoints;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.redocode.backend.Messages.ExcerciseDataMessage;
import com.redocode.backend.Messages.ExerciseListRequestMessage;
import com.redocode.backend.database.Excersize;
import com.redocode.backend.database.ExcersizeListEntry;
import com.redocode.backend.database.ExerciseRepository;
import com.redocode.backend.database.MediaRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;

@RestController
@Slf4j
@RequestMapping("/public/files")
public class FilesController {



    @Autowired
    MediaRepository mediaRepository;

    private static HttpHeaders getHeadersForExtesnion(String extension)
    {
        HttpHeaders headers = new HttpHeaders();
        switch (extension)
        {
            case "jpeg":  headers.setContentType(MediaType.IMAGE_JPEG);; break;
            case "png":  headers.setContentType(MediaType.IMAGE_PNG); break;
            case "gif":  headers.setContentType(MediaType.IMAGE_GIF); break;
            case "json": headers.setContentType(MediaType.APPLICATION_JSON); break;
            case "xml": headers.setContentType(MediaType.APPLICATION_XML); break;

        }
        return headers;

    }


    @GetMapping("/media/{uuid}")
    public  @ResponseBody ResponseEntity<byte[]> getMediaImage(@PathVariable("uuid")String uuid)  {
        log.info("getMediaImage: "+FilenameUtils.getBaseName(uuid) );
        UUID uuidRetrived = UUID.fromString(FilenameUtils.getBaseName(uuid));
        byte[] data= mediaRepository.getReferenceById(uuidRetrived).getData();
        ResponseEntity<byte[]> responseEntity=new ResponseEntity<>(data,getHeadersForExtesnion(FilenameUtils.getExtension(uuid)),HttpStatus.OK);
        return responseEntity;
    }


}
