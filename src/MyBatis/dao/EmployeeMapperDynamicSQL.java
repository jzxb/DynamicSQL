package MyBatis.dao;

import MyBatis.bean.Employee;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface EmployeeMapperDynamicSQL {
    List<Employee> getEmpsByConditionIf(Employee employee);
    List<Employee> getEmpsByConditionTrim(Employee employee);
    List<Employee> getEmpsByConditionChoose(Employee employee);
    List<Employee> getEmpsByConditionForeach(@Param("id")List<Integer> id);
    void updateEmp(Employee employee);
    void addEmps(@Param("emps")List<Employee> emps);
}
