package day08_DbUtils的使用;

/**
 * @program: JDBC学习源码
 * @description:
 * @author: HHJ
 * @created: 2020/09/26 11:17
 */
public class Test {
    public static void main(String[] args) throws Exception{

//        Connection con=null;
//        try{
//            con = JDBCUtils.getConnection1();
//            String sql="insert into 学生信息表(学号,姓名,班级) values (?,?,?)";
//            System.out.println(QueryRunner_Demo.query_update(con,sql,"00008","郭十","18软件工程2班"));
//        }catch (SQLException e){
//            e.printStackTrace();
//        }finally {
//            JDBCUtil.closeResource(con,null);
//        }


        //测试查询一条记录
        QueryRunner_Demo.query_instance();

        //测试查询多条记录
        QueryRunner_Demo.query_instanceList();

        //测试查询特殊值
        QueryRunner_Demo.query_speVal();


    }
}
