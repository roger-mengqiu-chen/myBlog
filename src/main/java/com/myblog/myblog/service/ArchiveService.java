package com.myblog.myblog.service;

import com.myblog.myblog.constant.Status;
import com.myblog.myblog.entity.Archive;
import com.myblog.myblog.entity.Post;
import com.myblog.myblog.mapper.ArchiveMapper;
import com.myblog.myblog.mapper.PostMapper;
import com.myblog.myblog.response.JsonResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ArchiveService {
    @Autowired
    private ArchiveMapper archiveMapper;
    @Autowired
    private PostMapper postMapper;

    Logger logger = LoggerFactory.getLogger(ArchiveService.class);

    public JsonResponse getAllArchives() {
        Map<String, Integer> archivesAndCount = new HashMap<>();
        List<String> archiveNames = archiveMapper.findAllArchiveNames();
        for (int i = 0; i < archiveNames.size(); i++) {
            String name = archiveNames.get(i);
            int count = archiveMapper.getArchiveCountByName(name);
            archivesAndCount.put(name, count);
        }
        return new JsonResponse(Status.SUCCESS, archivesAndCount);
    }

    public JsonResponse getArchivesByYear(int year) {
        try {
            List<Archive> archives = archiveMapper.findArchivesByYear(year);
            if (archives.size() == 0) {
                return new JsonResponse(Status.ARCHIVE_NOT_FOUND, "No archives for this year");
            }
            else {
                return new JsonResponse(Status.SUCCESS);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new JsonResponse(Status.SERVER_ERROR);
        }
    }
}
