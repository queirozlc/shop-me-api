package com.lucas.shopme.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestBody {
	@NotBlank(message = "O campo 'nome' não pode ser vazio.")
	private String name;
	@NotBlank(message = "O campo 'email' não pode ser vazio.")
	@Email(message = "Email inválido.")
	private String email;
	@NotBlank(message = "O campo 'senha' não pode ser vazio.")
	private String password;
	private boolean enabled;
}
