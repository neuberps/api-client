package com.ms.client.dto;

import com.ms.client.model.Client;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientUpdateDTO implements Serializable {

    @Pattern(regexp = "^[A-Z][a-z]+\s[A-Z][a-z]+$",
            message = "O nome completo deve conter: " +
                    "Nome e sobrenome com iniciais em Letra Maiúscula!")
    private String name;

    @Email
    private String email;

    @Pattern(regexp = "\\d{11}", message = "O telefone deve ser: " +
            "No formato: XXXXXXXXXXX (Apenas números)" +
            "Devem ser inseridos 11 números (DDD, 9 na frente e o número em si")
    private String cel;

    @Pattern(regexp = "\\d{11}", message = "CPF deve conter 11 números!")
    private String cpf;

    public  ClientUpdateDTO (Client entity){
        BeanUtils.copyProperties(entity, this);
    }



}
