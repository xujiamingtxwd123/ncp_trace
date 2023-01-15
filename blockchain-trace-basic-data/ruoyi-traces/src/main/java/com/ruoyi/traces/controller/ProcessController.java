package com.ruoyi.traces.controller;

import com.ruoyi.common.annotation.RepeatSubmit;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.traces.domain.Crop;
import com.ruoyi.traces.domain.Process;
import com.ruoyi.traces.service.CropService;
import com.ruoyi.traces.service.ProcessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/traces")
public class ProcessController extends BaseController {

    @Autowired
    private ProcessService processService;

    @PostMapping("/process")
    @RepeatSubmit
    public AjaxResult saveProcess(@RequestBody Process process) throws Exception {
        return AjaxResult.success(processService.saveProcess(process));
    }

    @GetMapping("/process")
    public AjaxResult listProcess() throws Exception {
        return AjaxResult.success(processService.listProcess());
    }
}