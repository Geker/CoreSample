package org.mybatis.sample;

import org.apache.ibatis.annotations.Param;

interface CityMapper {
    public City selectCity(int cityId);

	public City selectCityName(String name);

    public Integer updateCity(@Param("city") String city, @Param("city_id") int city_id);

}
