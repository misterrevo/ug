package com.revo.ug.mapper;

import com.revo.ug.dto.response.ComputerResponse;
import com.revo.ug.entity.Computer;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ComputerMapper {

    ComputerResponse toResponse(final Computer computer);
}
