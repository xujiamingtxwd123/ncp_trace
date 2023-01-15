package com.ruoyi.traces.controller;

import com.ruoyi.common.annotation.RepeatSubmit;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.traces.domain.Transport;
import com.ruoyi.traces.service.TraceService;
import com.ruoyi.traces.service.TransportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/traces")
public class TraceController extends BaseController {

    @Autowired
    private TraceService traceService;

    @GetMapping("/trace/{productID}")
    public AjaxResult trace(@PathVariable("productID") String productID) throws Exception {
        return AjaxResult.success(traceService.getTrace(productID));
    }
}