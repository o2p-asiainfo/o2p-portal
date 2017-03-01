package com.ailk.o2p.portal.service.upload;
import java.util.List;
import java.util.Map;
import com.ailk.eaap.op2.bo.FileShare;
 
public interface IFileShareService{
 
	public Integer saveFileShare(FileShare fileShare); 
	
	public List<Map> selectFileShare(FileShare fileShareBean);
	
}
