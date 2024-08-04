package br.com.fiap.paymentapi.mapper;

import br.com.fiap.paymentapi.dto.ClienteRequestDto;
import br.com.fiap.paymentapi.dto.ClienteResponseDto;
import br.com.fiap.paymentapi.entity.Cliente;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClienteMapper {

    private final ModelMapper modelMapper;

    @Autowired
    public ClienteMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public ClienteResponseDto toDto(Cliente cliente) {
        return modelMapper.map(cliente, ClienteResponseDto.class);
    }

    public Cliente toEntity(ClienteRequestDto clienteRequestDTO) {
        return modelMapper.map(clienteRequestDTO, Cliente.class);
    }
}

