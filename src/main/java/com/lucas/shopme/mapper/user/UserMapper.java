package com.lucas.shopme.mapper.user;

import com.lucas.shopme.entity.User;
import com.lucas.shopme.request.user.UserRequestBody;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface UserMapper {

	@Mapping(target = "id", ignore = true)
	User toUser(UserRequestBody userRequestBody);
}