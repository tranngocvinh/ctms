package com.ctms.mapper;

import com.ctms.dto.SIDTO;
import com.ctms.entity.SI;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class SIDTOMapper implements Function<SI, SIDTO> {
    @Override
    public SIDTO apply(SI si) {
        return new SIDTO(
                si.getId(),
                si.getEmptyContainer().getId(),
                si.getCargoType().getId(),
                si.getCargoWeight(),
                si.getCargoVolume()
        );
    }
}
