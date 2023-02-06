package com.lucas.shopme.request.category;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryRequestBody {
	@NotBlank(message = "O nome da categoria é obrigatório.")
	private String name;
	@NotBlank(message = "O 'alias' da categoria é obrigatório")
	private String alias;
	private boolean enabled;
	private UUID parentId;
}
