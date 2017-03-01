package com.ailk.o2p.portal.dao.upload;
import java.util.List;
import java.util.Map;

import com.ailk.eaap.op2.bo.FileShare;

public interface IFileShareDao {
	
	public Integer addFileShare(FileShare fileshare);
	
	public List<Map>  selectFileShare(FileShare fileShareBean);
	
}
