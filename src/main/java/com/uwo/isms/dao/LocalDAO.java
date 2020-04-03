/**
 ***********************************
 * @source LoginDAO.java
 * @date 2014. 10. 14.
 * @project isms3
 * @description
 ***********************************
 */
package com.uwo.isms.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;

import egovframework.rte.psl.dataaccess.EgovAbstractDAO;
import egovframework.rte.psl.dataaccess.util.EgovMap;

@Repository("localDAO")
public class LocalDAO extends EgovAbstractDAO {

	static final int batchSize = 50;

	public List<?> localUserList() {
		return list("JOB.LOCAL_USER_LIST");
	}

	public void updateDept(List<EgovMap> list) throws SQLException {
		@SuppressWarnings("deprecation")
		SqlMapClient client = getSqlMapClient();

		try {
			client.delete("JOB.deleteDept");

			client.startTransaction();
			client.startBatch();

			int cnt = 1;
			for (EgovMap map : list) {
				client.insert("JOB.insertDept", map);
				if (cnt % batchSize == 0) {
					client.executeBatch();
	                client.startBatch();
	            }
	            cnt++;
			}

			client.executeBatch();
			client.commitTransaction();

		} catch (Exception e) {
			e.printStackTrace();
		}
	 	finally {
	 		client.endTransaction();
		}
	}

	public void updatePos(List<EgovMap> list) throws SQLException {
		@SuppressWarnings("deprecation")
		SqlMapClient client = getSqlMapClient();

		try {
			client.startTransaction();
			client.startBatch();

			int cnt = 1;
			for (EgovMap map : list) {
				client.update("JOB.updatePos", map);
				if (cnt % batchSize == 0) {
					client.executeBatch();
	                client.startBatch();
	            }
	            cnt++;
			}

			client.executeBatch();
			client.commitTransaction();

		} catch (Exception e) {
			e.printStackTrace();
		}
	 	finally {
	 		client.endTransaction();
		}
	}

	public void updateUser(List<EgovMap> list) throws SQLException {
		@SuppressWarnings("deprecation")
		SqlMapClient client = getSqlMapClient();

		try {
			client.startTransaction();
			client.startBatch();

			int cnt = 1;
			for(EgovMap map : list) {
				if (map.get("isNew").equals(1)) {
					client.insert("JOB.insertUser", map);
				}
				else {
					client.update("JOB.updateUser", map);
					// 2017-09-12, 1회성 비밀번호 초기화
					//client.update("JOB.updateUserPassword", map);
				}

				if (cnt % batchSize == 0) {
					client.executeBatch();
	                client.startBatch();
	            }
	            cnt++;
			}

			client.executeBatch();
			client.commitTransaction();

		} catch (Exception e) {
			e.printStackTrace();
		}
	 	finally {
	 		client.endTransaction();
		}
	}

	public void updateUserInfo() throws SQLException {
		update("JOB.updateUserInfo");
	}

	public void disableUser() throws SQLException {
		update("JOB.disableUser");
	}


	public EgovMap selectAssetInfo(EgovMap map) {
		return (EgovMap) select("JOB.selectAssetInfo", map);
	}

	public void updateAsset(List<EgovMap> list) throws SQLException {
		@SuppressWarnings("deprecation")
		SqlMapClient client = getSqlMapClient();

		try {
			client.startTransaction();
			client.startBatch();

			int cnt = 1;
			for(EgovMap map : list) {
				if (map.get("uarAssKey") == null) {
					client.insert("JOB.insertAsset", map);
					//insert("JOB.insertAsset", map);
				}
				else {
					client.update("JOB.updateAsset", map);
					//update("JOB.updateAsset", map);
				}

				if (cnt % batchSize == 0) {
					client.executeBatch();
	                client.startBatch();
	            }
	            cnt++;
			}

			client.executeBatch();
			client.commitTransaction();

		} catch (Exception e) {
			e.printStackTrace();
		}
	 	finally {
	 		client.endTransaction();
		}
	}

	public void insertAssetGroup(EgovMap map) {
		insert("JOB.insertAssetGroup", map);
	}

	public void updateUwoRskVlb(List<Map<String,String>> list) {
		update("JOB.updateUwoRskVlb", list);
	}

	public String getAssetGroupCode(Map<String, String> map) {
		return (String) select("JOB.getAssetGroupCode", map);
	}
}