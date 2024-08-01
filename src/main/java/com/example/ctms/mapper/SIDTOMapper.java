package com.example.ctms.mapper;

import com.example.ctms.dto.SIDTO;
import com.example.ctms.entity.SI;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class SIDTOMapper implements Function<SI, SIDTO> {
    @Override
    public SIDTO apply(SI si) {
        return new SIDTO(
                si.getEmptyContainer().getId(),
                si.getCargoType().getId(),
                si.getCargoWeight(),
                si.getCargoVolume()
        );
    }
}
