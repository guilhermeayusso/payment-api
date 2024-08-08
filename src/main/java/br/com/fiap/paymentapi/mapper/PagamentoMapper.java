package br.com.fiap.paymentapi.mapper;

import br.com.fiap.paymentapi.dto.PagamentoRequestDto;
import br.com.fiap.paymentapi.dto.PagamentoResponseDto;
import br.com.fiap.paymentapi.entity.Pagamento;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PagamentoMapper {

    private final ModelMapper modelMapper;

    @Autowired
    public PagamentoMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public static Pagamento toEntity (PagamentoRequestDto dto){
        return new ModelMapper().map(dto, Pagamento.class);
    }

    public static PagamentoResponseDto toDto (Pagamento entity){
        return new ModelMapper().map(entity, PagamentoResponseDto.class);
    }



}
