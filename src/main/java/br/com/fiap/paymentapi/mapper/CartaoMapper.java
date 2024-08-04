package br.com.fiap.paymentapi.mapper;

import br.com.fiap.paymentapi.dto.CartaoRequestDto;
import br.com.fiap.paymentapi.dto.CartaoResponseDto;
import br.com.fiap.paymentapi.entity.Cartao;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class CartaoMapper {

    private final ModelMapper modelMapper;

    public CartaoMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public CartaoResponseDto toDto(Cartao cartao) {
        return modelMapper.map(cartao, CartaoResponseDto.class);
    }

    public Cartao toEntity(CartaoRequestDto cartaoRequestDto) {
        return modelMapper.map(cartaoRequestDto, Cartao.class);
    }
}
