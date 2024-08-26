package com.ctms.mapper;

import com.ctms.dto.EmptyContainerDTO;
import com.ctms.dto.EmptyContainerDetailDTO;
import com.ctms.entity.EmptyContainer;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class EmptyContainerDTOMapper implements Function<EmptyContainer, EmptyContainerDTO> {

    @Override
    public EmptyContainerDTO apply(EmptyContainer emptyContainer) {
       // ContainerSizeDTO containerSizeDTO = new ContainerSizeDTO(emptyContainer.getDetails().);
        List<EmptyContainerDetailDTO> details= emptyContainer.getDetails().stream()
                .map(p -> new EmptyContainerDetailDTO(
                        p.getContainer().getContainerCode())).collect(Collectors.toList());
        return new EmptyContainerDTO(
                emptyContainer.getId(),
                emptyContainer.getRequestTime(),
                emptyContainer.getPortLocation(),
                emptyContainer.getShip(),
                emptyContainer.getIsApproved(),
                details,
                emptyContainer.getCustomer(),
                emptyContainer.isSi()
        );
    }
}
