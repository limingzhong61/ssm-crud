package com.atguigu.crud.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atguigu.crud.bean.Employee;
import com.atguigu.crud.bean.EmployeeExample;
import com.atguigu.crud.bean.EmployeeExample.Criteria;
import com.atguigu.crud.dao.EmployeeMapper;

@Service
public class EmployeeService {

	@Autowired
	EmployeeMapper employeeMapper;

	/**
	 * 员工删除
	 * 
	 * @param id
	 */
	public void deleteEmp(Integer id) {
		employeeMapper.deleteByPrimaryKey(id);
	}

	public void updateEmp(Employee employee) {
		employeeMapper.updateByPrimaryKeySelective(employee);
	}

	/**
	 * query all employee
	 * 
	 * @return
	 */
	public List<Employee> getAll() {
		return employeeMapper.selectByExampleWithDept(null);
	}

	public Employee getEmp(Integer empId) {
		return employeeMapper.selectByPrimaryKey(empId);
	}

	public void saveEmp(Employee employee) {
		employeeMapper.insertSelective(employee);
	}

	/**
	 * 检验用户名是否可用
	 * 
	 * @param empName
	 * @return
	 */
	public boolean checkUser(String empName) {
		EmployeeExample example = new EmployeeExample();
		EmployeeExample.Criteria criteria = example.createCriteria();
		criteria.andEmpNameEqualTo(empName);
		long count = employeeMapper.countByExample(example);
		return count == 0;
	}

	public void deleteBatch(List<Integer> ids) {
        EmployeeExample example = new EmployeeExample();
        EmployeeExample.Criteria criteria = example.createCriteria();
        //delete from xxx where emp_id in(1,2,3)
        criteria.andEmpIdIn(ids);
        employeeMapper.deleteByExample(example);
    }

}
