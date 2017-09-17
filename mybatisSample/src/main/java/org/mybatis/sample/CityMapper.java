package org.mybatis.sample;

import org.apache.ibatis.annotations.Param;

interface CityMapper {
	public City selectCity(int cityId);

	public Integer updateCity(String city, @Param("city_id") int cityId);

}
