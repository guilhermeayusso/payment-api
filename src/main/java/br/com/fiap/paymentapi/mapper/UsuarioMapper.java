package br.com.fiap.paymentapi.mapper;

import br.com.fiap.paymentapi.dto.UsuarioRequestDto;
import br.com.fiap.paymentapi.dto.UsuarioResponseDto;
import br.com.fiap.paymentapi.entity.Usuario;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.stereotype.Service;

@Service
public class UsuarioMapper {

    public static Usuario toUsuario (UsuarioRequestDto dto){
        return new ModelMapper().map(dto, Usuario.class);
    }

    public static UsuarioResponseDto toDto (Usuario usuario){
        String role = usuario.getRole().name().substring("ROLE_".length());
        PropertyMap<Usuario, UsuarioResponseDto> props = new PropertyMap<Usuario, UsuarioResponseDto>() {
            @Override
            protected void configure() {
                map().setRole(role);
            }
        };
        ModelMapper mapper = new ModelMapper();
        mapper.addMappings(props);
        return mapper.map(usuario,UsuarioResponseDto.class);
    }
}
