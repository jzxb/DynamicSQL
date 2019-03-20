
import MyBatis.bean.Department;
import MyBatis.bean.Employee;
import MyBatis.dao.DepartmentMapper;
import MyBatis.dao.EmployeeMapper;
import MyBatis.dao.EmployeeMapperDynamicSQL;
import MyBatis.dao.EmployeeMapperPlus;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MyBatisTest {

    public SqlSessionFactory getSqlSessionFactory() throws IOException {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        return new SqlSessionFactoryBuilder().build(inputStream);
    }

    @Test
    public void test() throws IOException {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession openSession = sqlSessionFactory.openSession();
        try {
            Employee employee = openSession.selectOne("org.lhx.mybatis.EmployeeMapper.selectEmp", 1);
            System.out.println(employee);
        } finally {
            openSession.close();
        }

    }

    @Test
    public void test01() throws IOException {
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        SqlSession openSession = sqlSessionFactory.openSession();
        try{
            EmployeeMapper mapper = openSession.getMapper(EmployeeMapper.class);
            Employee employee = mapper.getEmpById(1);
            System.out.println(mapper.getClass());
            System.out.println(employee);
        } finally {
            openSession.close();
        }
    }

    @Test
    public void test02() throws IOException {
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        SqlSession sqlSession = sqlSessionFactory.openSession(true);
        try{
            EmployeeMapper mapper = sqlSession.getMapper(EmployeeMapper.class);
            Employee employee = new Employee(null, "hongxuan", "1", "hehe@hehe.com");
            Employee employee1 = new Employee(3, "hongxuan", "1", "hehe@hehe.com");
            mapper.addEmp(employee);
            System.out.println(employee.getId());
            mapper.updateEmp(employee1);
            //mapper.deleteEmpById(2);
            //sqlSession.commit();
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void test03() throws IOException {
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try{
            EmployeeMapper mapper = sqlSession.getMapper(EmployeeMapper.class);
            List<Employee> nameLike = mapper.getEmpByLastNameLike("%n%");
            Map<String, Object> empByIdReturnMap = mapper.getEmpByIdReturnMap(1);
            System.out.println(empByIdReturnMap);
            for (Employee employee:nameLike) {
                System.out.println(employee);
            }
            Map<Integer, Employee> map = mapper.getEmpByLastNameLikeReturnMap("%h%");
            System.out.println(map);
        }finally {
            sqlSession.close();
        }
    }

    @Test
    public void test04() throws IOException {
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try{
            EmployeeMapperPlus mapper = sqlSession.getMapper(EmployeeMapperPlus.class);
//            Employee empById = mapper.getEmpById(1);
//            System.out.println(empById);
//            Employee empAndDept = mapper.getEmpAndDept(1);
//            System.out.println(empAndDept);
//            System.out.println(empAndDept.getDept());
            Employee empByIdStep = mapper.getEmpByIdStep(1);
            System.out.println(empByIdStep.getLastName());
            System.out.println(empByIdStep.getDept());
        }finally {
            sqlSession.close();
        }
    }

    @Test
    public void test05() throws IOException {
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try{
            DepartmentMapper mapper = sqlSession.getMapper(DepartmentMapper.class);
//            Department deptByIdPlus = mapper.getDeptByIdPlus(1);
//            System.out.println(deptByIdPlus);
//            System.out.println(deptByIdPlus.getEmps());
            Department deptByIdStep = mapper.getDeptByIdStep(1);
            System.out.println(deptByIdStep);
            System.out.println(deptByIdStep.getEmps());
        }finally {
            sqlSession.close();
        }
    }

    @Test
    public void testDynamicSql() throws IOException {
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try{
            EmployeeMapperDynamicSQL mapper = sqlSession.getMapper(EmployeeMapperDynamicSQL.class);
            Employee employee = new Employee(3,"%e%","0","hskh");
//            List<Employee> empsByConditionIf = mapper.getEmpsByConditionIf(employee);
//            for (Employee e:empsByConditionIf) {
//                System.out.println(empsByConditionIf);
//            }
//            List<Employee> empsByConditionTrim = mapper.getEmpsByConditionTrim(employee);
//            for (Employee e:empsByConditionTrim) {
//                System.out.println(empsByConditionIf);
//            }
//            List<Employee> empsByConditionChoose = mapper.getEmpsByConditionChoose(employee);
//            for (Employee e:empsByConditionTrim) {
//                System.out.println(empsByConditionChoose);
//            }
//            mapper.updateEmp(employee);
//            sqlSession.commit();
            List<Integer> id = new ArrayList<>();
            id.add(1);
            id.add(2);
            List<Employee> empsByConditionForeach = mapper.getEmpsByConditionForeach(id);
            for (Employee e:empsByConditionForeach
                 ) {
                System.out.println(e);
            }
        }finally {
            sqlSession.close();
        }
    }

    @Test
    public void testBatchSave() throws IOException {
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try{
            EmployeeMapperDynamicSQL mapper = sqlSession.getMapper(EmployeeMapperDynamicSQL.class);
            List<Employee> emps = new ArrayList<>();
            emps.add(new Employee(null,"smith","1","sss",new Department(1)));
            emps.add(new Employee(null,"allen","0","aaa",new Department(2)));
            mapper.addEmps(emps);
            sqlSession.commit();
        }finally {
            sqlSession.close();
        }
    }
}
