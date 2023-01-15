package com.ruoyi.traces.controller;

import com.ruoyi.common.annotation.RepeatSubmit;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.traces.domain.Sale;
import com.ruoyi.traces.domain.Transport;
import com.ruoyi.traces.service.SaleService;
import com.ruoyi.traces.service.TransportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/traces")
public class SaleController extends BaseController {

    @Autowired
    private SaleService saleService;

    @PostMapping("/sale")
    @RepeatSubmit
    public AjaxResult saveSale(@RequestBody Sale sale) throws Exception {
        return AjaxResult.success(saleService.saveSale(sale));
    }

    @GetMapping("/sale")
    public AjaxResult listSale() throws Exception {
        return AjaxResult.success(saleService.listSale());
    }
}