package com.komencash.backend.controller;

import com.komencash.backend.dto.law.LawSelectResponse;
import com.komencash.backend.service.LawService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/law")
public class LawController {

    @Autowired
    LawService lawService;

    @GetMapping("/{group_id}")
    public List<LawSelectResponse> findLawByGroupId(@PathVariable("group_id") int group_id) {
        return lawService.findLawByGroupId(group_id);
    }

}
