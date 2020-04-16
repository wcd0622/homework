package com.lagou.edu.dao;

import com.lagou.edu.pojo.Resume;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 一个符合SpringDataJpa要求的Dao层接口是需要继承JpaRepository和JpaSpecificationExecutor
 * JpaRepository<要操作的实体类类型,主键类型>
 *     封装了基本的crud操作
 *
 *     JpaSpecificationExecutor<要操作的实体类类型>
 *         封装了复杂的查询（分页，排序等）
 *
 */
@Repository
public interface ResumeDao extends JpaRepository<Resume,Long>, JpaSpecificationExecutor<Resume> {
    @Query(value = "select * from tb_resume where id= ?1 and address= ?2 and name = ?3 and phone=?4",nativeQuery = true)
    public Resume login(Resume resume);
    @Query("from Resume where id=?1 and name=?2")
    public List<Resume> findByJpql(Long id,String name);

    /**
     * 使用原生sql语句查询需要将nativeQuery设置为true 默认为false（jpql）
     * @param name
     * @param address
     * @return
     */
    @Query(value = "select * from tb_resume where id= ?1 and address= ?2 and name = ?3 and phone=?4",nativeQuery = true)
    public List<Resume> findBySql(String name,String address);

    /**
     * 方法命名规则
     * 按照name模糊查询（like）
     * 方法名findBy开头
     * -属性名（首字母大写）
     * -查询方式（模糊查询，等价查询）,如果不写查询方式，默认等价查询
     */
    public List<Resume> findByNameLikeAndAddress(String name,String address);
}
