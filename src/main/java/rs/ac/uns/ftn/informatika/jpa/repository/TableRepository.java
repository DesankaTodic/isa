package rs.ac.uns.ftn.informatika.jpa.repository;


import java.util.ArrayList;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import rs.ac.uns.ftn.informatika.jpa.domain.Reon;
import rs.ac.uns.ftn.informatika.jpa.domain.Restaurant;
import rs.ac.uns.ftn.informatika.jpa.domain.Tablee;

public interface TableRepository extends PagingAndSortingRepository<Tablee, Long> {
	
	public ArrayList<Tablee> findByReonAndRestaurant(Reon reon, Restaurant restaurant);
	
	public ArrayList<Tablee> findByRestaurant(Restaurant restaurant);
	
	public ArrayList<Tablee> findByReon(Reon reon);
	
	
	@Modifying
	@Query("update Tablee set exist = ? where id = ? ")
	public void updateTableFlag(boolean exist, Long id);
}
