package com.exercise.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Service;

import com.exercise.dto.ClassDTO;
import com.exercise.dto.UserDTO;
import com.exercise.service.JPAUtil;

@Service
public class ClassDAOImpl implements ClassDAO {

	@Override
	public int insert(ClassDTO dto) {
		EntityManager em=null;
		int res=0;
		try
		{
			em=JPAUtil.getEntityManagerFactory().createEntityManager();
			em.getTransaction().begin();
			em.persist(dto);
			em.getTransaction().commit();
			res=1;
			
		}
		finally
		{
			em.close();
		}
		
		return res;
	}

	@SuppressWarnings("unchecked")
	public List<ClassDTO> selectone(ClassDTO dto) {
		EntityManager em = null;
		List<ClassDTO> outputDTO = new ArrayList<ClassDTO>();
		try {
			em = JPAUtil.getEntityManagerFactory().createEntityManager();
			outputDTO = em.createQuery(
					"select c from ClassDTO c where c.id=:id or c.name=:name")
					.setParameter("id", dto.getId())
					.setParameter("name", dto.getName()).getResultList();
		} finally {
			em.close();
		}
		return outputDTO;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ClassDTO> select() {
		EntityManager em = null;
		List<ClassDTO> lstClass = new ArrayList<ClassDTO>();
		try {
		em = JPAUtil.getEntityManagerFactory().createEntityManager();
		lstClass= em.createQuery("select c from ClassDTO c").getResultList();
		}
		finally {
		em.close();
		}
		return lstClass;	}

	

}
