package ar.edu.unju.edm.app.dao;

import org.springframework.data.repository.CrudRepository;

import ar.edu.unju.edm.app.model.UserBD;

public interface IUserDAO extends CrudRepository<UserBD, Long> {

}
