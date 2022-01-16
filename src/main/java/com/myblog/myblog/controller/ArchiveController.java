package com.myblog.myblog.controller;

import com.myblog.myblog.response.JsonResponse;
import com.myblog.myblog.service.ArchiveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/archive")
public class ArchiveController {

    @Autowired
    private ArchiveService archiveService;

    @GetMapping()
    public JsonResponse getAllArchive() {
        return archiveService.getAllArchives();
    }

    @GetMapping("/{year}")
    public JsonResponse getAllArchiveByYear(@PathVariable Integer year) {
        return archiveService.getArchivesByYear(year);
    }
}
