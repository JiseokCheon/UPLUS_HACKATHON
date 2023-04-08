package uplus.hackerton.barogo.repository;

import org.springframework.data.repository.CrudRepository;

import uplus.hackerton.barogo.domain.DeliverList;

public interface DeliverRedisRepository extends CrudRepository<DeliverList, String> {
}
