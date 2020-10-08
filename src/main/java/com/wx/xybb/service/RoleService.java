package com.wx.xybb.service;

import com.wx.xybb.entity.SysRole;
import com.wx.xybb.vo.req.AddRoleReqVO;
import com.wx.xybb.vo.req.RolePageReqVO;
import com.wx.xybb.vo.req.RoleUpdateReqVO;
import com.wx.xybb.vo.resp.PageVO;

import java.util.List;

/**
 * @ClassName: RoleService
 * TODO:类文件简单描述
 * @Author: Shawshank King
 * @UpdateUser: Shawshank King
 * @Version: 0.0.1
 */
public interface RoleService {
    PageVO<SysRole> pageInfo(RolePageReqVO vo);
    SysRole addRole(AddRoleReqVO vo);
    List<SysRole> selectAll();
    SysRole detailInfo(String id);
    void updateRole(RoleUpdateReqVO vo);
    void deletedRole(String roleId);
    List<String> getRoleNames(String userId);
    List<SysRole> getRoleInfoByUserId(String userId);
}
