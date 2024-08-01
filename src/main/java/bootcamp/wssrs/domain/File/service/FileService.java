package bootcamp.wssrs.domain.File.service;


import bootcamp.wssrs.domain.File.entity.File;
import bootcamp.wssrs.domain.File.repository.FileRepository;
import bootcamp.wssrs.domain.Notice.entity.Notice;
import bootcamp.wssrs.global.s3.S3FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class FileService {

    private final FileRepository fileRepository;
    private final S3FileService s3FileService;

    public void create(MultipartFile file, Notice notice) {
        String filename = file.getOriginalFilename();
        String url = s3FileService.createFile("notice", file);
        File entity = File.create(url, filename, notice);
        fileRepository.save(entity);
    }
}
