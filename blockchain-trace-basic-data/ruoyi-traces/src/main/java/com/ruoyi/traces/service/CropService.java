package com.ruoyi.traces.service;

import com.ruoyi.traces.domain.Crop;

import java.util.List;

public interface CropService {
    int saveCrops(Crop crop) throws Exception;
    List<Crop> listCrops() throws Exception;
}
