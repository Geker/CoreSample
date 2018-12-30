package org.corejava.bean.utils.copytools.mapstruct;

import org.corejava.bean.utils.Computer;
import org.corejava.bean.utils.NewComputer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface MapperExample {
	MapperExample INSTANCE = Mappers.getMapper(MapperExample.class);

	@Mapping(dateFormat = "yyyy.dd.MM.", source = "buyTime", target = "buyTime")
	NewComputer toNewComp(Computer car);


}
