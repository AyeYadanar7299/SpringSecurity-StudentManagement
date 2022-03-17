package com.exercise.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Service;

import com.exercise.dto.StudentDTO;
import com.exercise.dto.UserDTO;
import com.exercise.service.JPAUtil;

@Service
public class StudentDAOImpl implements StudentDAO {

	@Override
	public int insert(StudentDTO dto) {
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
	public int update(StudentDTO dto) {
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
	public int delete(StudentDTO dto) {
		EntityManager em = null;
		int result = 0;
		try {
		em = JPAUtil.getEntityManagerFactory().createEntityManager();
		em.getTransaction().begin();
		StudentDTO outputDTO = em.find(StudentDTO.class,dto.getStudentId());

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
	public List<StudentDTO> select() {
		EntityManager em = null;
		List<StudentDTO> lstStudent = new ArrayList<StudentDTO>();
		try {
		em = JPAUtil.getEntityManagerFactory().createEntityManager();
		lstStudent= em.createQuery("select s from StudentDTO s").getResultList();
		}
		finally 
		{
		em.close();
		}
		return lstStudent;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<StudentDTO> selectOne(StudentDTO dto) {
		EntityManager em = null;
		List<StudentDTO> outputDTO = new ArrayList<StudentDTO>();
		try {
			em = JPAUtil.getEntityManagerFactory().createEntityManager();
			outputDTO = em.createQuery(
					"select s from StudentDTO s where s.studentId=:studentId or s.studentName=:studentName or s.className=:className")
					.setParameter("studentId", dto.getStudentId())
					.setParameter("studentName", dto.getStudentName())
					.setParameter("className", dto.getClassName())
					.getResultList();
		} finally {
			em.close();
		}
		return outputDTO;
	}
	
	@Override
	public StudentDTO selectone(StudentDTO dto)
	{
		EntityManager em = null;
		StudentDTO sDTO = new StudentDTO();
		try {
		em = JPAUtil.getEntityManagerFactory().createEntityManager();
		sDTO = em.find(StudentDTO.class, dto.getStudentId());
		}
		finally {
		em.close();
		}
		return sDTO;
	}


}
