package com.exercise.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Service;

import com.exercise.dto.StudentDTO;
import com.exercise.dto.UserDTO;
import com.exercise.service.JPAUtil;
@Service
public class UserDAOImpl implements UserDAO {

	@Override
	public int insert(UserDTO dto) {
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

	@Override
	public int update(UserDTO dto) {
		EntityManager em = null;
		int result = 0;
		try {
			
			em = JPAUtil.getEntityManagerFactory().createEntityManager();
			em.getTransaction().begin();
			em.merge(dto);
			em.getTransaction().commit();
			result = 1;
			}
		finally 
		{
		em.close();
		}
		return result;
	}

	@Override
	public int delete(UserDTO dto) {
		EntityManager em = null;
		int result = 0;
		try {
		em = JPAUtil.getEntityManagerFactory().createEntityManager();
		em.getTransaction().begin();
		UserDTO outputDTO = em.find(UserDTO.class,dto.getId());

		em.remove(outputDTO);
		em.getTransaction().commit();
		result = 1;
		}
		finally 
		{
		em.close();
		}
		return result;
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UserDTO> select() {
		EntityManager em = null;
		List<UserDTO> lstUser = new ArrayList<UserDTO>();
		try {
		em = JPAUtil.getEntityManagerFactory().createEntityManager();
		lstUser= em.createQuery("select u from UserDTO u").getResultList();
		}
		finally {
		em.close();
		}
		return lstUser;
	}

	@SuppressWarnings("unchecked")
	public List<UserDTO> selectOne(UserDTO dto) {
		EntityManager em = null;
		List<UserDTO> outputDTO = new ArrayList<UserDTO>();
		try {
			em = JPAUtil.getEntityManagerFactory().createEntityManager();
			outputDTO = em.createQuery(
					"select u from UserDTO u where u.id=:id or u.name=:name")
					.setParameter("id", dto.getId())
					.setParameter("name", dto.getName()).getResultList();
		} finally {
			em.close();
		}
		return outputDTO;
	}
	
	@Override
	public UserDTO selectone(UserDTO dto)
	{
		EntityManager em = null;
		UserDTO sDTO = new UserDTO();
		try {
		em = JPAUtil.getEntityManagerFactory().createEntityManager();
		sDTO = em.find(UserDTO.class, dto.getId());
		}
		finally {
		em.close();
		}
		return sDTO;
	}

}
