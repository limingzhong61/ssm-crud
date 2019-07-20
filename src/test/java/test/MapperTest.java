package test;

import java.util.UUID;

import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.atguigu.crud.bean.Department;
import com.atguigu.crud.bean.Employee;
import com.atguigu.crud.dao.DepartmentMapper;
import com.atguigu.crud.dao.EmployeeMapper;

/**
 * 测试dao工作层
 * 
 * @author test 推荐Spring的项目就可以使用spring的单元测试，可以依赖注入我们需要的组件 1.导入springTest模块
 *         2.@ContextConfiguration指定spring配置文件内容,@RunWith(SpringJUnit4ClassRunner.class)
 *         3.直接autoWrited要使用的组件即可
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class MapperTest {
	@Autowired
	DepartmentMapper departmentMapper;
	@Autowired
	EmployeeMapper employeeMapper;
	@Autowired
	SqlSession sqlSession;

	/**
	 * 测试departMapper
	 */
	@Test
	public void testCRUD() {
//		//1.creat springIoc container
//		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
//		//2.从spring Container中获取bean
//		DepartmentMapper bean = context.getBean(DepartmentMapper.class);
		System.out.println(departmentMapper);
//		departmentMapper.insertSelective(new Department(null,"development"));
//		departmentMapper.insertSelective(new Department(null,"test"));
//		System.out.println(departmentMapper.selectByPrimaryKey(1));;
		employeeMapper.insertSelective(new Employee(null, "Jerry", "M", "Jerry@qq.com", 1));
		EmployeeMapper mapper = (EmployeeMapper) sqlSession.getMapper(EmployeeMapper.class);
		for (int i = 0; i < 1000; i++) {
			String uid = UUID.randomUUID().toString().substring(0, 5)+i;
			mapper.insertSelective(new Employee(null, uid,"M", uid+"@qq.com", 1));
		}
		System.out.println("success");
	}
}
