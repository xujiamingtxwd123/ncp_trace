package com.ruoyi.traces.controller;

import com.ruoyi.common.annotation.RepeatSubmit;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.traces.domain.Crop;
import com.ruoyi.traces.service.CropService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/traces")
public class CropController extends BaseController {

    @Autowired
    private CropService cropService;

    @PostMapping("/ncp")
    @RepeatSubmit
    public AjaxResult saveCrops(@RequestBody Crop crop) throws Exception {
        return AjaxResult.success(cropService.saveCrops(crop));
    }

    @GetMapping("/ncp")
    public AjaxResult listCrops() throws Exception {
        return AjaxResult.success(cropService.listCrops());
    }
}