package com.atguigu.crud.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.atguigu.crud.bean.Employee;
import com.atguigu.crud.bean.Msg;
import com.atguigu.crud.service.EmployeeService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * @author nicolas
 *
 */
@Controller
public class EmployeeController {
	@Autowired
	EmployeeService employeeService;

	/**
	 * 单个批量二合一 批量删除：1-2-3 单个删除：1
	 * 
	 * @param ids
	 * @return
	 */
	@ResponseBody
	@DeleteMapping("/emp/{ids}")
	public Msg deleteEmpById(@PathVariable("ids") String ids) {
		if (ids.contains("-")) {
			List<Integer> del_ids = new ArrayList<>();
			String[] str_ids = ids.split("-");
			// 组装id的集合
			for (String string : str_ids) {
				del_ids.add(Integer.parseInt(string));
			}
			employeeService.deleteBatch(del_ids);
		} else {
			Integer id = Integer.parseInt(ids);
			employeeService.deleteEmp(id);
		}
		return Msg.success();
	}

	/*
	 * @ResponseBody
	 * 
	 * @DeleteMapping("/emp/{id}") public Msg deleteEmpById(@PathVariable("id")
	 * Integer id) { employeeService.deleteEmp(id); return Msg.success(); }
	 */

	/**
	 * 解决方案 要能支持直接发送PUT之类的请求，还要封装请求体中的数据 1、配置上HttpputFormContentFilter；
	 * 2、作用：将请求体中的数据解析包装成一个map。
	 * 3、request被重新包装，request.getParameter()被重写，就会从自己封装的map中取数据 员工更新方法
	 * 
	 * @param employee
	 * @return
	 */
	@PutMapping("/emp/{empId}")
	@ResponseBody
	public Msg saveEmp(Employee employee, HttpServletRequest request) {
		System.out.println("requestScope中的值：" + request.getParameter("email"));
		System.out.println(employee);
		employeeService.updateEmp(employee);
		return Msg.success();
	}

	/**
	 * 根据id查询员工
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping("/emp/{id}")
	@ResponseBody
	public Msg getEmp(@PathVariable("id") Integer empId) {
		Employee employee = employeeService.getEmp(empId);
		return Msg.success().add("emp", employee);
	}

	/**
	 * 检验用户名是否可用
	 * 
	 * @param empName
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/checkuser")
	public Msg checkuser(@RequestParam("empName") String empName) {
		// 先判断用户名是否是合法的表达式
		String regx = "(^[a-zA-Z0-9_-]{4,16}$)|(^[\\u2E80-\\u9FFF]{2,5})";
		if (!empName.matches(regx)) {
			return Msg.fail().add("va_msg", "用户名必须是4-16位英文数字或2-5位中文");
		}
		// 数据库用户名重复校验
		boolean b = employeeService.checkUser(empName);
		System.out.println(empName);
		if (b) {
			System.out.println("true");
			return Msg.success();
		} else {
			return Msg.fail().add("va_msg", "用户名不可用");
		}
	}

	/**
	 * 员工保存 1、支持jsr303校验 2、导入Hibernate-Validator
	 */
	@PostMapping("/emp")
	@ResponseBody
	public Msg saveEmp(@Valid Employee employee, BindingResult result) {
		if (result.hasErrors()) {
			Map<String, Object> map = new HashMap<>();
			// 校验失败，应该返回失败，在模态框中显示校验失败的错误信息
			List<FieldError> errors = result.getFieldErrors();
			for (FieldError fieldError : errors) {
				System.out.println("错误的字段名：" + fieldError.getField());
				System.out.println("错误信息：" + fieldError.getDefaultMessage());
				map.put(fieldError.getField(), fieldError.getDefaultMessage());
			}
			return Msg.fail().add("errorFields", map);
		} else {
			employeeService.saveEmp(employee);
			return Msg.success();
		}
	}

	/**
	 * 导入jackson包
	 * 
	 * @author lmz
	 * @date 2019年7月15日-上午8:59:36
	 * @param pageNumber
	 * @return
	 */
	@RequestMapping("/emps")
	@ResponseBody // 告诉SpringMVC，此时的返回 不是一个 View页面，而是一个 ajax调用的返回值（Json数组）
	public Msg getEmpsWithJson(@RequestParam(value = "pageNumber", defaultValue = "1") Integer pageNumber) {

		// 引入分页插件PageHleper
		// 在查询之前调用即可
		PageHelper.startPage(pageNumber, 5);
		// startPage之后紧跟着的查询就是分页查询
		List<Employee> emps = employeeService.getAll();
		// 用PageInfo对结果进行包装
		// 将pagaInfo交给页面就可以了，封装了详细的分页信息，包括有我们的查询数据
		// 传入连续显示的页数
		PageInfo<Employee> page = new PageInfo<Employee>(emps, 5);
		return Msg.success().add("pageInfo", page);
	}

	/**
	 * query employees data(devide page)
	 * 
	 * @author lmz
	 * @date 2019年7月14日-下午7:46:42
	 * @return
	 */
//	@RequestMapping("/emps")
	public String getEmployees(@RequestParam(value = "pageNumber", defaultValue = "1") Integer pageNumber,
			Model model) {
		// 引入分页插件PageHleper
		// 在查询之前调用即可
		PageHelper.startPage(pageNumber, 5);
		// startPage之后紧跟着的查询就是分页查询
		List<Employee> emps = employeeService.getAll();
		// 用PageInfo对结果进行包装
		// 将pagaInfo交给页面就可以了，封装了详细的分页信息，包括有我们的查询数据
		// 传入连续显示的页数
		PageInfo<Employee> page = new PageInfo<Employee>(emps, 5);
//		System.out.println("page Put");
		model.addAttribute("pageInfo", page);
		return "list";
	}
}
