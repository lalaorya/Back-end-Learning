package day03_����Blob���͵�����;

import day01_��λ�ȡ���ݿ�����.JDBCUtil;

import java.io.*;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @program: JDBCѧϰԴ��
 * @description:Blob�Ƕ����ƵĴ����ݣ�ֻ��ʹ��PreparedStatement����
 *              ���Blob���͵����ݣ�ռλ��һ����������ʽ��
 *              ��ȡBlob���͵�����Ҳһ��
 * @author: HHJ
 * @created: 2020/09/12 09:23
 */
public class BlobDemo {
    /**
     * ��ѧ����Ϣ�����blob����
     */
    public void InsertBlobtoStu() throws Exception{
        Connection con= JDBCUtil.getConnection();
        String sql="insert ѧ����Ϣ�� (ѧ��,����,photo) values (?,?,?)";

        PreparedStatement ps=con.prepareStatement(sql);

        FileInputStream is=new FileInputStream(new File("C:\\Documents(����)\\Lifes\\�����ֽ\\cat_help.png"));
        ps.setString(1,"06002");
        ps.setString(2,"����");
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
            String sql="select photo from ѧ����Ϣ�� where ����=?";

            ps=con.prepareStatement(sql);

            ps.setString(1,"����");

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
