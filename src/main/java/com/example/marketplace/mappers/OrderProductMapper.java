package com.example.marketplace.mappers;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {ProductMapper.class})
public interface OrderProductMapper { }
