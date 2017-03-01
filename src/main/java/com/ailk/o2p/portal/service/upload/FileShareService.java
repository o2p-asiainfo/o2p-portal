package com.ailk.o2p.portal.service.upload;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ailk.eaap.op2.bo.FileShare;
import com.ailk.o2p.portal.dao.upload.IFileShareDao;

@Service
public class FileShareService implements IFileShareService {
	@Autowired
	private IFileShareDao fileShareSqlDao;
	
	public Integer saveFileShare(FileShare fileShareBean){
		return fileShareSqlDao.addFileShare(fileShareBean);
	}

	public List<Map> selectFileShare(FileShare fileShareBean) {
		return fileShareSqlDao.selectFileShare(fileShareBean);
	}


}
