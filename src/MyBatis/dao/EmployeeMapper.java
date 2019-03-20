package MyBatis.dao;


import MyBatis.bean.Employee;
import org.apache.ibatis.annotations.MapKey;

import java.util.List;
import java.util.Map;

public interface EmployeeMapper {
    @MapKey("id")
    Map<Integer,Employee> getEmpByLastNameLikeReturnMap(String lastName);

    Map<String,Object> getEmpByIdReturnMap(Integer id);

    List<Employee> getEmpByLastNameLike(String lastName);

    Employee getEmpById(Integer id);

    void addEmp(Employee employee);

    void updateEmp(Employee employee);

    void deleteEmpById(Integer id);
}
