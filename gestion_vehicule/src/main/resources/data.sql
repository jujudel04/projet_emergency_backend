insert into vehicle(id,lon,lat,type,efficiency,
			liquid_type,liquid_quantity,liquid_consumption,fuel,
			fuel_consumption,crew_member, crew_member_capacity,facility_ref_id)values(1,0,0,'CAR',10,'WATER',50,10,50,10,1,10,1);

insert into caserne(id,vehicules,lon,lat)values(1,'1:2:3:4',0,0);