package com.ailk.o2p.portal.dao.upload;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.ailk.eaap.op2.bo.FileShare;
import com.linkage.rainbow.dao.SqlMapDAO;

@Repository
public class FileShareDao  implements IFileShareDao {
 	 
	private static final Logger log = Logger.getLogger(FileShareDao.class);
	
	@Resource(name="iBatisSqlMapDAO")
	private SqlMapDAO sqlMapDao;
	
	public Integer addFileShare(FileShare fileShareBean){
		 File fileContent = fileShareBean.getSFileContent() ;
		 fileShareBean.setSFileContent(null) ;
		 Integer fileShareId = (Integer)sqlMapDao.insert("fileShare.insertFileShare", fileShareBean) ;
		 return  inImageContent(fileShareId,fileContent) ;
	}
	
	public Integer inImageContent(Integer fileShareId,File fileContent){
		PreparedStatement pstmt = null;
		Connection con = null;
		FileInputStream stream =null;
        try {
        	con = sqlMapDao.getSmcTemplate().getDataSource().getConnection();
			con.setAutoCommit(false);
			pstmt = con.prepareStatement("update file_share set s_file_content = ? where s_file_id=?");
			stream = new FileInputStream(fileContent);
			pstmt.setInt(2, fileShareId);
			pstmt.setBinaryStream(1, stream, stream.available());
			pstmt.executeUpdate();
			con.commit();
		} catch (Exception e) {
			log.error(e.getStackTrace());
		}finally{
			try {
				if(stream!=null){
					stream.close();
				}
			} catch (Exception e) {
				log.error("Exception!", e);
			} 
			try {
				if(pstmt!=null){
					pstmt.close();
				}
			} catch (SQLException e) {
				log.error("SQLException!", e);

			} catch (Exception e) {
				log.error("Exception!", e);
			} 
			try {
				if(con!=null){
						con.close();
				}
			} catch (SQLException e) {
				log.error("SQLException!", e);
			} catch (Exception e) {
				log.error("Exception!", e);
			} 
		}
		return fileShareId ;
	}
	
	
	public List<Map>  selectFileShare(FileShare fileShareBean){
		return (List<Map>)sqlMapDao.queryForList("fileShare.selectFileShare", fileShareBean) ;
	}
	

}
