package com.wx.xybb.service;

import com.wx.xybb.entity.SysDept;
import com.wx.xybb.vo.req.DeptAddReqVO;
import com.wx.xybb.vo.req.DeptUpdateReqVO;
import com.wx.xybb.vo.resp.DeptRespNodeVO;

import java.util.List;

/**
 * @ClassName: DeptService
 * TODO:类文件简单描述
 * @Author: Shawshank King
 * @UpdateUser: Shawshank King
 * @Version: 0.0.1
 */
public interface DeptService {

    List<SysDept> selectAll();

    List<DeptRespNodeVO> deptTreeList(String deptId);

    SysDept addDept(DeptAddReqVO vo);

    void updateDept(DeptUpdateReqVO vo);

    void deletedDept(String id);

}
