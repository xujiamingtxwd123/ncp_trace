package com.ruoyi.traces.controller;

import com.ruoyi.common.annotation.RepeatSubmit;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.traces.domain.Process;
import com.ruoyi.traces.domain.Transport;
import com.ruoyi.traces.service.ProcessService;
import com.ruoyi.traces.service.TransportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/traces")
public class TransportController extends BaseController {

    @Autowired
    private TransportService transportService;

    @PostMapping("/transport")
    @RepeatSubmit
    public AjaxResult saveTransport(@RequestBody Transport transport) throws Exception {
        return AjaxResult.success(transportService.saveTransport(transport));
    }

    @GetMapping("/transport")
    public AjaxResult listTransport() throws Exception {
        return AjaxResult.success(transportService.listTransport());
    }
}