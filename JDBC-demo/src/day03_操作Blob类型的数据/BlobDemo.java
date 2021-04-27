package day03_操作Blob类型的数据;

import day01_如何获取数据库连接.JDBCUtil;

import java.io.*;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @program: JDBC学习源码
 * @description:Blob是二进制的大数据，只能使用PreparedStatement操作
 *              填充Blob类型的数据，占位符一般用流的形式，
 *              读取Blob类型的数据也一样
 * @author: HHJ
 * @created: 2020/09/12 09:23
 */
public class BlobDemo {
    /**
     * 向学生信息表插入blob数据
     */
    public void InsertBlobtoStu() throws Exception{
        Connection con= JDBCUtil.getConnection();
        String sql="insert 学生信息表 (学号,姓名,photo) values (?,?,?)";

        PreparedStatement ps=con.prepareStatement(sql);

        FileInputStream is=new FileInputStream(new File("C:\\Documents(资料)\\Lifes\\桌面壁纸\\cat_help.png"));
        ps.setString(1,"06002");
        ps.setString(2,"李四");
        ps.setBlob(3,is);

        ps.execute();

        JDBCUtil.closeResource(con,ps);
    }

    public void queryBlobtoStu() throws Exception{
        Connection con=null;
        PreparedStatement ps=null;
        InputStream is=null;
        OutputStream os=null;
        ResultSet rs=null;
        try{
            con = JDBCUtil.getConnection();
            String sql="select photo from 学生信息表 where 姓名=?";

            ps=con.prepareStatement(sql);

            ps.setString(1,"李四");

            rs=ps.executeQuery();

            if(rs.next()) {
                Blob blob = rs.getBlob(1);
                is = blob.getBinaryStream();
                os = new FileOutputStream("photo2.jpg");
                byte[] buffer = new byte[1024];
                int len = 0;
                while ((len = is.read(buffer)) != -1) {
                    os.write(buffer, 0, len);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            JDBCUtil.closeResource(con,ps,rs);
            is.close();
            os.close();
        }
    }
}
