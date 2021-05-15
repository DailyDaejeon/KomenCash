package com.komencash.backend.dto.law;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LawFindResponseDto {

    private List<LawFindByLawTypeResponseDto> lawFindByLawTypeResponseDtoList;
}
