package com.obinnaogbonna.drone.daoImpl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.obinnaogbonna.drone.dao.AppRepository;
import com.querydsl.core.types.EntityPath;
import com.querydsl.jpa.impl.JPAQuery;

import org.springframework.stereotype.Repository;

@Repository
public class AppRepositoryImpl implements AppRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public <E> JPAQuery<E> startJPAQuery(EntityPath<E> entityPath) {
        return new JPAQuery<E>(entityManager).from(entityPath);
    }

}
