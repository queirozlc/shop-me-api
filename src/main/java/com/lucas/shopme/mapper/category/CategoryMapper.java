package com.lucas.shopme.mapper.category;

import com.lucas.shopme.entity.Category;
import com.lucas.shopme.request.category.CategoryRequestBody;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface CategoryMapper {
	@Mapping(target = "parent", ignore = true)
	@Mapping(target = "id", ignore = true)
	@Mapping(target = "parent.id", source = "parentId")
	@Mapping(target = "children", ignore = true)
	Category toCategory(CategoryRequestBody categoryRequestBody);
}