package com.lucas.shopme.mapper;

import com.lucas.shopme.entity.User;
import com.lucas.shopme.request.UserRequestBody;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface UserMapper {

	@Mapping(target = "id", ignore = true)
	User toUser(UserRequestBody userRequestBody);
}