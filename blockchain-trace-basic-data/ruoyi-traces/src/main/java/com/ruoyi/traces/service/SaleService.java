package com.ruoyi.traces.service;

import com.ruoyi.traces.domain.Sale;
import com.ruoyi.traces.domain.Transport;

import java.util.List;

public interface SaleService {
    int saveSale(Sale sale) throws Exception;
    List<Sale> listSale() throws Exception;
}
